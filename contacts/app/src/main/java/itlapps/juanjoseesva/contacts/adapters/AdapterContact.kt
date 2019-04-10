package itlapps.juanjoseesva.contacts.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import io.realm.Realm
import io.realm.RealmList
import itlapps.juanjoseesva.contacts.R
import itlapps.juanjoseesva.contacts.model.Contact
import kotlinx.android.synthetic.main.item_rv_contact.view.*

class AdapterContact(val contacts: List<Contact>, val context: Context, val listener: (Contact) -> Unit) : RecyclerView.Adapter<AdapterContact.AdapterContactViewHolder>(){
    private var searchList: List<Contact>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdapterContactViewHolder = AdapterContactViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_contact, p0, false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(p0: AdapterContactViewHolder, p1: Int) = p0.bind(contacts[p1]!!, listener)


    class AdapterContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contact: Contact, listener: (Contact) -> Unit) = with(itemView) {
            tv_name.text = "${contact.name} ${contact.lastName1} ${contact.lastName2}"
            tv_paysheetrv.text = "N° nomina: ${contact.paysheet}"
            val decodedString = Base64.decode(contact.photoURL, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            iv_contact.setImageBitmap(decodedByte)
            setOnClickListener {
                listener(contact)
            }
        }
    }
}