package com.bimdata.bimdataviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.launchButton)

        button.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ViewerActivity::class.java))
        })
    }
}