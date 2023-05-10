package com.example.therealassignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.therealassignment.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class Register : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This two line is for the viewBinding purpose
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // For the firebase returns a user identifier
        firebaseAuth = FirebaseAuth.getInstance()


        //SignIn button
        binding.registerRegister.setOnClickListener {
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()
            val confirmPassword = binding.registerConfirmPassword.text.toString()

            //check any null
            if(email.isEmpty()){
                Toast.makeText(this, "Email fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!isValidEmail(email)){
                Toast.makeText(baseContext, "Invalid Email",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                Toast.makeText(this, "Password fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.length < 6){
                Toast.makeText(this, "Password length should more than 6 letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(confirmPassword.isEmpty()){
                Toast.makeText(this, "Confirm password fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(this, "Password does not matched", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                //if success go to login page
                if (it.isSuccessful) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else if(it.exception is FirebaseAuthUserCollisionException) {
                    Toast.makeText(this,"Provided Email Is Already Registered!", Toast.LENGTH_SHORT).show()
                }
            }


        }

        binding.registerBack.setOnClickListener {
            val back = Intent(this,loginRegisterControl::class.java)
            startActivity(back)
            finish()
        }

    }

    //Check the email validation
    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}