package com.artem.android.task1

import android.graphics.Bitmap
import android.net.Uri
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
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profilePic = view.findViewById(R.id.profile_pic_iv)
        childFragmentManager.setFragmentResultListener("imageBitmapRequest", viewLifecycleOwner) {
                _, bundle ->
            val result = bundle.getParcelable("imageBitmapBundle", Bitmap::class.java)
            dialogFragment?.dismiss()
            profilePic.setImageBitmap(result)
        }
        childFragmentManager.setFragmentResultListener("imageUriRequest", viewLifecycleOwner) {
                _, bundle ->
            val result = bundle.getParcelable("imageUriBundle", Uri::class.java)
            dialogFragment?.dismiss()
            profilePic.setImageURI(result)
        }
        childFragmentManager.setFragmentResultListener("deleteRequest", viewLifecycleOwner) {
                _, bundle ->
            val result = bundle.getBoolean("deleteBundle")
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