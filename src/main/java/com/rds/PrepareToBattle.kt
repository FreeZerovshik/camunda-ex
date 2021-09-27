package com.rds

import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class PrepareToBattle: JavaDelegate {
    override fun execute(p0: DelegateExecution) {
        var enemyWarriors = (Math.random() * 100).toInt()
        var battleStatus = "Undefined"
        var warriors = p0.getVariable("warriors") as Int
        var isWin = false

        if (warriors <0 || warriors >100)  throw BpmnError("warriorsError")

        if ((warriors - enemyWarriors) > 0 ) {
            battleStatus = "Winner!"
            isWin = true
        } else {
            battleStatus = "Fail :("
            isWin = false
        }

        p0.setVariable("enemyWarriors", enemyWarriors)
        p0.setVariable("battleStatus", battleStatus)
        p0.setVariable("isWin", isWin)
    }
}