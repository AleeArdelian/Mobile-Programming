package com.example.examen

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_client_sec.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var addCode=200
class ClientSec : AppCompatActivity() {

    lateinit var service: ObjAPI
    lateinit var objList: ArrayList<Object>
    lateinit var context: Context
    lateinit var recyclerView1: RecyclerView
    lateinit var localDB: AppDatabase
    lateinit var progressBar: ProgressBar
    lateinit var adaptor: ObjAdaptor


    private fun addToServer(obj:Object){
        progressBar.visibility = View.VISIBLE

        service.addObject(obj, "application/json").enqueue(object : Callback<Object> {
            override fun onFailure(call: Call<Object>, t: Throwable) {
                Log.d("add", "FAILED")
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call<Object>, response: Response<Object>) {
                if(response.isSuccessful()) {
                    Log.d("add", "SUCCESS")
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

    private fun getFromServer()
    {
        progressBar.visibility = View.VISIBLE
        //var myO = myObj(userId)
        val call = service.getForUser("application/json", userId)
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

                    adaptor = ObjAdaptor(context, localDB,objList)// ArrayList(objList.take(10)))
                    recyclerView1.adapter = adaptor
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
        adaptor.notifyDataSetChanged()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_sec)

        context = this


        progressBar = findViewById(R.id.pBar2)
        recyclerView1 = findViewById(R.id.recycle_view2)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.115.50:2302")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ObjAPI::class.java)
        recyclerView1.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        localDB = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "maindb1.db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()

        val floatButton = findViewById<FloatingActionButton>(R.id.add_float_btn)

        floatButton.setOnClickListener {
            val intent = Intent(this, AddObject::class.java)
            startActivityForResult(intent, addCode)
        }

        if (!noInternetConnection()) {
            see_by_user.setOnClickListener {
                getFromServer()
            }
        }

        retry_button.setOnClickListener{
            if (!noInternetConnection()) {
                getFromServer()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == addCode) {
            if (resultCode == Activity.RESULT_OK) {

                val details = data!!.getStringExtra("details")
                val age = data.getStringExtra("age")
                val type = data.getStringExtra("type")
                val status = data.getStringExtra("status")


                val newObj = Object(
                    null,
                    details,
                    status,
                    userId,
                    age.toInt(),
                    type
                )

//                val localDB = Room.databaseBuilder(
//                    applicationContext,
//                    AppDatabase::class.java, "maindb1.db"
//                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

                localDB.objects().insert(newObj)
                //doctorsList!!.add(newDoc)
                addToServer(newObj)
            }
        }
    }
}
