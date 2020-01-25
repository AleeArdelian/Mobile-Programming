package com.example.vet_house

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast


class AddDoctor  : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_doctors)

        val doctorFName = findViewById<TextView>(R.id.addDoctor_doctor_fName)
        val doctorLName  = findViewById<TextView>(R.id.addDoctor_doctor_lname)
        val doctorSpecilaity = findViewById<TextView>(R.id.addDoctor_doctor_speciality)
        val doctEmail = findViewById<TextView>(R.id.addDoctor_doctor_email)
        val docPass = findViewById<TextView>(R.id.addDoc_pass)

        val addButton = findViewById<Button>(R.id.addDoctor_add_button)
        val cancelButton = findViewById<Button>(R.id.addDoctor_cancel_button)

        addButton.setOnClickListener {
            when {
                doctorFName.text.isEmpty() or
                        doctorSpecilaity.text.isEmpty() or
                        doctorLName.text.isEmpty() or
                        doctEmail.text.isEmpty()
                -> Toast.makeText(
                    this,
                    "You must fill in all the requested fields ",
                    Toast.LENGTH_LONG
                ).show()
                else -> {
                    val data = Intent()
                    data.putExtra("first_name",doctorFName.text.toString())
                    data.putExtra("last_name",doctorLName.text.toString())
                    data.putExtra("spec",doctorSpecilaity.text.toString())
                    data.putExtra("email",doctEmail.text.toString())
                    data.putExtra("pass",docPass.text.toString())
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }
            }
        }
        cancelButton.setOnClickListener {
            val data = Intent()
            setResult(Activity.RESULT_CANCELED, data)
            finish()
        }
    }
}