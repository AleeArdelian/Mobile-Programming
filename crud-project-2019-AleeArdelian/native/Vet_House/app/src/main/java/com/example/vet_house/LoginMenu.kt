package com.example.vet_house

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_login_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)


        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "maindb1.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        val doctors = db.doctor().getAll() as ArrayList<Doctor>
        val users = db.user().getAll() as ArrayList<User>


        login_button.setOnClickListener {
            val emailUser = login_email.text.toString()
            val existsUser = users.find { x -> x.email == emailUser }
            val existsDoctor = doctors.find { x -> x.email == emailUser }

            if (existsDoctor != null) {
                userRole = "DOCTOR"
                val intent = Intent(this, StartPage::class.java)
                startActivity(intent)
            } else {
                if (existsUser != null) {
                    userRole = "OWNER"
                    val intent = Intent(this, StartPage::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Invalid credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
