package com.example.examen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ObjAdaptor2(mainContext: Context, var db: AppDatabase, private val objList: ArrayList<myObj>) : RecyclerView.Adapter<ObjAdaptor2.ViewHolder>() {

    lateinit var service: ObjAPI
    private var localDB = db
    private var context = mainContext


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(objList[position])
    }

    override fun getItemCount(): Int {
        return objList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card2, parent, false)
        return ViewHolder(v)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        var cardButton: FloatingActionButton? = null
//        init {
//            cardButton = itemView.findViewById<FloatingActionButton>(R.id.card_edit_button)
//        }
        @SuppressLint("SetTextI18n")
        fun bindItems(obj: myObj) {
            val cardTextView1 = itemView.findViewById(R.id.card2_header1) as TextView
//            val cardtextView2  = itemView.findViewById(R.id.card_header2) as TextView
//            val cardtextView3 = itemView.findViewById(R.id.card_header3) as TextView
            cardTextView1.text = "User: " + obj.id.toString() + "Orders: " +obj.ord.toString()
//            cardtextView2.text = "Details "+ obj.details
//            cardtextView3.text = "Type: " + obj.type + " Rating: " + obj.rating.toString()
        }
    }
}