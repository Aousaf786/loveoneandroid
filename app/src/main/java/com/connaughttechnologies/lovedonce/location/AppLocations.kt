package com.connaughttechnologies.lovedonce.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.connaughttechnologies.lovedonce.base.SharedViewModel
import com.connaughttechnologies.lovedonce.permissions.AppPermissions
import com.google.android.gms.location.*
import timber.log.Timber

class AppLocations(
    private val context: AppCompatActivity,
    private val permission: AppPermissions
) : LifecycleObserver {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var locationCallback: LocationCallback


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        sharedViewModel = ViewModelProvider(context).get(SharedViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                Timber.d("lastLocation")
                sharedViewModel.liveLastKnownLocation.value = locationResult.locations.first()
                stopLocationUpdates()
            }
        }
        observeSharedViewModel()
    }

    private fun observeSharedViewModel() {
        sharedViewModel.eventGetLastKnownLocation.observe(context, Observer {
            if (permission.checkLocationPermission()) {
                getLastKnownLocation()
            } else {
                permission.requestLocationPermission(sharedViewModel.eventGetLastKnownLocation)
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationClient.requestLocationUpdates(
            createLocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}