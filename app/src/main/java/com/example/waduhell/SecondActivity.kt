package com.example.waduhell

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        val label = intent.getStringExtra(MainActivity.EXTRA_LABEL) ?: "(no label)"
        findViewById<TextView>(R.id.selectedText).text = label
        findViewById<Button>(R.id.backBtn).setOnClickListener { finish() }
    }
}
