package com.example.paxclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.ViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tvPid)
        val button = findViewById<Button>(R.id.appPid)
        viewModel.uiEvents.observe(this, Observer {
            tv.text = it.toString()
        })
        viewModel.uiEventsApp.observe(this, Observer {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })
        tv.setOnClickListener{
            viewModel.getPid()
        }

        button.setOnClickListener{
            viewModel.getAppPid()
        }
    }
}