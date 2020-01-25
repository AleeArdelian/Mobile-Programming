package com.example.vet_house

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main_page.*

class StartPage : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val drawer =  findViewById<DrawerLayout>(R.id.startup_layout)
        when(item.itemId)
        {
            R.id.nav_doctors ->
            {
                val intent = Intent(this, DoctorsList::class.java)
                startActivity(intent)
            }
//            R.id.nav_appointments ->
//            {
//                val intent = Intent(this, AppointmentsList::class.java)
//                startActivity(intent)
//            }
//            R.id.nav_profile ->
//            {
//
//            }
            R.id.logout_button ->
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val drawer =  findViewById<DrawerLayout>(R.id.startup_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_bar_navview)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this,drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        val drawer =  findViewById<DrawerLayout>(R.id.startup_layout)
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}
