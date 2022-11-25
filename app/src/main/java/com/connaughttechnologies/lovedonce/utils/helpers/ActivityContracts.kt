package com.connaughttechnologies.lovedonce.utils.helpers

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import com.connaughttechnologies.lovedonce.MainActivity
import com.connaughttechnologies.lovedonce.base.BaseActivity

class ActivityContracts(val activity: MainActivity) : LifecycleObserver {

    private val selectImageFromGalleryResult = activity.registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            activity.setPickedImageUri(it)
        }
    }

    fun pickImageGallery() {
        selectImageFromGalleryResult.launch("image/*")
    }

    //camera permission
    private val listPermissionCamera = listOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    private val requestCameraPermissionLauncher =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            val listNotGranted = it.values.filter { it == false }
            activity.eventWriteStoragePermissionsResult.value = listNotGranted.isEmpty()
        }

    fun requestWriteStoragePermission() {
        requestCameraPermissionLauncher.launch(listPermissionCamera.toTypedArray())
    }
}