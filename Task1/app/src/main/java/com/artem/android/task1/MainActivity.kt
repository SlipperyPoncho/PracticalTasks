package com.artem.android.task1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var bottomNavBarHelp: View
    private lateinit var bottomNavBarSearch: View
    private lateinit var bottomNavBarProfile: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavBarHelp = bottomNavigationView.findViewById(R.id.help)
        bottomNavBarSearch = bottomNavigationView.findViewById(R.id.search)
        bottomNavBarProfile = bottomNavigationView.findViewById(R.id.profile)
        bottomNavigationView.selectedItemId = R.id.help

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment.newInstance())
                .commit()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpFragment.newInstance())
                .commit()
            bottomNavigationView.isVisible = true
        }, 2000)
    }

    override fun onStart() {
        super.onStart()
        bottomNavBarHelp.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.help
        }
        bottomNavBarProfile.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.profile
        }
        bottomNavBarSearch.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.search
        }
    }
}