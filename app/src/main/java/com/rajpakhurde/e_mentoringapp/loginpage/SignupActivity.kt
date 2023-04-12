package com.rajpakhurde.e_mentoringapp.loginpage

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.rajpakhurde.e_mentoringapp.R
import com.rajpakhurde.e_mentoringapp.data.DataClass
import com.rajpakhurde.e_mentoringapp.databinding.ActivitySignupBinding
import java.text.DateFormat
import java.util.*

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    var imageURL: String? = null
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Creating Account")
        progressDialog.setMessage("We are creating your account.")

        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data!!.data
                binding.uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this@SignupActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
        binding.uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        binding.apply {
            tvLogin.setOnClickListener {
                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            }

            btnSignup.setOnClickListener {
                val fName = etFirstName.text.toString().trim()
                val lName = etLastName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val cPassword = etConfirmPassoword.text.toString().trim()

                if (fName.isBlank() || lName.isBlank() || email.isBlank() || password.isBlank() || cPassword.isBlank()) {
                    Toast.makeText(
                        this@SignupActivity,
                        "Please fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (password != cPassword) {
                    Toast.makeText(
                        this@SignupActivity,
                        "Confirm password and Password doesn't match...",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    progressDialog.show()
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            progressDialog.dismiss()
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "Account Created",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(
                                        this@SignupActivity,
                                        LoginActivity::class.java
                                    )
                                )
                                saveData()
                            } else {
                                Toast.makeText(
                                    this@SignupActivity,
                                    task.exception?.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }
    }

    private fun saveData() {
        val storageReference = FirebaseStorage.getInstance().reference.child("Task Images")
            .child(uri!!.lastPathSegment!!)

        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapShot ->
            val uriTask = taskSnapShot.storage.downloadUrl
            while (!uriTask.isComplete);
            val uriImage = uriTask.result
            imageURL = uriImage.toString()
            uploadData()
        }.addOnFailureListener {
            Toast.makeText(this@SignupActivity,it.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadData() {
        val fname = binding.etFirstName.text.toString().trim()
        val lname = binding.etLastName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        val name = "$fname $lname"
        var userMode = ""
        if (binding.rbStudent.isChecked) {
            userMode = "Student"
        } else {
            userMode = "Mentor"
        }

        val dataClass = DataClass(name, email, password, imageURL, userMode)
        val currenDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        FirebaseDatabase.getInstance().getReference("User").child(currenDate)
            .setValue(dataClass).addOnCompleteListener { task ->
                Toast.makeText(this@SignupActivity, "Saved", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener { e ->
                Toast.makeText(this@SignupActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }

    }
}