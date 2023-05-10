package com.example.therealassignment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.therealassignment.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase


class home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var homeBinding : ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout

    //For user login active status
    val userActiveStatus = "user_login_status"

    //For store the user email information
    val userEmail = "user_email_information"

    //For notification
    private val CHANNEL_ID = "FitLife"
    private val notificationID = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        // TODO:  clear this
        //store the data to sharePreferences
        val waterSharedPreferences = getSharedPreferences("waterPreferences", Context.MODE_PRIVATE)
        val waterEditor = waterSharedPreferences?.edit()

        //Left navigation bar
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        //Home header
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.home_layout, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.home)
        }

        //For the notification, create the notification channel
        createNotificationChannel()

        //This is for the navigation to switch the screen
        homeBinding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {

                R.id.home -> {
                    replaceFragment(HomeFragment())
                    sendNotification()
                }
                R.id.water -> replaceFragment(WaterFragment())
                R.id.exercise -> replaceFragment(ExerciseFragment())
                R.id.sleep -> replaceFragment(SleepFragment())
                R.id.chart  -> {
                    replaceFragment(ChartFragment())

                    // TODO: Clear this
                    waterEditor?.apply{
                        putString("water_amount", "0")
                        apply()
                    }
                }
            }
            true
        }

        //Change the email information for the left navigation bar
/*        val userEmailTextView = findViewById<TextView>(R.id.left_nav_header_user_email)
        val user = FirebaseAuth.getInstance().currentUser
        userEmailTextView.text = user?.email.toString()*/


    }

    // A function that use by the navigation bar
    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.home_layout,fragment)
        fragmentTransaction.commit()

    }

    //function that use by the left navigation bar
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.left_nav_profile -> {
                val intent = Intent(this, profile::class.java)
                startActivity(intent)
            }
            R.id.left_nav_reward -> {
                val intent = Intent(this, reward::class.java)
                startActivity(intent)
            }
            R.id.left_nav_setting -> {
                val intent = Intent(this, setting::class.java)
                startActivity(intent)
            }
            R.id.left_nav_about -> {
                val intent = Intent(this, aboutUs::class.java)
                startActivity(intent)
            }

            //Logout
            R.id.left_nav_logout -> {
                //Code to clear user login status
                val sharedPreferences = getSharedPreferences(userActiveStatus, MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("active_status", "")
                editor.apply()

                //Go back to login page
                val intent = Intent(this, loginRegisterControl::class.java)
                startActivity(intent)
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun changeToolColour(item: MenuItem){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        if(item.itemId == R.id.home){
            toolbar.setBackgroundColor(0)
        }else{
            toolbar.setBackgroundColor(Color.parseColor("#0485A2"))
        }
    }

    //Notification channel, create for handle the security purpose
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "FitLife"
            val descriptionText = "Drink Water"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this, home::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent:PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        //For display picture on the navigation
        //val bitmap = BitmapFactory.decodeResource(application.resources, R.drawable.logo_app)
        //val bitmapLargeImage = BitmapFactory.decodeResource(application.resources, R.drawable.logo_app)

        // Set up the notification content.
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_app)
            .setContentTitle("Drink")
            .setContentText("message")
            //To display picture on navigation
            //.setLargeIcon(bitmapLargeImage)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Go and drink more and more water."))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationID,builder.build())
        }
    }

    //Another method to send notification
    private fun showNotification() {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_app)
            .setContentTitle("Hello world")
            .setContentText("This is a description")
            .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}