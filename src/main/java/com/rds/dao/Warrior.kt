package com.rds.dao

import java.io.Serializable

class Warrior : Serializable {
    var title: String
    var name: String
    var hp: Int
    var isAlive: Boolean


    constructor(title: String, name: String, hp: Int, isAlive: Boolean) {
        this.title = title
        this.name = name
        this.hp = hp
        this.isAlive = isAlive
    }



}