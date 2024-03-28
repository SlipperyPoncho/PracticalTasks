package com.artem.android.task1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), AuthFragment.LoginCallback {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var bottomNavBarHelp: View
    private lateinit var bottomNavBarSearch: View
    private lateinit var bottomNavBarProfile: View
    private lateinit var bottomNavBarNews: View
    private lateinit var progressBar: ProgressBar
    private lateinit var broadcastReceiver: BroadcastReceiver
    private var isDataLoaded: Boolean = false

    private val charitySharedViewModel by lazy {
        ViewModelProvider(this)[CharitySharedViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_nav_bar)
        bottomNavBarHelp = bottomNavigationView.findViewById(R.id.help)
        bottomNavBarSearch = bottomNavigationView.findViewById(R.id.search)
        bottomNavBarProfile = bottomNavigationView.findViewById(R.id.profile)
        bottomNavBarNews = bottomNavigationView.findViewById(R.id.news)
        bottomNavigationView.selectedItemId = R.id.help
        progressBar = findViewById(R.id.pb_circle)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, SplashFragment.newInstance())
                .commit()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AuthFragment.newInstance())
                .commit()
        }, 2000)

        val intent = Intent(this, MyService::class.java)
        startService(intent)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val resultCategories = intent?.getStringExtra(PARAM_RESULT_CATEGORIES)
                val resultEvents = intent?.getStringExtra(PARAM_RESULT_EVENTS)
                if (resultCategories != null && resultEvents != null) {
                    charitySharedViewModel.initializeData(resultCategories, resultEvents)
                    isDataLoaded = true
                }
                progressBar.visibility = View.GONE
            }
        }

        val intentFilter = IntentFilter(BROADCAST_ACTION)
        registerReceiver(broadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    override fun onStart() {
        super.onStart()
        bottomNavBarHelp.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.help
            if (!isDataLoaded) progressBar.visibility = View.VISIBLE
        }
        bottomNavBarProfile.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.profile
            progressBar.visibility = View.GONE
        }
        bottomNavBarSearch.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.search
            if (!isDataLoaded) progressBar.visibility = View.VISIBLE
        }
        bottomNavBarNews.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NewsFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.news
            if (!isDataLoaded) progressBar.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onLoginClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HelpFragment.newInstance())
            .commit()
        bottomNavigationView.isVisible = true
        if (!isDataLoaded) progressBar.visibility = View.VISIBLE
        bottomNavigationView.selectedItemId = R.id.help
    }

    companion object {
        const val PARAM_RESULT_CATEGORIES = "result_categories"
        const val PARAM_RESULT_EVENTS = "result_events"
        const val BROADCAST_ACTION = "broadcast"
    }
}