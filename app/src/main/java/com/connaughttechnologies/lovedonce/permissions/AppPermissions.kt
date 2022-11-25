package com.connaughttechnologies.lovedonce.permissions

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.connaughttechnologies.lovedonce.base.SharedViewModel
import com.connaughttechnologies.lovedonce.utils.SingleLiveEvent

class AppPermissions(private val context: AppCompatActivity) : LifecycleObserver {
    private lateinit var sharedViewModel: SharedViewModel

    private val listLocationPermissions = listOf<String>(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var launcherRequestMultiplePermissions: ActivityResultLauncher<Array<String>>
    private var event = SingleLiveEvent<Any>()


    private lateinit var launcherSingle: ActivityResultLauncher<String>


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        launcherRequestMultiplePermissions =
            context.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                val result = it.values.filter { it == false }
                if (result.isEmpty())
                    event.call()
            }
        launcherSingle =
            context.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            }
        sharedViewModel = ViewModelProvider(context).get(SharedViewModel::class.java)
        observeSharedViewModel()
    }

    private fun observeSharedViewModel() {
        sharedViewModel.eventPermissionCheckLocation.observe(context, Observer {
            sharedViewModel.eventPermissionResultLocation.value = checkLocationPermission()
        })
    }

    fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            listLocationPermissions[0]
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    listLocationPermissions[1]
                ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermission(eventGetLastKnownLocation: SingleLiveEvent<Any>) {
        event = eventGetLastKnownLocation
        launcherRequestMultiplePermissions.launch(listLocationPermissions.toTypedArray())
    }

    fun checkWriteStorage(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
}