package com.artem.android.task1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class ChangePhotoDialogFragment: DialogFragment() {

    private lateinit var choosePhotoTv: TextView
    private lateinit var makePhotoTv: TextView
    private lateinit var deletePhotoTv: TextView
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val photoBitmap = data?.extras?.getParcelable("data", Bitmap::class.java)
                val res = Bundle().apply {
                    putParcelable("bundleKey", photoBitmap)
                }
                parentFragmentManager.setFragmentResult("requestKey", res)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_photochange, container, false)
        choosePhotoTv = view.findViewById(R.id.upload_photo_tv)
        makePhotoTv = view.findViewById(R.id.make_photo_tv)
        deletePhotoTv = view.findViewById(R.id.delete_photo_tv)
        return view
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onStart() {
        super.onStart()
        choosePhotoTv.setOnClickListener { Log.i(TAG, "choose photo clicked") }
        makePhotoTv.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            activityResultLauncher.launch(cameraIntent)
        }
        deletePhotoTv.setOnClickListener {
            val isDeleted = true
            val result = Bundle().apply {
                putSerializable("bundleKey2", isDeleted)
            }
            parentFragmentManager.setFragmentResult("requestKey2", result)
        }
    }

    companion object {
        private const val TAG = "ChangePhotoDialogFragment"
        fun newInstance(): DialogFragment {
            return ChangePhotoDialogFragment()
        }
    }
}