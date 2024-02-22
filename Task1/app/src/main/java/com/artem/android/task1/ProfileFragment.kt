package com.artem.android.task1

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment: Fragment() {

    private lateinit var profilePic: ImageView
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var bottomNavBarHelp: View
    private lateinit var bottomNavBarSearch: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        profilePic = view.findViewById(R.id.profile_pic_iv)
        bottomNavBar = view.findViewById(R.id.bottom_nav_bar)
        bottomNavBar.selectedItemId = R.id.profile
        bottomNavBarHelp = bottomNavBar.findViewById(R.id.help)
        bottomNavBarSearch = bottomNavBar.findViewById(R.id.search)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        profilePic.setOnClickListener {
            childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) {
                    _, bundle ->
                val result = bundle.getParcelable("bundleKey", Bitmap::class.java)
                profilePic.setImageBitmap(result)
            }
            val dialog = ChangePhotoDialogFragment.newInstance()
            dialog.show(this@ProfileFragment.childFragmentManager, PHOTO_CHANGE)

            // NOT WORKING
            childFragmentManager.setFragmentResultListener("requestKey2", viewLifecycleOwner) {
                    _, bundle ->
                val result = bundle.getSerializable("bundleKey2", Boolean::class.java)
                //if (result == true) {
                    profilePic.setImageResource(R.drawable.profile_pic)
                //}
            }
        }
        bottomNavBarHelp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpFragment.newInstance())
                .commit()
        }
        bottomNavBarSearch.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SearchFragment.newInstance())
                .commit()
        }
    }

    companion object {
        private const val PHOTO_CHANGE = "PhotoChange"
        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }
}