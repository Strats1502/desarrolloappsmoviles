package itlapps.juanjoseesva.changeactivities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import itlapps.juanjoseesva.changeactivities.model.Person
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_next_activity.setOnClickListener {
            val person = Person("Juan José", "Estrada", "Programmer")

            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
        }
    }
}
