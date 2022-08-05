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
}
