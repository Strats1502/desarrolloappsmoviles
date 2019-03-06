package itlapps.juanjoseesva.changeactivities

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.second_activity.*

public class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        button.setOnClickListener{
            finish()
        }
    }

}