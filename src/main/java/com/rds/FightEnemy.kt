package com.rds

import com.rds.dao.Warrior
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class FightEnemy: JavaDelegate {
    override fun execute(delegateExecution: DelegateExecution?) {
        var army: MutableList<Warrior> = delegateExecution?.getVariable("army") as MutableList<Warrior>
        var enemyWarriors  = delegateExecution.getVariable("enemyWarriors") as Int
        
//        println("размер армии =${army.size}")
        Thread.sleep(2000)

        if (Random.nextBoolean()) {
            enemyWarriors--
            println("Вражеский воин убит! ($enemyWarriors vs ${army.size})")
        } else {
            army.removeLast()
            println("Наш воин убит! ($enemyWarriors vs ${army.size})")
        }

        delegateExecution.setVariable("enemyWarriors", enemyWarriors)
        delegateExecution.setVariable("warriors", army.size)
        delegateExecution.setVariable("army", army)
    }
}