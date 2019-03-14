package itlapps.juanjoseesva.contacts.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable
import java.util.ArrayList

@RealmClass
open class Contact(): Serializable, RealmObject() {
    var name: String = ""
    var lastName1: String = ""
    var lastName2: String = ""
    var birthday: String = ""
    var phone: String = ""
    var email: String = ""
    var address: String = ""
    var paysheet: String = ""
    var area: String = ""
    var job: String = ""
    var state: String = ""
    var nss: String = ""
    //var diseases: List<Disease> = ArrayList()
    //var emergencyContact: Contact = Contact()
    var photoURL: String = ""
    var scholarship: String = ""
    var nationality: String = ""
    var status: String = ""

    constructor(name: String, lastName1: String, lastName2: String, birthday: String, phone: String,
                email: String, address: String, paysheet: String, area: String, job: String, state: String,
                nss: String, /*diseases: List<Disease>,*/ emergencyContact: Contact, photoURL: String,
                scholarship: String, nationality: String, status: String):this() {
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
        //this.diseases = diseases
        //this.emergencyContact = emergencyContact
        this.photoURL = photoURL
        this.scholarship = scholarship
        this.nationality = nationality
        this.status = status
    }

    constructor(name: String, lastName1: String, phone: String, email: String, address: String):this() {
        this.name = name
        this.lastName1 = lastName1
        this.phone = phone
        this.email = email
        this.address = address
    }

}