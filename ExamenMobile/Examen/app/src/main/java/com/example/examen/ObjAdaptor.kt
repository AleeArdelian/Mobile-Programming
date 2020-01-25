package com.example.examen

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ObjAdaptor(mainContext: Context, var db: AppDatabase, private val objList: ArrayList<Object>) : RecyclerView.Adapter<ObjAdaptor.ViewHolder>() {

    lateinit var service : ObjAPI
    private var localDB = db
    private var context = mainContext

    private fun updateServer(obj:Object){
        service.postObject("application/json", obj.id!!).enqueue(object:
            Callback<Object> {
            override fun onFailure(call: Call<Object>, t: Throwable) {
                Log.d("reserve", "FAILED")
            }
            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if (response.isSuccessful()) {
                    Log.d("reserve", "SUCCESS")
                    val newObj = response.body()!!
                    localDB.objects().insert(newObj)
                }else {
                    when (response.code()) {
                        404 -> Toast.makeText(
                            context,
                            "not found",
                            Toast.LENGTH_SHORT
                        ).show()
                        500 -> Toast.makeText(
                            context,
                            "server broken",
                            Toast.LENGTH_SHORT
                        ).show()
                        else -> Toast.makeText(
                            context,
                            "unknown error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    response.errorBody()?.string()
                    Log.d("error message", response.errorBody()?.string())
                }
            }
        }) //notifyDataSetChanged()
    }

    private fun noInternetConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        var isConnected = false
        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnectedOrConnecting
        }
        if (!isConnected) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }


    override fun getItemCount(): Int {
        return objList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(objList[position])

        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.115.50:2302")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ObjAPI::class.java)

        if(!noInternetConnection()) {
            holder.cardButton?.setOnClickListener {
                val clickedObj = objList[holder.adapterPosition]
                updateServer(clickedObj)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardButton: FloatingActionButton? = null
        init {
            cardButton = itemView.findViewById<FloatingActionButton>(R.id.card_edit_button)
        }

        @SuppressLint("SetTextI18n")
        fun bindItems(obj: Object) {
            val cardTextView1 = itemView.findViewById(R.id.card_header1) as TextView
            val cardtextView2  = itemView.findViewById(R.id.card_header2) as TextView
            val cardtextView3 = itemView.findViewById(R.id.card_header3) as TextView
            cardTextView1.text = "Details: "+ obj.details + "Status: " +obj.status
            cardtextView2.text = "User: "+ obj.user.toString()
            cardtextView3.text = "Age: " + obj.age.toString() + " Type: " + obj.type
        }
    }
}