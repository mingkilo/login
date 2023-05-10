package com.example.therealassignment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.therealassignment.databinding.ActivityLoginRegisterControlBinding
import com.example.therealassignment.databinding.ActivitySplashBinding
import java.util.Random

class splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    val userActiveStatus = "user_login_status"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val changeText : TextView = binding.preloadText2
        val wordMap = mapOf(
            R.drawable.splash_image1 to "WORK LIKE \n" +
                    "THERE IS NO \nTOMMOROW",
            R.drawable.splash_image2 to "STAY \n" +
                    "HYDRATED",
            R.drawable.splash_image3 to "DON’T TELL PEOPLE YOUR PLAN, SHOW THEM WITH RESULT"
        )
        val img1: ImageView =binding.preload1Image
        val img2: ImageView =binding.preload2Image
        val img3: ImageView =binding.preload3Image
        val imageArray = intArrayOf(R.drawable.splash_image2,
            R.drawable.splash_image3,
            R.drawable.splash_image1)
        val random = Random()
        val randomIndex = random.nextInt(imageArray.size)
        val selectedImage = imageArray[randomIndex]
        fun updateViews() {
            when (selectedImage) {
                R.drawable.splash_image2 -> {
                    img1.visibility = View.VISIBLE
                    img2.visibility = View.INVISIBLE
                    img3.visibility = View.INVISIBLE
                    changeText.text = wordMap[R.drawable.splash_image2]

                    val padding_in_dp = 20 // 6 dps
                    val scale = resources.displayMetrics.density
                    val padding_in_px = (padding_in_dp * scale + 0.5f).toInt()

                    findViewById<TextView>(R.id.preload_text2).setPadding(padding_in_px,0 ,0, 0)
                }
                R.drawable.splash_image3 -> {
                    img1.visibility = View.INVISIBLE
                    img2.visibility = View.VISIBLE
                    img3.visibility = View.INVISIBLE
                    changeText.text = wordMap[R.drawable.splash_image3]
                }
                R.drawable.splash_image1 -> {
                    img1.visibility = View.INVISIBLE
                    img2.visibility = View.INVISIBLE
                    img3.visibility = View.VISIBLE
                    changeText.text = wordMap[R.drawable.splash_image1]
                }
            }
        }
        selectedImage?.let {
            updateViews()
        }
        Handler().postDelayed({

            //Check whether the user status need to login or not, if no then jump to main page
            val sharedPreferences = getSharedPreferences(userActiveStatus, MODE_PRIVATE)
            val check = sharedPreferences.getString("active_status", "")

            if(check.equals("true")){
                val intent = Intent(this, home::class.java)
                startActivity(intent)
                finish()
            } else{
                val intent = Intent(this, loginRegisterControl::class.java)
                startActivity(intent)
                finish()
            }


        }, 3000)
    }
}