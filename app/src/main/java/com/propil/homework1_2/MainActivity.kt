package com.propil.homework1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var counter = 1
    private var rollFlag = false

    private lateinit var textViewHint: TextView
    private lateinit var diceSide: TextView
    private lateinit var rollButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewHint = findViewById(R.id.textview)
        diceSide = findViewById(R.id.dice_side)
        rollButton = findViewById(R.id.roll_button)

        rollButton.setOnClickListener() {
            when (rollFlag) {
                false -> {
                    rollButton.text = getString(R.string.roll_button_stop)
                    rollFlag = true

                    GlobalScope.launch {
                        while (rollFlag) {
                            counter++
                            if (counter == 7) counter = 1
                            GlobalScope.launch(Dispatchers.IO) {
                                diceSide.post { diceSide.text = "$counter" }
                            }
                        }
                        delay(500L)
                    }
                }
                true -> {
                    rollButton.text = getString(R.string.roll_button_start)
                    rollFlag = false
                }
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(COUNTER_KEY, counter)
            putString(TEXT_BUTTON_KEY, rollButton.text.toString())
            putBoolean(ROLL_FLAG_STATE, rollFlag)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            diceSide.text = getInt(COUNTER_KEY).toString()
            rollButton.text = getString(TEXT_BUTTON_KEY)
            rollFlag = getBoolean(ROLL_FLAG_STATE)
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        private const val COUNTER_KEY = "COUNTER_KEY"
        private const val TEXT_BUTTON_KEY = "TEXT_BUTTON_KEY"
        private const val ROLL_FLAG_STATE = "ROLL_FLAG_STATE"
    }
}
