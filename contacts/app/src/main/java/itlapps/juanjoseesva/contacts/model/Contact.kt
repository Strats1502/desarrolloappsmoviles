package itlapps.juanjoseesva.contacts.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import java.io.Serializable
import java.util.ArrayList

@RealmClass
open class Contact(): Serializable, RealmObject() {
    @PrimaryKey
    @Required
    var paysheet: String = ""
    var name: String = ""
    var lastName1: String = ""
    var lastName2: String = ""
    var birthday: String = ""
    var phone: String = ""
    var email: String = ""
    var address: String = ""
    var area: String = ""
    var job: String = ""
    var state: String = ""
    var nss: String = ""
    var diseases: String = ""
    var photoURL: String = ""
    var scholarship: String = ""
    var nationality: String = ""
    var status: String = ""
    var emergencyContactName: String = ""
    var emergencyContactPhone: String = ""

    constructor(name: String, lastName1: String, lastName2: String, birthday: String, phone: String,
                email: String, address: String, paysheet: String, area: String, job: String, state: String,
                nss: String, diseases: String, photoURL: String,
                scholarship: String, nationality: String, status: String, emergencyContactName: String,
                emergencyContactPhone: String):this() {
        this.name = name
        this.lastName1 = lastName1
        this.lastName2 = lastName2
        this.birthday = birthday
        this.phone = phone
        this.email = email
        this.address = address
        this.paysheet = paysheet
        this.area = area
        this.job = job
        this.state = state
        this.nss = nss
        this.diseases = diseases
        this.photoURL = photoURL
        this.scholarship = scholarship
        this.nationality = nationality
        this.status = status
        this.emergencyContactName = emergencyContactName
        this.emergencyContactPhone = emergencyContactPhone
    }

}