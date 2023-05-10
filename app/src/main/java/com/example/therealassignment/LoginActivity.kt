package com.example.therealassignment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.therealassignment.databinding.LoginPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException


class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: LoginPageBinding
    private lateinit var firebaseAuth : FirebaseAuth

    val userActiveStatus = "user_login_status"
    val userEmail = "user_email_information"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This two line is for the login page viewBinding purpose
        loginBinding = LoginPageBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        // For the firebase returns a user identifier
        firebaseAuth = FirebaseAuth.getInstance()

        //login button
        loginBinding.loginLogin.setOnClickListener {

            val email = loginBinding.loginEmail.text.toString()
            val password = loginBinding.loginPassword.text.toString()

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

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){

                    //if the remember me checkbox was click, it will make the user stay active
                    if(loginBinding.loginStayLogin.isChecked){
                        //Code to record user login status
                        val sharedPreferences = getSharedPreferences(userActiveStatus, MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("active_status", "true")
                        editor.apply()
                    }

                    //Store the  user email information
                    val sharedPreferences = getSharedPreferences(userEmail, MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("user_email", email)
                    editor.apply()

                    //Go to the home page
                    val intent = Intent(this, home::class.java)
                    startActivity(intent)
                    finish()
                } else if(it.exception is FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(this,"Invalid password!", Toast.LENGTH_SHORT).show()
                }
            }

        }

        //Forget password text
        loginBinding.loginForgetPassword.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_forget_password, null)
            val userEmail = view.findViewById<EditText>(R.id.forget_editBox)

            builder.setView(view)
            val dialog = builder.create()

            //reset button in forget page
            view.findViewById<Button>(R.id.forget_btnReset).setOnClickListener {
                compareEmail(userEmail)
                dialog.dismiss()
            }

            //cancel button in forget page
            view.findViewById<Button>(R.id.forger_btnCancel).setOnClickListener {
                dialog.dismiss()
            }

            if (dialog.window != null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
        }

        loginBinding.loginBack.setOnClickListener {
            val back = Intent(this,loginRegisterControl::class.java)
            startActivity(back)
            finish()
        }
    }

    //function using in forget password
    private fun compareEmail(email: EditText){
        if (email.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                }
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