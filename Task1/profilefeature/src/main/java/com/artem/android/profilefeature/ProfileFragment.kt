package com.artem.android.profilefeature

import android.content.Context
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
import com.artem.android.profilefeature.changephotodialogfragment.ChangePhotoDialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ProfileFragment: Fragment() {

    private lateinit var profilePic: ImageView
    private lateinit var friend1Pic: ImageView
    private lateinit var friend2Pic: ImageView
    private lateinit var friend3Pic: ImageView
    private var dialogFragment: DialogFragment? = null
    private lateinit var profileComponent: ProfileComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        profileComponent = (requireActivity().applicationContext as ProfileComponentProvider)
            .provideProfileComponent()
        profileComponent.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profilePic = view.findViewById(R.id.profile_pic_iv)

        friend1Pic = view.findViewById(R.id.friend1_pic)
        friend2Pic = view.findViewById(R.id.friend2_pic)
        friend3Pic = view.findViewById(R.id.friend3_pic)

        Glide.with(requireActivity())
            .load("https://cdn.zeplin.io/5a8295e8de62056425a09dbc/assets/E0F81DAD-ED24-4342-957A-8DC6B274A0BA.png")
            .placeholder(R.drawable.user_icon)
            .error(R.drawable.cardimage_3)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(friend1Pic)

        Glide.with(requireActivity())
            .load("https://cdn.zeplin.io/5a8295e8de62056425a09dbc/assets/3DF5C78D-DCEE-4751-BE1D-7ADA5DE00F19.png")
            .placeholder(R.drawable.user_icon)
            .error(R.drawable.cardimage_3)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(friend2Pic)

        Glide.with(requireActivity())
            .load("https://cdn.zeplin.io/5a8295e8de62056425a09dbc/assets/F79B5B72-528E-498C-A6BD-C693FA05D12D.png")
            .placeholder(R.drawable.user_icon)
            .error(R.drawable.cardimage_3)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(friend3Pic)

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