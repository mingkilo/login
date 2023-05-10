package com.example.therealassignment

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.therealassignment.databinding.ActivityLoginRegisterControlBinding
import com.example.therealassignment.databinding.ActivityMainBinding
import com.example.therealassignment.databinding.LoginPageBinding

class loginRegisterControl : AppCompatActivity() {

    private lateinit var LogRegbinding: ActivityLoginRegisterControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogRegbinding = ActivityLoginRegisterControlBinding.inflate(layoutInflater)
        setContentView(LogRegbinding.root)

        val LogRegImageChange : ConstraintLayout = LogRegbinding.root
        val LogRegDrink : ImageView = LogRegbinding.LogRegDrink
        val LogRegExercise : ImageView = LogRegbinding.LogRegExercise
        val LogRegSleep : ImageView = LogRegbinding.LogRegSleep
        val LogRegTextChange1 : TextView = LogRegbinding.LogRegTextChange
        val LogRegTextChange2 : TextView = LogRegbinding.LogRegTextChange2
        val LogRegCreate : Button = LogRegbinding.LogRegCreateButton
        val LogRegLogin : Button = LogRegbinding.LogRegLoginButton
        val LogRegChangeDot1 : View = LogRegbinding.LogRegDot1
        val LogRegChangeDot2 : View = LogRegbinding.LogRegDot2
        val LogRegChangeDot3 : View = LogRegbinding.LogRegDot3
        val LogRegimageArray = intArrayOf(R.drawable.login_register_drink,
            R.drawable.login_register_exercise, R.drawable.login_register_sleep)
        var imageIndex = 0
        val wordMap = mapOf(
            R.drawable.login_register_drink to "Drink Is A Must",
            R.drawable.login_register_exercise to "Move More Gain More",
            R.drawable.login_register_sleep to "You Need Rest"
        )
        val wordMap2 = mapOf(
            R.drawable.login_register_drink to "Constantly monitoring one's own water intake is " +
                    "crucial for maintaining proper hydration and promoting optimal bodily functions.",
            R.drawable.login_register_exercise to "Regular physical activity is essential for " +
                    "maintaining good health and should be incorporated into one's routine",
            R.drawable.login_register_sleep to "Obtaining adequate sleep is critical for maintaining " +
                    "optimal physical and mental health."
        )
        fun updateViews() {
            when (imageIndex) {
                0 -> {
                    LogRegDrink.visibility = View.VISIBLE
                    LogRegExercise.visibility = View.INVISIBLE
                    LogRegSleep.visibility = View.INVISIBLE
                    LogRegTextChange1.text = wordMap[R.drawable.login_register_drink]
                    LogRegTextChange2.text = wordMap2[R.drawable.login_register_drink]
                    LogRegChangeDot1.setBackgroundColor(Color.LTGRAY)
                    LogRegChangeDot2.setBackgroundColor(Color.BLACK)
                    LogRegChangeDot3.setBackgroundColor(Color.BLACK)
                }
                1 -> {
                    LogRegDrink.visibility = View.INVISIBLE
                    LogRegExercise.visibility = View.VISIBLE
                    LogRegSleep.visibility = View.INVISIBLE
                    LogRegTextChange1.text = wordMap[R.drawable.login_register_exercise]
                    LogRegTextChange2.text = wordMap2[R.drawable.login_register_exercise]
                    LogRegChangeDot1.setBackgroundColor(Color.BLACK)
                    LogRegChangeDot2.setBackgroundColor(Color.LTGRAY)
                    LogRegChangeDot3.setBackgroundColor(Color.BLACK)
                }
                2 -> {
                    LogRegDrink.visibility = View.INVISIBLE
                    LogRegExercise.visibility = View.INVISIBLE
                    LogRegSleep.visibility = View.VISIBLE
                    LogRegTextChange1.text = wordMap[R.drawable.login_register_sleep]
                    LogRegTextChange2.text = wordMap2[R.drawable.login_register_sleep]
                    LogRegChangeDot1.setBackgroundColor(Color.BLACK)
                    LogRegChangeDot2.setBackgroundColor(Color.BLACK)
                    LogRegChangeDot3.setBackgroundColor(Color.LTGRAY)
                }
            }
        }
        LogRegImageChange.setOnClickListener {
            imageIndex = (imageIndex + 1) % LogRegimageArray.size
            val imageResource = LogRegimageArray[imageIndex]
            updateViews()
        }
        LogRegCreate.setOnClickListener {
            val register = Intent(this,Register::class.java)
            startActivity(register)
            finish()
        }
        LogRegLogin.setOnClickListener {
            val login = Intent(this,LoginActivity::class.java)
            startActivity(login)
            finish()
        }
    }
}