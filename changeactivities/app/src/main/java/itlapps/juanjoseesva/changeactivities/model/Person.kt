package itlapps.juanjoseesva.changeactivities.model

import java.io.Serializable

class Person(name: String) : Serializable{
    var name: String = ""
    var lastName: String = ""
    var job: String = ""

    init {
        this.name = name
    }

    constructor(name: String, lastName: String):this(name) {
        this.lastName = lastName
    }

    constructor(name: String, lastName: String, job: String):this(name) {
        this.lastName = lastName
        this.job = job
    }

}