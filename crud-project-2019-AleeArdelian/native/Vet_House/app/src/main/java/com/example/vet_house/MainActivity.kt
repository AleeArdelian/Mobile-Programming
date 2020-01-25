package com.example.vet_house

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_register_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var userRole :String = "OWNER"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        stars.onStart()

        register_speciality_edit.visibility = View.GONE

        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.115.50:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userService = retrofit.create(UserAPI::class.java)
        val doctorService = retrofit.create(DoctorApi::class.java)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "maindb1.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
//        db.user().deleteAll()
//        db.doctor().deleteAll()

        val users = db.user().getAll() as ArrayList<User>
        val doctors = db.doctor().getAll() as ArrayList<Doctor>

        register_checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                register_speciality_edit.visibility = View.VISIBLE
            else
                register_speciality_edit.visibility = View.GONE
        }

        register_button.setOnClickListener {
            val firstName = register_firstName.text.toString()
            val lastName = register_lastName.text.toString()
            val pass = pass_editText.text.toString()
            val email = email_editText.text.toString()
            val spec = register_speciality_edit.text.toString()

            val existsUser = users.find { x -> x.email == email }
            val existsDoctor = doctors.find { x -> x.email == email }

            if (existsUser != null && existsDoctor != null)
                Toast.makeText(
                    applicationContext,
                    "Email already registered. Click Log in",
                    Toast.LENGTH_SHORT
                ).show()
            else {
                if (firstName == "" || pass == "" || lastName == "") {
                    Toast.makeText(
                        applicationContext,
                        "Please fill in credentials",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    if (register_checkBox.isChecked && spec == "") {
                        Toast.makeText(
                            applicationContext,
                            "Please fill in doctor speciality",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (register_checkBox.isChecked) {
                            userRole = "DOCTOR"

                            val newUser = Doctor(null, firstName, lastName, email, pass, spec)
                            db.doctor().insert(newUser)
                            doctorService?.addDoctor(newUser, "application/json")
                                ?.enqueue(object : Callback<Doctor> {
                                    override fun onFailure(call: Call<Doctor>, t: Throwable) {
                                        Log.d("add", "FAILED")
                                    }

                                    override fun onResponse(
                                        call: Call<Doctor>,
                                        response: Response<Doctor>
                                    ) {
                                        Log.d("add", "SUCCESS")
                                    }
                                })
                            val intent = Intent(this, StartPage::class.java)
                            startActivity(intent)
                        } else {
                            val newUser = User(null, firstName, lastName, email, pass)
                            db.user().insert(newUser)
                            userService?.addUser(newUser, "application/json")
                                ?.enqueue(object : Callback<User> {
                                    override fun onFailure(call: Call<User>, t: Throwable) {
                                        Log.d("add", "FAILED")
                                    }

                                    override fun onResponse(
                                        call: Call<User>,
                                        response: Response<User>
                                    ) {
                                        Log.d("add", "SUCCESS")
                                    }
                                })
                            val intent = Intent(this, StartPage::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
            logIn_textView.setOnClickListener {
                val intent = Intent(this, LoginMenu::class.java)
                startActivity(intent)

            }
        }
    }

