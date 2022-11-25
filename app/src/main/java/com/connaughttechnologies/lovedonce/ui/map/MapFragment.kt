package com.connaughttechnologies.lovedonce.ui.map

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.MainActivity
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentMapBinding
import com.connaughttechnologies.lovedonce.utils.Constants
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import timber.log.Timber
import java.util.*


class MapFragment : BaseFragment<MapViewModel, FragmentMapBinding>(), OnMapReadyCallback {
    override var viewModelClass: Class<MapViewModel> = MapViewModel::class.java
    override var layoutId: Int = R.layout.fragment_map
    override var bindingVariable: Int = BR.viewModel

    private var mMap: GoogleMap? = null
    private var lat = 0.0
    private var lng = 0.0

    override fun initViewModel() {
        binding.viewModel = viewModel
        binding.sharedViewModel = sharedViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAutoComplete()
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        binding.btnNext.setOnClickListener {
            if (sharedViewModel.address.isNotEmpty()) {
                sharedViewModel.lat = "$lat"
                sharedViewModel.lng = "$lng"
                findNavController().navigate(R.id.paymentFragment)
            } else {
                showToast(resources.getString(R.string.str_map_error))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    private fun initAutoComplete() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Timber.d(place.toString())
                fetchPlaceDetails(place)
            }

            override fun onError(status: Status) {
                Timber.d(status.toString())

            }
        })
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.liveLastKnownLocation.observe(viewLifecycleOwner, Observer {
// Add a marker in Sydney and move the camera
            it?.apply {
                lat = latitude
                lng = longitude
                setMarket()
            } ?: run { viewModel.showToast(Constants.LOCATION_ERROR) }
        })
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.eventAddress.observe(this, Observer {
            setAddress(it)
        })
    }

    private fun setMarket() {
        mMap?.apply {
            mapType = GoogleMap.MAP_TYPE_SATELLITE
            clear()
            val sydney = LatLng(lat, lng)
            addMarker(
                MarkerOptions()
                    .position(sydney)
            )
            moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20f))
            viewModel.getAddressFromLocation(
                lat,
                lng,
                Geocoder(requireContext(), Locale.getDefault())
            )
        }
    }

    private fun setAddress(address: String) {
        sharedViewModel.address = address
        binding.tvAddress.text = address
    }

    override fun onMapReady(googleMap: GoogleMap) {
        getLastKnownLocation()
        mMap = googleMap
        initMap()
    }

    private fun initMap() {
        mMap?.setOnMapClickListener {
            lat = it.latitude
            lng = it.longitude
            setMarket()
        }
    }

    private fun fetchPlaceDetails(place: Place) {
        // Define a Place ID.
        val placeId = place.id!!

// Specify the fields to return.
        val placeFields =
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS)

// Construct a request object, passing the place ID and fields array.
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        (requireActivity() as MainActivity).placesClient.fetchPlace(request)
            .addOnSuccessListener { response: FetchPlaceResponse ->
                val place = response.place
                setAddress(place.address!!)
                lat = place.latLng?.latitude ?: 0.0
                lng = place.latLng?.longitude ?: 0.0
                setMarket()
                Timber.d("Place found: ${place.name}")
            }.addOnFailureListener { exception: Exception ->
                if (exception is ApiException) {
                    Timber.e("Place not found: ${exception.message}")
                    val statusCode = exception.statusCode
                }
            }
    }

}