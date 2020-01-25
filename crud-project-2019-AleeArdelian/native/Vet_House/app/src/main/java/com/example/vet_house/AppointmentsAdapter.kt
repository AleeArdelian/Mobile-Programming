package com.example.vet_house

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_pets_appointments.view.*
import kotlinx.android.synthetic.main.update_appointment.view.*
import kotlinx.android.synthetic.main.update_doctors.view.*
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NAME_SHADOWING")
class AppointmentsAdapter(mainContext: Context, var db: AppDatabase, val appList: ArrayList<Appointment>) : RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {
    var mContext = mainContext
    var datab = db

    override fun getItemCount(): Int {
        return appList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(appList[position])

        holder.editButton?.setOnClickListener {

            val appDAo = datab.appointment()
            val inflater = LayoutInflater.from(mContext)
            val viewDialog = inflater.inflate(R.layout.update_appointment, null)

            val builder = AlertDialog.Builder(mContext)
                .setTitle("Update appointment")
                .setView(viewDialog)

            val alertDialog = builder.show()


            viewDialog.button_calendar_up.setOnClickListener {

                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(
                    mContext,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        viewDialog.date_textview_up.text = "$dayOfMonth/$monthOfYear/$year"
                    },
                    year,
                    month,
                    day
                )
                dpd.show()
            }

            val medCheck = viewDialog.medcheck_up.text.toString()

            viewDialog.update_app_button.setOnClickListener {
                appList[holder.adapterPosition].doctor_name = appList[holder.adapterPosition].doctor_name
                appList[holder.adapterPosition].date = viewDialog.date_textview_up.text.toString()
                appList[holder.adapterPosition].medical_check = medCheck

                appDAo.updateAppointment(appList[holder.adapterPosition])
                notifyDataSetChanged()
                Toast.makeText(mContext, "Appointment details updated!", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.appointment_list_layout, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var editButton: Button? = null
        init {
            editButton = itemView.findViewById(R.id.edit_button_ap)

        }

        fun bindItems(appoint: Appointment) {
            val textViewName = itemView.findViewById(R.id.textViewDoctorName) as TextView
            val textViewMedicalCheck  = itemView.findViewById(R.id.textViewMedicalCheck) as TextView
            val textViewDate  = itemView.findViewById(R.id.textViewDate) as TextView
            textViewName.text = appoint.doctor_name
            textViewMedicalCheck.text = appoint.medical_check
            textViewDate.text = appoint.date
        }
    }
}