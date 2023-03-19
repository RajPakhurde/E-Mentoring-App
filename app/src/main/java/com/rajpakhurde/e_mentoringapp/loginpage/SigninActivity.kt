package com.rajpakhurde.e_mentoringapp.loginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rajpakhurde.e_mentoringapp.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            tvSignup.setOnClickListener {
                startActivity(Intent(this@SigninActivity,SignupActivity::class.java))
            }
        }
    }
}