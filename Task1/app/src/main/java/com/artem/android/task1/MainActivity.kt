package com.artem.android.task1

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.artem.android.task1.data.readJSONFromAssets
import com.artem.android.task1.presentation.authfragment.AuthFragment
import com.artem.android.task1.presentation.helpfragment.HelpFragment
import com.artem.android.task1.presentation.mainviewmodel.MainActivityViewModel
import com.artem.android.task1.presentation.newsfragment.NewsFragment
import com.artem.android.task1.presentation.profilefragment.ProfileFragment
import com.artem.android.task1.presentation.searchfragment.SearchFragment
import com.artem.android.task1.presentation.splashfragment.SplashFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), AuthFragment.LoginCallback {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var bottomNavBarHelp: View
    private lateinit var bottomNavBarSearch: View
    private lateinit var bottomNavBarProfile: View
    private lateinit var bottomNavBarNews: View
    private lateinit var progressBar: ProgressBar
    private var isDataLoaded: Boolean = false

    private val mainActivityViewModel: MainActivityViewModel by viewModels {
        (application as App).appComponent.mainActivityViewModelFactory()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel.initializeData(
            readJSONFromAssets(this, "categories.json"),
            readJSONFromAssets(this, "events.json")
        )

        (application as App).appComponent.inject(this)

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
    }

    override fun onStart() {
        super.onStart()
        bottomNavBarHelp.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.help
            if (!isDataLoaded) progressBar.visibility = View.GONE
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
            if (!isDataLoaded) progressBar.visibility = View.GONE
        }
        bottomNavBarNews.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NewsFragment.newInstance())
                .commit()
            bottomNavigationView.selectedItemId = R.id.news
            if (!isDataLoaded) progressBar.visibility = View.GONE
        }
    }

    override fun onLoginClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HelpFragment.newInstance())
            .commit()
        bottomNavigationView.isVisible = true
        if (!isDataLoaded) progressBar.visibility = View.GONE
        bottomNavigationView.selectedItemId = R.id.help
    }
}