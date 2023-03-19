package com.rajpakhurde.e_mentoringapp.loginpage

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.rajpakhurde.e_mentoringapp.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Creating Account")
        progressDialog.setMessage("We are creating your account.")

        binding.apply {
            tvLogin.setOnClickListener {
                startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
            }

            btnSignup.setOnClickListener {
                val fName = etFirstName.text.toString().trim()
                val lName = etLastName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val cPassword = etConfirmPassoword.text.toString().trim()
                val user = if (rbStudent.isChecked) {
                    "student"
                } else {
                    "mentor"
                }

                if (fName.isBlank() || lName.isBlank() || email.isBlank() || password.isBlank() || cPassword.isBlank()) {
                    Toast.makeText(this@SignupActivity,"Please fill all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else if (password != cPassword) {
                    Toast.makeText(this@SignupActivity,"Confirm password and Password doesn't match...",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    progressDialog.show()
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                        progressDialog.dismiss()
                        if (task.isSuccessful) {
                            Toast.makeText(this@SignupActivity,"Account Created", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignupActivity,LoginActivity::class.java))
                        } else {
                            Toast.makeText(this@SignupActivity,task.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}