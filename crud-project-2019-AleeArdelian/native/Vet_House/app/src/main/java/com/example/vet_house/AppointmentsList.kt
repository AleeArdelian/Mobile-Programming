package com.example.vet_house

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_pets_appointments.*
import kotlinx.android.synthetic.main.activity_pets_appointments.view.*
import kotlinx.android.synthetic.main.activity_pets_appointments.view.date_textview
import kotlinx.android.synthetic.main.add_doctors.view.*
import java.util.*
import kotlin.collections.ArrayList

var guidA = 10
class AppointmentsList : AppCompatActivity() {

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view_ap)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager?

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "maindb1.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()


        //db.appointment().deleteAll()
//        db.appointment().insert(Appointment(guid++,"Diana Achim","General check-up", "1/2/2018"))
//        db.appointment().insert(Appointment(guidA++,"Szibil Nicoara","Deparazitare", "2/11/2017"))
//        db.appointment().insert(Appointment(guidA++,"Sofia Morar","Teeth cleaning", "10/10/2017"))
//        db.appointment().insert(Appointment(guidA++,"Razvan Pascalau","Feline", "01/04/2019"))
//        db.appointment().insert(Appointment(guidA++,"Alexandru Balea", "Canine","07/08/2019"))
//        db.appointment().insert(Appointment(guidA++,"Stefan Florin", "Birds","25/12/2019"))

        val appoint = ArrayList(db.appointment().getAll())
        val adapter = AppointmentsAdapter(this,db, appoint)

        recyclerView.adapter = adapter

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                val app = appoint[position]
                db.appointment().delete(app)
                appoint.remove(app)
//                db.appointment().deleteAll()
//                for (tc in appoint) {
//                    db.appointment().insert(tc)
//                }
                adapter.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val fab = findViewById<FloatingActionButton>(R.id.fab_ap)

        fab.setOnClickListener {
            val dialog1 =
                LayoutInflater.from(this).inflate(R.layout.activity_pets_appointments, null)
            val builder = AlertDialog.Builder(this).setView(dialog1)

            val alertDialog = builder.show()

            val app_spinner = dialog1.spinner_names
            val doc_names = arrayOf("Diana Achim", "Sophia Morar", "Szibil Nicoara", "Razvan Pascalau")
            val adapter_array = ArrayAdapter(this, android.R.layout.simple_list_item_1, doc_names)
            app_spinner.adapter = adapter_array

            dialog1.button_calendar.setOnClickListener{
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                   dialog1.date_textview.text = "$dayOfMonth/$monthOfYear/$year"
                }, year, month, day)
                dpd.show()
            }

            dialog1.createApp_button.setOnClickListener {
                val date = dialog1.date_textview.text.toString()
                val doc_name = app_spinner.selectedItem.toString()
                val check = dialog1.medcheck_editText.text.toString()

                val newApp = Appointment(guidA++, doc_name, check, date)
                db.appointment().insert(newApp)
                appoint.add(newApp)
//                db.appointment().deleteAll()
//                for (tc in appoint) {
//                    db.appointment().insert(tc)
//                }
                adapter.notifyDataSetChanged()
                alertDialog.dismiss()
            }
        }
    }
}
