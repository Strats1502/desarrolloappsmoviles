package itlapps.juanjoseesva.contacts.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import io.realm.Realm
import io.realm.RealmList
import itlapps.juanjoseesva.contacts.R
import itlapps.juanjoseesva.contacts.adapters.AdapterContact
import itlapps.juanjoseesva.contacts.model.Contact
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var adapter: AdapterContact? = null

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

        val manager: LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = AdapterContact(contacts, this) { contact ->
            val builder =  AlertDialog.Builder(this)
            builder.setTitle(contact.name)
            builder.setMessage("Nombre: ${contact.name} Apellido: ${contact.lastName1} Correo: ${contact.email}")
            builder.show()
        }

        rv_contacts.layoutManager = manager
        rv_contacts.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.mainactivity_menu, menu)

        val searchItem: MenuItem = menu!!.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.queryHint = "Buscar..."

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter!!.getFilter().filter(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter!!.getFilter().filter(p0)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}

