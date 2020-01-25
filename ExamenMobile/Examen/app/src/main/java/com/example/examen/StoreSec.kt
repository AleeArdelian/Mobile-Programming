package com.example.examen

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoreSec : AppCompatActivity() {

    lateinit var service : ObjAPI
    lateinit var adaptor : ObjAdaptor
    lateinit var objList : ArrayList<Object>
    lateinit var context : Context
    lateinit var recyclerView : RecyclerView
    lateinit var localDB : AppDatabase
    lateinit var progressBar : ProgressBar

    private fun getPending(){
        progressBar.visibility = View.VISIBLE

        val call = service.getTypes("application/json")
        call.enqueue(object : Callback<List<Object>> {
            override fun onFailure(call: Call<List<Object>>, t: Throwable) {
                Log.d("get", "FAILED")
                progressBar.visibility = View.GONE
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Object>>, response: Response<List<Object>>) {
                if(response.isSuccessful()) {
                    Log.d("get", "SUCCESS")
                    objList = ArrayList(response.body()!!)
                    adaptor = ObjAdaptor(context, localDB, objList)
                    recyclerView.adapter = adaptor
                    progressBar.visibility = View.GONE
                }else{
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
        })
    }

    private fun noInternetConnection(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        var isConnected = false
        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnectedOrConnecting
        }
        if (!isConnected) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_sec)
        context = this

        recyclerView = findViewById(R.id.recycle_view)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.115.50:2302")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ObjAPI::class.java)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        localDB = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "maindb1.db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()

        progressBar = findViewById(R.id.pBar)

        if(!noInternetConnection()){
            getPending()
        }
    }
}
