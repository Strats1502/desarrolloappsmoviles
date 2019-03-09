package itlapps.juanjoseesva.changeactivities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_next_activity.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            val myObject = MyObject()
            myObject.name = "Juan Jose"
            myObject.lastname = "Estrada"

            intent.putExtra("object", myObject)
            startActivity(intent)
        }
    }
}
