package itlapps.juanjoseesva.contacts.activities

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import io.realm.Realm
import itlapps.juanjoseesva.contacts.R
import itlapps.juanjoseesva.contacts.model.Contact
import kotlinx.android.synthetic.main.activity_addcontact.*

class AddContactActivity : AppCompatActivity() {
    var realm: Realm = Realm.getDefaultInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcontact)

        Realm.init(this)

        setAdapters()

        iv_photo.setOnClickListener {
            selectPhoto()
        }

        button_save.setOnClickListener {
            saveContact()
        }
    }

    private fun setAdapters() {
        val context: Context = this
        val spinnerItem: Int = android.R.layout.simple_spinner_item
        val spinnerDropdownItem: Int = android.R.layout.simple_spinner_dropdown_item

        val adapterAreas: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context,
            R.array.areas, spinnerItem)
        adapterAreas.setDropDownViewResource(spinnerDropdownItem)

        val adapterJobs: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context,
            R.array.jobs, spinnerItem)
        adapterJobs.setDropDownViewResource(spinnerDropdownItem)

        val adapterScholarship: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context,
            R.array.schooling, spinnerItem)
        adapterScholarship.setDropDownViewResource(spinnerDropdownItem)

        val adapterNationality: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context,
            R.array.nationalities, spinnerItem)
        adapterNationality.setDropDownViewResource(spinnerDropdownItem)

        val adapterStatuses: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context,
            R.array.statuses, spinnerItem)
        adapterStatuses.setDropDownViewResource(spinnerDropdownItem)

        spinner_area.adapter = adapterAreas
        spinner_job.adapter = adapterJobs
        spinner_scholarship.adapter = adapterScholarship
        spinner_nationality.adapter = adapterNationality
        spinner_status.adapter = adapterStatuses
    }

    fun selectPhoto() {
        val bottomMenu: BottomSheetDialog = BottomSheetDialog(this)
        bottomMenu.show()
    }

    fun saveContact() {
        val name: String = et_name.text.toString()
        val lastName1: String = et_lastname1.text.toString()
        val lastName2: String = et_lastname2.text.toString()
        val birthday: String = et_birthday.text.toString()
        val phone: String = et_phone.text.toString()
        val email: String = et_email.text.toString()
        val address: String = et_address.text.toString()
        val paysheet: String = et_paysheet.text.toString()
        val area: String = spinner_area.selectedItem.toString()
        val job: String = spinner_job.selectedItem.toString()
        val state: String = et_state.text.toString()
        val nss: String = et_nss.text.toString()
        val scholarship: String = spinner_scholarship.selectedItem.toString()
        val nationality: String = spinner_nationality.selectedItem.toString()
        val status: String = spinner_status.selectedItem.toString()

        realm.beginTransaction()
        val contact = realm.createObject(Contact::class.java)
        contact.name = name
        contact.lastName1 = lastName1
        contact.lastName2 = lastName2
        contact.birthday = birthday
        contact.phone = phone
        contact.email = email
        contact.address = address
        contact.paysheet = paysheet
        contact.area = area
        contact.job = job
        contact.state = state
        contact.nss = nss
        contact.scholarship = scholarship
        contact.nationality = nationality
        contact.status = status
        realm.commitTransaction()

        this.finish()
    }

}