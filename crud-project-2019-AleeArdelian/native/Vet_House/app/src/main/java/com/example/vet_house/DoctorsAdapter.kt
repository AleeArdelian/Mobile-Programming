package com.example.vet_house

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.add_doctors.view.*
import kotlinx.android.synthetic.main.update_doctors.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DoctorsAdapter( mainContext: Context, var db: AppDatabase, val doctorList: ArrayList<Doctor>) : RecyclerView.Adapter<DoctorsAdapter.ViewHolder>() {

    var mContext = mainContext
    var datab = db

    override fun getItemCount(): Int {
        return doctorList.size
    }

    @SuppressLint("InflateParams")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(doctorList[position])

        if(userRole == "OWNER")
            holder.mEditButton?.hide()
        else {
            holder.mEditButton?.setOnClickListener {

            val doctorDAo = datab.doctor()
            val inflater = LayoutInflater.from(mContext)
            val view = inflater.inflate(R.layout.update_doctors, null)

            val builder = AlertDialog.Builder(mContext)
                .setTitle("Update doctor")
                .setView(view)

            val alertDialog = builder.show()

            view.update_button_doc.setOnClickListener {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://172.30.115.50:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(DoctorApi::class.java)

                val oldDoctor = doctorList[holder.adapterPosition].id
                doctorList[holder.adapterPosition].firstName = view.doctor_FName.text.toString()
                doctorList[holder.adapterPosition].lastName = view.doc_lastName.text.toString()
                doctorList[holder.adapterPosition].speciality = view.speciality_v.text.toString()
                doctorDAo.updateDoctor(doctorList[holder.adapterPosition])

                service.putDoctor(doctorList[holder.adapterPosition], oldDoctor!!, "application/json").enqueue(object:
                    Callback<Doctor>{
                    override fun onFailure(call: Call<Doctor>, t: Throwable) {
                        Log.d("put", "FAILED")
                    }
                    override fun onResponse(call: Call<Doctor>, response: Response<Doctor>) {
                        Log.d("put", "SUCCESS")
                    }
                })

                notifyDataSetChanged()
                Toast.makeText(mContext, "Doctor details updated!", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }
            view.cancel_button.setOnClickListener {
                alertDialog.dismiss()
            }
        }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.doctor_list_layout, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mEditButton: FloatingActionButton? = null
         init {
             mEditButton = itemView.findViewById<FloatingActionButton>(R.id.edit_button)

         }

        fun bindItems(doctor: Doctor) {
            val textViewName = itemView.findViewById(R.id.textViewDoctorName) as TextView
            val textViewSpeciality  = itemView.findViewById(R.id.textViewSpeciality) as TextView
            textViewName.text = doctor.firstName + doctor.lastName
            textViewSpeciality.text = doctor.speciality
        }
    }
}