package itlapps.juanjoseesva.calculator

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var lastResult = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_clear_c.setOnClickListener {
            tv_entry.text = getString(R.string.number_0)
        }

        button_clear_ce.setOnClickListener {
            tv_entry.text = getString(R.string.number_0)
            tv_last_result.text = getString(R.string.number_0)
            lastResult = 0.0
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
            operation(getString(R.string.operation_add))
        }

        button_operation_sub.setOnClickListener {
            operation(getString(R.string.operation_sub))
        }

        button_operation_mul.setOnClickListener {
            operation(getString(R.string.operation_mul))
        }

        button_operation_div.setOnClickListener {
            operation(getString(R.string.operation_div))
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

    @SuppressLint("SetTextI18n")
    fun operation(operationSign : String) {
        val currentNumber = tv_entry.text.toString()

        if (currentNumber.toDouble() > 0 && !currentNumber.last().toString().equals(getString(R.string.point))) {
            if (lastResult > 0) {
                when(operationSign) {
                    getString(R.string.operation_add) -> lastResult += currentNumber.toDouble()
                    getString(R.string.operation_sub) -> lastResult -= currentNumber.toDouble()
                    getString(R.string.operation_mul) -> lastResult *= currentNumber.toDouble()
                    getString(R.string.operation_div) -> lastResult /= currentNumber.toDouble()
                }

                tv_last_result.text = String.format("%.2f", lastResult)
                tv_entry.text = getString(R.string.number_0)
            } else {
                lastResult = currentNumber.toDouble()

                tv_last_result.text = String.format("%.2f", lastResult)
                tv_entry.text = getString(R.string.number_0)
            }
        }


    }
}
