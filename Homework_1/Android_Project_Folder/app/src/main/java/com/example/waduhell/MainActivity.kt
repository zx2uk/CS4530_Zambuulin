package com.example.waduhell

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

/**
 * Main screen: shows 5 buttons.
 * Tapping any button launches SecondActivity and passes the button text via [EXTRA_LABEL].
 */
class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LABEL = "extra_button_label"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val buttons: List<Button> = listOf(
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5)
        )

        val goToSecond: (String) -> Unit = { label ->
            val i = Intent(this, SecondActivity::class.java).apply {
                putExtra(EXTRA_LABEL, label)
            }
            startActivity(i)
        }

        buttons.forEach { btn ->
            btn.setOnClickListener { goToSecond(btn.text.toString()) }
        }
    }
}
