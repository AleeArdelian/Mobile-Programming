package com.example.examen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_object.*

class AddObject : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_object)

        add_obj_button.setOnClickListener {
            when {
                add_details_field.text.isEmpty() or
                        add_stat_field.text.isEmpty() or
                        add_age_field.text.isEmpty() or
                        add_type_field.text.isEmpty()
                -> Toast.makeText(
                    this,
                    "You must fill in all the requested fields ",
                    Toast.LENGTH_LONG
                ).show()
                else -> {
                    val data = Intent()
                    data.putExtra("details", add_details_field.text.toString())
                    data.putExtra("age", add_age_field.text.toString())
                    data.putExtra("type", add_type_field.text.toString())
                    data.putExtra("status", add_stat_field.text.toString())
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }
            }
        }
        add_cancel_btn.setOnClickListener {
            val data = Intent()
            setResult(Activity.RESULT_CANCELED, data)
            finish()
        }
    }
}
