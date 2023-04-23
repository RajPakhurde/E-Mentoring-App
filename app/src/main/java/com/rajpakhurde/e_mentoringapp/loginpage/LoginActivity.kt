package com.rajpakhurde.e_mentoringapp.loginpage

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.rajpakhurde.e_mentoringapp.databinding.ActivitySigninBinding
import com.rajpakhurde.e_mentoringapp.fragments.ProfileFragments
import com.rajpakhurde.e_mentoringapp.ui.DashBoardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait..")
        progressDialog.setMessage("Just a moment..")

        binding.apply {
            tvSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity,SignupActivity::class.java))
            }

            btnLogin.setOnClickListener {
                Log.i("TAG","Log in")
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (email.isBlank()) {
                    Toast.makeText(this@LoginActivity,"Please Enter an Email", Toast.LENGTH_SHORT).show()
                } else if (password.isBlank()) {
                    Toast.makeText(this@LoginActivity,"Please Enter a Password", Toast.LENGTH_SHORT).show()
                } else {
                    progressDialog.show()
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                        progressDialog.dismiss()
                        if (task.isSuccessful) {
                            Toast.makeText(this@LoginActivity,"Logged in", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity,DashBoardActivity::class.java)
                            intent.putExtra("email",email)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this@LoginActivity,task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}