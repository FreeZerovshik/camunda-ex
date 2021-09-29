package com.rds

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component

@Component
class CalcResultBattle: JavaDelegate {
    override fun execute(delegateExecution: DelegateExecution?) {
        var enemyWarriors = delegateExecution?.getVariable("enemyWarriors") as Int
        var warriors = delegateExecution?.getVariable("warriors")  as Int
        var isWin = false
        var battleStatus = "Проигрыш!"

        if (warriors > enemyWarriors) {
            isWin = true
            battleStatus = "Победа!"
        }
//        println("enemyWarriors=$enemyWarriors")
//        println("warriors=$warriors")
//        println("isWin=$isWin")

        delegateExecution.setVariable("battleStatus", battleStatus)
        delegateExecution.setVariable("isWin", isWin)
        delegateExecution.setVariable("enemyWarriors", enemyWarriors)
        delegateExecution.setVariable("warriors", warriors)
    }

}