package com.rajpakhurde.e_mentoringapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rajpakhurde.e_mentoringapp.R
import com.rajpakhurde.e_mentoringapp.databinding.ActivityDashBoardBinding
import com.rajpakhurde.e_mentoringapp.fragments.ChatFragment
import com.rajpakhurde.e_mentoringapp.fragments.ProfileFragments
import com.rajpakhurde.e_mentoringapp.fragments.SearchFragment
import com.rajpakhurde.e_mentoringapp.fragments.VideoFragment

class DashBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profileFragment = ProfileFragments()
        val searchFragment = SearchFragment()
        val chatFragment = ChatFragment()
        val videoFragment = VideoFragment()

        val email = getIntent().getStringExtra("email")
        Log.i("TAG","email = $email")

        val bundle = Bundle()
        bundle.putString("email", email)
        profileFragment.setArguments(bundle)

        setCurrentFragment(profileFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.imProfile -> setCurrentFragment(profileFragment)
                R.id.imSearch -> setCurrentFragment(searchFragment)
                R.id.imChat -> setCurrentFragment(chatFragment)
                R.id.imVideo -> setCurrentFragment(videoFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}