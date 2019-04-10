package itlapps.juanjoseesva.contacts.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import io.realm.Case
import io.realm.Realm
import io.realm.RealmList
import itlapps.juanjoseesva.contacts.R
import itlapps.juanjoseesva.contacts.adapters.AdapterContact
import itlapps.juanjoseesva.contacts.model.Contact
import kotlinx.android.synthetic.main.activity_addcontact.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alertdialog_contact_detail.view.*
import kotlinx.android.synthetic.main.item_rv_contact.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    //private var adapter: AdapterContact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshList()

        fab_addcontact.setOnClickListener {
            val intent = Intent(this@MainActivity, AddContactActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun refreshList() {
        Realm.init(this)
        //val config = RealmConfiguration.Builder().name("contact.realm").build()
        var realm = Realm.getDefaultInstance()

        val allContacts = realm.where(Contact::class.java).findAll()

        val contacts: RealmList<Contact> = RealmList()
        contacts.addAll(allContacts.subList(0, allContacts.size))

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        rv_contacts.layoutManager = manager
        rv_contacts.adapter = makeAdapter(contacts)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.mainactivity_menu, menu)

        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.queryHint = "Buscar..."

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //adapter!!.getFilter().filter(p0)

                val realm = Realm.getDefaultInstance()


                val allContacts = realm.where(Contact::class.java).contains("paysheet", p0, Case.INSENSITIVE).or()
                    .contains("name", p0, Case.INSENSITIVE).or()
                    .contains("lastName1", p0, Case.INSENSITIVE).or()
                    .contains("lastName2", p0, Case.INSENSITIVE).or()
                    .contains("email", p0, Case.INSENSITIVE).findAll()

                val contacts: RealmList<Contact> = RealmList()
                contacts.addAll(allContacts.subList(0, allContacts.size))

                /*adapter = AdapterContact(contacts, this@MainActivity) { contact ->
                    val builder =  AlertDialog.Builder(this@MainActivity)
                    val inflater = this@MainActivity.layoutInflater
                    val dialogView = inflater.inflate(R.layout.alertdialog_contact_detail, null)

                    builder.setView(dialogView)

                    val decodedString = Base64.decode(contact.photoURL, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    dialogView.iv_contactt.setImageBitmap(decodedByte).contains("name", p0, Case.INSENSITIVE).findAll()

                    dialogView.tv_namecontact.text = "${contact.name} ${contact.lastName1} ${contact.lastName2}"
                    dialogView.tv_phone.text = "${contact.phone}"
                    dialogView.tv_email.text = "${contact.email}"
                    dialogView.tv_jobarea.text = "Área: ${contact.area} Puesto: ${contact.job}"

                    /*builder.setTitle(contact.name)
                    builder.setMessage("Nombre: ${contact.name} Apellido: ${contact.lastName1} Correo: ${contact.email}")*/
                    builder.show()
                }*/


                //rv_contacts.layoutManager = manager
                rv_contacts.adapter = makeAdapter(contacts)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                //adapter!!.getFilter().filter(p0)
                val realm = Realm.getDefaultInstance()

                val allContacts = realm.where(Contact::class.java).contains("paysheet", p0, Case.INSENSITIVE).or()
                    .contains("name", p0, Case.INSENSITIVE).or()
                    .contains("lastName1", p0, Case.INSENSITIVE).or()
                    .contains("lastName2", p0, Case.INSENSITIVE).or()
                    .contains("email", p0, Case.INSENSITIVE).findAll()


                val contacts: RealmList<Contact> = RealmList()
                contacts.addAll(allContacts.subList(0, allContacts.size))

                /*adapter = AdapterContact(contacts, this@MainActivity) { contact ->
                    val builder =  AlertDialog.Builder(this@MainActivity)
                    val inflater = this@MainActivity.layoutInflater
                    val dialogView = inflater.inflate(R.layout.alertdialog_contact_detail, null)

                    builder.setView(dialogView)

                    val decodedString = Base64.decode(contact.photoURL, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    dialogView.iv_contactt.setImageBitmap(decodedByte)

                    dialogView.tv_namecontact.text = "${contact.name} ${contact.lastName1} ${contact.lastName2}"
                    dialogView.tv_phone.text = "${contact.phone}"
                    dialogView.tv_email.text = "${contact.email}"
                    dialogView.tv_jobarea.text = "Área: ${contact.area} Puesto: ${contact.job}"

                    /*builder.setTitle(contact.name)
                    builder.setMessage("Nombre: ${contact.name} Apellido: ${contact.lastName1} Correo: ${contact.email}")*/
                    builder.show()
                }*/


                //rv_contacts.layoutManager = manager
                rv_contacts.adapter = makeAdapter(contacts)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun makeAdapter(contacts: RealmList<Contact>): AdapterContact {
        val realm = Realm.getDefaultInstance()

        return AdapterContact(contacts, this) { contact ->
            val builder =  AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val dialogView = inflater.inflate(R.layout.alertdialog_contact_detail, null)

            builder.setView(dialogView)

            val decodedString = Base64.decode(contact.photoURL, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            dialogView.iv_contactt.setImageBitmap(decodedByte)

            dialogView.tv_namecontact.text = "Nombre: ${contact.name} ${contact.lastName1} ${contact.lastName2}"
            dialogView.tv_paysheet.text = "N° nomina: ${contact.paysheet}"
            dialogView.tv_nss.text = "NSS: ${contact.nss}"
            dialogView.tv_phone.text = "Telefono: ${contact.phone}"
            dialogView.tv_email.text = "Correo: ${contact.email}"
            dialogView.tv_area.text = "Área: ${contact.area}"
            dialogView.tv_job.text = "Puesto: ${contact.job}"
            dialogView.tv_birthday.text = "Fecha cumpleaños: ${contact.birthday}"

            if (contact.diseases != "") {
                dialogView.ll_containerdiseases.visibility = View.VISIBLE
                dialogView.tv_diseases.text = contact.diseases
            }

            if (contact.emergencyContactName != "") {
                dialogView.ll_containeremergencycontact.visibility = View.VISIBLE
                dialogView.tv_emergencycontactname.text = contact.emergencyContactName
                dialogView.tv_emergencycontactphone.text = contact.emergencyContactPhone
            }


            builder.setPositiveButton("Editar") {
                dialog, which ->
                val intent = Intent(this@MainActivity, EditContactActivity::class.java)
                intent.putExtra("paysheet", contact.paysheet)
                startActivity(intent)
            }

            builder.setNegativeButton("Eliminar") {
                dialog, which ->
                val builder =  AlertDialog.Builder(this)

                builder.setTitle("Eliminar contacto")
                builder.setMessage("¿Estas seguro de eliminar este contacto?")

                builder.setPositiveButton("Aceptar") {
                    dialog, which ->
                    realm.beginTransaction()
                    contact.deleteFromRealm()
                    realm.commitTransaction()

                    dialog.dismiss()
                    refreshList()
                }

                builder.setNegativeButton("Cancelar") {
                    dialog, which -> dialog.dismiss()
                }

                builder.show()

            }


            /*builder.setTitle(contact.name)
            builder.setMessage("Nombre: ${contact.name} Apellido: ${contact.lastName1} Correo: ${contact.email}")*/
            builder.show()
        }
    }


}

