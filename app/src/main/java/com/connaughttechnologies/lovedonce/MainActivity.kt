package com.connaughttechnologies.lovedonce

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.android.billingclient.api.*
import com.connaughttechnologies.lovedonce.location.AppLocations
import com.connaughttechnologies.lovedonce.base.BaseActivity
import com.connaughttechnologies.lovedonce.data.models.requests.RequestUpdatePaymentStatus
import com.connaughttechnologies.lovedonce.databinding.ActivityMainBinding
import com.connaughttechnologies.lovedonce.permissions.AppPermissions
import com.connaughttechnologies.lovedonce.utils.SingleLiveEvent
import com.connaughttechnologies.lovedonce.utils.helpers.ActivityContracts
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), PurchasesUpdatedListener {

    lateinit var placesClient: PlacesClient
    override var viewModelClass: Class<MainViewModel> = MainViewModel::class.java
    override var layoutId: Int = R.layout.activity_main
    override var bindingVariable: Int = BR.viewModel

    private lateinit var permissions: AppPermissions
    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
        }
    private lateinit var billingClient: BillingClient

    private lateinit var activityContracts: ActivityContracts

    //single live events
    val eventWriteStoragePermissionsResult = SingleLiveEvent<Boolean>()

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityContracts = ActivityContracts(this)
        lifecycle.addObserver(activityContracts)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        viewModel.initDataLayer(dataLayer)
        permissions = AppPermissions(this)
        lifecycle.addObserver(permissions)
        lifecycle.addObserver(
            AppLocations(
                this,
                permissions
            )
        )
        initPlacesSdk()
        initInApp()
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                handlePurchase(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        val cal = Calendar.getInstance()
        cal.time = Date(purchase.purchaseTime)
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val purchaseDate = sdf.format(cal.time)
        val request = RequestUpdatePaymentStatus(
            purchase.orderId,
            purchase.skus.first(),
            purchase.purchaseToken,
            purchaseDate
        )
        viewModel.updatePaymentStatus(request, sharedViewModel.apiToken)
    }

    private fun initPlacesSdk() {
        // Initialize the SDK
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)

        // Create a new PlacesClient instance
        placesClient = Places.createClient(this)
    }

    private fun initInApp() {
        billingClient = BillingClient.newBuilder(this)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()
        sharedViewModel.billingClient = billingClient
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    sharedViewModel.isBillingClientReady = true
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                sharedViewModel.isBillingClientReady = false
            }
        })
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.eventShowLoading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        viewModel.eventResponseGetAllServices.observe(this, Observer {
            sharedViewModel.liveListServices.value = it
        })
        viewModel.eventResponseGetServiceDetail.observe(this, Observer {
            sharedViewModel.liveServiceDetail.value = it
        })

        viewModel.eventResponseCreateOrder.observe(this, Observer {
            sharedViewModel.eventCreateOrderSuccess.call()
        })

        viewModel.eventResponseOrderDetail.observe(this, Observer {
            sharedViewModel.eventOrderDetailSuccess.value = it
        })

        viewModel.eventResponseOrderDetailFailed.observe(this, Observer {
            sharedViewModel.eventOrderDetailFailed.call()
        })

        viewModel.eventResponseOrderCancel.observe(this, Observer {
            sharedViewModel.eventOrderCancelSuccess.call()
        })

        viewModel.eventResponseProfileDetail.observe(this, Observer {
            sharedViewModel.liveProfileDetail.value = it
        })

        viewModel.eventResponseGetMyMemorialProfiles.observe(this, Observer {
            sharedViewModel.liveMyMemorialProfiles.value = it
        })

        viewModel.eventResponseSearchMemorialProfile.observe(this, Observer {
            sharedViewModel.liveSearchMemorialProfiles.value = it
        })

        viewModel.eventResponseOrderListing.observe(this, Observer {
            sharedViewModel.liveOrderListing.value = it
        })

        viewModel.eventResponseSuccessServiceToken.observe(this, Observer {
            sharedViewModel.liveServiceToken.value = it
        })

        viewModel.eventResponseUpdateAdditionalInfo.observe(this, Observer {
            sharedViewModel.eventApiResponseUpdateAdditionalInfo.value = it
        })
        viewModel.eventResponseGetDiaryData.observe(this, Observer {
            sharedViewModel.eventApiResponseGetDiaryData.value = it
        })
        viewModel.eventResponseGetAllDiariesData.observe(this, Observer {
            sharedViewModel.eventApiResponseGetAllDiariesData.value = it
        })

        viewModel.eventResponseImageUploading.observe(this, Observer {
            sharedViewModel.eventApiResponseImageUploading.value = it
        })
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.eventToolbarBackedClicked.observe(this, Observer {
            findNavController(R.id.nav_host_fragment).popBackStack()
        })
        sharedViewModel.eventToolbarRightBtnClicked.observe(this, Observer {
            //todo
        })

        sharedViewModel.eventSharedShowLoading.observe(this, Observer {
            if (it)
                viewModel.showLoading()
            else
                viewModel.hideLoading()
        })

        sharedViewModel.eventExeApiUpdateProfile.observe(this, Observer {
            viewModel.updateProfile(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiGetProfileDetail.observe(this, Observer {
            viewModel.getProfileDetail(sharedViewModel.apiToken)
        })
        sharedViewModel.eventExeApiGetMyMemorialProfiles.observe(this, Observer {
            viewModel.getMyMemorialProfiles(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiSearchMemorialProfiles.observe(this, Observer {
            viewModel.searchMemorialProfile(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiGetAllServices.observe(this, Observer {
            viewModel.getAllServices(sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiGetServiceDetail.observe(this, Observer {
            viewModel.getServiceDetail(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiCreateOrder.observe(this, Observer {
            viewModel.createOrder(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiOrderDetail.observe(this, Observer {
            viewModel.orderDetail(it, sharedViewModel.apiToken)
        })
        sharedViewModel.eventExeApiOrderCancel.observe(this, Observer {
            viewModel.orderCancel(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiOrderListing.observe(this, Observer {
            viewModel.orderListing(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiGetServiceToken.observe(this, Observer {
            viewModel.getServiceToken(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiUpdateAdditionalInfo.observe(this, Observer {
            viewModel.updateAdditionalInfo(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiSetReminder.observe(this, Observer {
            viewModel.setReminder(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiGetDiaryData.observe(this, Observer {
            viewModel.getDiaryData(it, sharedViewModel.apiToken)
        })
        sharedViewModel.eventExeApiUpdateDiaryData.observe(this, Observer {
            viewModel.updateDiaryData(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiMyDiaries.observe(this, Observer {
            viewModel.geyMyDiaries(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiImageUploading.observe(this, Observer {
            viewModel.imageUploading(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventExeApiAddProfile.observe(this, Observer {
            viewModel.addMemorialProfile(it, sharedViewModel.apiToken)
        })

        sharedViewModel.eventLogout.observe(this, Observer {
            viewModel.logout()
            viewModel.eventNavigateAuth.call()
        })
    }

    fun setPickedImageUri(uri: Uri) {
        sharedViewModel.pickedImageUri.value = uri
    }

    fun pickImageFromGallery() {
        activityContracts.pickImageGallery()
    }

    fun checkWriteStoragePermission(): Boolean {
        return permissions.checkWriteStorage()
    }

    fun requestWriteStoragePermission() {
        activityContracts.requestWriteStoragePermission()
    }


}