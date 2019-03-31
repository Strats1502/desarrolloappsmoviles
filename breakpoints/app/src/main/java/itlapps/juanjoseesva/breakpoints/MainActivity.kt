package itlapps.juanjoseesva.breakpoints

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

//Set the max number of elements in list
private const val MAX_NUMBER_OF_ELEMENTS = 10

class MainActivity : AppCompatActivity() {
    //List to put all my numbers
    private var myList: MutableList<Int> = mutableListOf<Int>()

    //Create a simple range from 0 to MAX_NUMBER_OF_ELEMENTS
    private val range = 0..MAX_NUMBER_OF_ELEMENTS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Call to add list method
        addToList()

        //Print the list information
        printListInformation()
    }

    /**
     * Add a random number to the list
     */
    fun addToList() {
        for (i in range) {
            val randomNumber = (1..100).random()
            myList.add(randomNumber)
        }
    }

    /**
     * Prints the list information
     */
    fun printListInformation() {
        for (i in range) {
            Log.d("Index", myList.indexOf(myList[i]).toString())
            Log.d("Element", myList[i].toString())
        }

        Log.d("Final list size", myList.size.toString())
    }
}
