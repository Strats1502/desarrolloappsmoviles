package itlapps.juanjoseesva.contacts.model

import io.realm.RealmObject
import java.io.Serializable

class Disease(): Serializable {
    var name: String = ""

    constructor(name: String):this() {
        this.name = name
    }
}