package com.example.mymobileapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mymobileapp.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)

        val entity = intent.getStringExtra("entity")

    }
}
