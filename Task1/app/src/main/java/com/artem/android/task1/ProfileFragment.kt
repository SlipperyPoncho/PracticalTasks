package com.artem.android.task1

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class ProfileFragment: Fragment() {

    private lateinit var profilePic: ImageView
    private var dialogFragment: DialogFragment? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        profilePic = view.findViewById(R.id.profile_pic_iv)
        childFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) {
                _, bundle ->
            val result = bundle.getParcelable("bundleKey", Bitmap::class.java)
            dialogFragment?.dismiss()
            profilePic.setImageBitmap(result)
        }
        childFragmentManager.setFragmentResultListener("requestKey2", viewLifecycleOwner) {
                _, bundle ->
            val result = bundle.getBoolean("bundleKey2")
            if (result) {
                profilePic.setImageResource(R.drawable.profile_pic)
            }
            dialogFragment?.dismiss()
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        profilePic.setOnClickListener {
            dialogFragment = ChangePhotoDialogFragment.newInstance()
            dialogFragment?.show(this@ProfileFragment.childFragmentManager, PHOTO_CHANGE)
        }
    }

    companion object {
        private const val PHOTO_CHANGE = "PhotoChange"
        fun newInstance(): Fragment {
            return ProfileFragment()
        }
    }
}