package com.rds

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class FightEnemy: JavaDelegate {
    override fun execute(delegateExecution: DelegateExecution?) {
        var army: MutableList<Boolean> = delegateExecution?.getVariable("army") as MutableList<Boolean>
        var enemyWarriors  = delegateExecution.getVariable("enemyWarriors") as Int
        
//        println("размер армии =${army.size}")
        Thread.sleep(2000)

        if (Random.nextBoolean()) {
            enemyWarriors--
            println("Вражеский воин убит!")
        } else {
            army.removeLast()
            println("Наш воин убит!")
        }

        delegateExecution.setVariable("enemyWarriors", enemyWarriors)
        delegateExecution.setVariable("warriors", army.size)
        delegateExecution.setVariable("army", army)
    }
}