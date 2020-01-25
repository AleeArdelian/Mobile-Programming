package com.example.vet_house

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val myRequestCode = 1
class DoctorsList : AppCompatActivity() {

    var doctorService: DoctorApi? = null
    var doctorAdapter : DoctorsAdapter ?= null
    var doctorsList : ArrayList<Doctor> ?= null
    var contex : Context ?= null

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
        contex = this

        setContentView(R.layout.activity_doctors_list)
        val recyclerView = findViewById<RecyclerView>(R.id.recycle_view)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.115.50:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        doctorService = retrofit.create(DoctorApi::class.java)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val localDB = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "maindb1.db"
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()

        //localDB.doctor().deleteAll()

        val call = doctorService?.getDoctors("application/json")
        call?.enqueue(object : Callback<List<Doctor>> {
            override fun onFailure(call: Call<List<Doctor>>, t: Throwable) {
                Log.d("get", "FAILED")
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Doctor>>, response: Response<List<Doctor>>) {
                Log.d("get", "SUCCESS")
                doctorsList = ArrayList(response.body()!!)
                doctorAdapter = DoctorsAdapter(contex!!, localDB, doctorsList!!)
                recyclerView.adapter = doctorAdapter
            }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        if(userRole == "OWNER")
            fab.hide()
        else {
            val itemTouchCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        viewHolder1: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun getSwipeDirs(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder
                    ): Int {
                        return if (noInternetConnection()) 0 else super.getSwipeDirs(
                            recyclerView,
                            viewHolder
                        )
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                        val position = viewHolder.adapterPosition
                        val doc = doctorsList!![position]
                        localDB.doctor().delete(doc)
                        doctorsList!!.remove(doc)

                        val call1 = doctorService?.deleteDoctor("application/json", doc.email)
                        call1?.enqueue(object : Callback<Doctor> {
                            override fun onFailure(call: Call<Doctor>, t: Throwable) {
                                Log.d("delete", "FAILED")
                                t.printStackTrace()
                            }

                            override fun onResponse(
                                call: Call<Doctor>,
                                response: Response<Doctor>
                            ) {
                                Log.d("delete", "SUCCESS")
                            }
                        })
                        doctorAdapter?.notifyDataSetChanged()
                    }
                }
            val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)

            fab.setOnClickListener {
                val intent = Intent(this, AddDoctor::class.java)
                startActivityForResult(intent, myRequestCode)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == myRequestCode) {
            if (resultCode == Activity.RESULT_OK) {

                val doctorFName = data!!.getStringExtra("first_name")
                val doctorSpeciality = data.getStringExtra("last_name")
                val doctorLName = data.getStringExtra("spec")
                val doctorEmail = data.getStringExtra("email")
                val doctorPass = data.getStringExtra("pass")
                val newDoc = Doctor(null, doctorFName,doctorLName,doctorEmail,doctorPass,doctorSpeciality)

                val localDB = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "maindb1.db"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

                localDB.doctor().insert(newDoc)
                doctorsList!!.add(newDoc)
                doctorService?.addDoctor(newDoc, "application/json")?.enqueue(object: Callback<Doctor>{
                    override fun onFailure(call: Call<Doctor>, t: Throwable) {
                        Log.d("add", "FAILED")
                    }
                    override fun onResponse(call: Call<Doctor>, response: Response<Doctor>) {
                        Log.d("add", "SUCCESS")
                    }
                })
                doctorAdapter?.notifyDataSetChanged()
            }
        }
    }
}

