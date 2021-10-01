package com.rds

import com.rds.dao.Warrior
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.ArrayList

@Component
class PrepareToBattle: JavaDelegate {

    @Value(value = "\${maxWarriors}")
    var maxWarriors: Int = 100

    override fun execute(delegateExecution: DelegateExecution) {
        var enemyWarriors = (Math.random() * 100).toInt()
        var warriors = delegateExecution.getVariable("warriors") as Int

        if (warriors <0 || warriors > maxWarriors)  throw BpmnError("warriorsError")

        var army: MutableList<Boolean> = ArrayList(Collections.nCopies(warriors, true))

        println("Подготовка к битве! Врагов = $enemyWarriors против нашей армии = $warriors")

        delegateExecution.setVariable("enemyWarriors", enemyWarriors)
        delegateExecution.setVariable("army",army)
    }

}