package com.example.examen

import android.content.Context
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

class ManagersSec : AppCompatActivity() {

    lateinit var service : ObjAPI
    lateinit var adaptor : ObjAdaptor2
    lateinit var objList : ArrayList<Object>
    lateinit var sortList :ArrayList<myObj>
    lateinit var context : Context
    lateinit var recyclerView : RecyclerView
    lateinit var localDB : AppDatabase
    lateinit var progressBar : ProgressBar

    private fun getAllSort(){
        progressBar.visibility = View.VISIBLE

        sortList = arrayListOf<myObj>()
        val call = service.getAll("application/json")
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

                    for(el in objList) {
                        if(sortList.any { p-> p.id ==el.user })
                        {
                            val ob =sortList.find { p-> p.id ==el.user }
                            if (ob != null) {
                                ob.ord = 1
                            }
                        }
                        else{
                            sortList.add(myObj(el.user,1))
                        }
                    }


                    //ArrayList(myList.sortedWith(compareBy(Object::quantity)))


                    adaptor = ObjAdaptor2(context, localDB,ArrayList(sortList.sortedWith(compareBy(myObj::ord)).asReversed()))// ArrayList(objList.take(10)))
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_managers_sec)

        context = this

        recyclerView = findViewById(R.id.recycle_view1)
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

        progressBar = findViewById(R.id.pBar1)

        getAllSort()
    }
}
