package itlapps.juanjoseesva.changeactivities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import itlapps.juanjoseesva.changeactivities.model.Person
import kotlinx.android.synthetic.main.second_activity.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        val person = intent.getSerializableExtra("person") as? Person

        tv_persondata.text = "Name: " + person?.name + "\nLast name:" + person?.lastName + "\nJob: " + person?.job

        button.setOnClickListener{
            finish()
        }
    }

}