package com.rds

import com.rds.dao.Warrior
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.Variables
import org.camunda.connect.Connectors
import org.camunda.connect.httpclient.HttpConnector
import org.camunda.connect.httpclient.HttpRequest
import org.camunda.spin.Spin.JSON
import org.camunda.spin.json.SpinJsonNode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.ArrayList

@Component
class PrepareToBattle: JavaDelegate {

    @Value(value = "\${url}")
    val url: String = ""

    @Value(value = "\${maxWarriors}")
    var maxWarriors: Int = 100

    override fun execute(delegateExecution: DelegateExecution) {
        var enemyWarriors = (Math.random() * 100).toInt()
        var warriors = delegateExecution.getVariable("warriors") as Int

        if (warriors <0 || warriors > maxWarriors)  throw BpmnError("warriorsError")

        var army: MutableList<Warrior> = ArrayList()

        for (i in 1..warriors) {
            army.add(createWarrior())
        }

        val armyJson = Variables.objectValue(army).serializationDataFormat("application/json").create()
        println("Подготовка к битве! Врагов = $enemyWarriors против нашей армии = ${army.size}")

        delegateExecution.setVariable("enemyWarriors", enemyWarriors)
        delegateExecution.setVariable("army",army)
        delegateExecution.setVariable("armyJson",armyJson)
    }

    private fun createWarrior(): Warrior {
        lateinit var warrior: Warrior
        val http: HttpConnector =  Connectors.getConnector(HttpConnector.ID)
        val request: HttpRequest = http.createRequest().get().url(url)

        val headers = mutableMapOf<String,String>()
        headers["headers"] = "application/json"

        request.setRequestParameter("headers", headers)

        val response = request.execute()

        if (response.statusCode == 200){
            val node: SpinJsonNode = JSON(response.response)
            warrior = Warrior(
                name = node.prop("name.findName").stringValue(),
                title = node.prop("name.title").stringValue(),
                hp = Integer.parseInt(node.prop("random.number").stringValue()),
                isAlive = true
            )
        }

        response.close()

        return warrior
    }


}