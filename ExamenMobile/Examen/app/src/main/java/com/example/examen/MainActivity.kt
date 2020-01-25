package com.example.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

var userId=3

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client_section.setOnClickListener{
            val intent = Intent(this, ClientSec::class.java)
            startActivity(intent)
        }

        store_section.setOnClickListener{
            val intent = Intent(this, StoreSec::class.java)
            startActivity(intent)
        }

        manager_section.setOnClickListener{
            val intent = Intent(this, ManagersSec::class.java)
            startActivity(intent)

        }
    }
}
