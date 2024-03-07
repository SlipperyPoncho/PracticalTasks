package com.artem.android.task1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
    private lateinit var cameraActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryActivityResultLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val photoBitmap = data?.extras?.getParcelable("data", Bitmap::class.java)
                val res = Bundle().apply {
                    putParcelable("imageBitmapBundle", photoBitmap)
                }
                parentFragmentManager.setFragmentResult("imageBitmapRequest", res)
            }
        }
        galleryActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                val res = Bundle().apply {
                    putParcelable("imageUriBundle", imageUri)
                }
                parentFragmentManager.setFragmentResult("imageUriRequest", res)
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
        choosePhotoTv.setOnClickListener {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            galleryActivityResultLauncher.launch(galleryIntent)
        }
        makePhotoTv.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraActivityResultLauncher.launch(cameraIntent)
        }
        deletePhotoTv.setOnClickListener {
            val isDeleted = true
            val result = Bundle().apply {
                putBoolean("deleteBundle", isDeleted)
            }
            parentFragmentManager.setFragmentResult("deleteRequest", result)
        }
    }

    companion object {
        fun newInstance(): DialogFragment {
            return ChangePhotoDialogFragment()
        }
    }
}