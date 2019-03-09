package itlapps.juanjoseesva.calculator

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_clear_c.setOnClickListener {
            tv_entry.text = getString(R.string.number_0)
        }

        button_number_0.setOnClickListener {
            setNumber(getString(R.string.number_0))
        }

        button_number_1.setOnClickListener {
            setNumber(getString(R.string.number_1))
        }

        button_number_2.setOnClickListener {
            setNumber(getString(R.string.number_2))
        }

        button_number_3.setOnClickListener {
            setNumber(getString(R.string.number_3))
        }

        button_number_4.setOnClickListener {
            setNumber(getString(R.string.number_4))
        }

        button_number_5.setOnClickListener {
            setNumber(getString(R.string.number_5))
        }

        button_number_6.setOnClickListener {
            setNumber(getString(R.string.number_6))
        }

        button_number_7.setOnClickListener {
            setNumber(getString(R.string.number_7))
        }

        button_number_8.setOnClickListener {
            setNumber(getString(R.string.number_8))
        }

        button_number_9.setOnClickListener {
            setNumber(getString(R.string.number_9))
        }

        button_point.setOnClickListener {
            setPoint(getString(R.string.point))
        }

        button_operation_add.setOnClickListener {
            add()
        }

    }

    @SuppressLint("SetTextI18n")
    fun setNumber(number : String) {
        val currentNumber = tv_entry.text

        if (currentNumber[0].toString().equals(getString(R.string.number_0))) {
            if (!currentNumber.contains(getString(R.string.point))) {
                val new = tv_entry.text.replaceRange(0, 1, "")
                tv_entry.text = new
            }
        }

        tv_entry.text = tv_entry.text.toString() + number
    }

    @SuppressLint("SetTextI18n")
    fun setPoint(point : String) {
        val currentNumber = tv_entry.text
        if (currentNumber.isNotEmpty() && currentNumber[0].isDigit() && !currentNumber.contains(point)) {
            tv_entry.text = tv_entry.text.toString() + point
        }
    }

    fun add() {
        val currentNumber = tv_entry.text

        Log.e("First", currentNumber.first().toString())
        Log.e("Last", currentNumber.last().toString().equals(getString(R.string.number_0)).toString())

        if (currentNumber.isNotEmpty() &&
            !currentNumber.last().equals(getString(R.string.point)) &&
            !currentNumber.first().equals(getString(R.string.number_0))) {
            tv_entry.text = tv_entry.text.toString() + getString(R.string.operation_add)
        }
    }
}
