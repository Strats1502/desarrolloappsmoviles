package itlapps.juanjoseesva.contacts.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import io.realm.Realm
import io.realm.RealmList
import itlapps.juanjoseesva.contacts.R
import itlapps.juanjoseesva.contacts.model.Contact
import kotlinx.android.synthetic.main.item_rv_contact.view.*

class AdapterContact(val contacts: RealmList<Contact>, val context: Context, val listener: (Contact) -> Unit) : RecyclerView.Adapter<AdapterContact.AdapterContactViewHolder>(){
    private var searchList: RealmList<Contact>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AdapterContactViewHolder = AdapterContactViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_contact, p0, false))

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(p0: AdapterContactViewHolder, p1: Int) = p0.bind(contacts[p1]!!, listener)

    fun getFilter(): Filter {

        Realm.init(context)
        //val config = RealmConfiguration.Builder().name("contact.realm").build()
        var realm = Realm.getDefaultInstance()

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if (charString.isEmpty()) {
                    searchList = contacts
                } else {
                    val filteredList = ArrayList<Contact>()
                    for (row  in realm!!.where(Contact::class.java).findAll()) {
                        if (row.name!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }

                    searchList = filteredList as? RealmList<Contact>
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchList = results!!.values as? RealmList<Contact>
                notifyDataSetChanged()
            }
        }
    }

    class AdapterContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contact: Contact, listener: (Contact) -> Unit) = with(itemView) {
            tv_name.text = contact.name
            setOnClickListener {
                listener(contact)
            }
        }
    }
}