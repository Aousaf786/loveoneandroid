package com.connaughttechnologies.lovedonce.ui.subscriptions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.*
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentSubscriptionsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscriptionsFragment : BaseFragment<SubscriptionViewModel, FragmentSubscriptionsBinding>() {
    override var viewModelClass: Class<SubscriptionViewModel> = SubscriptionViewModel::class.java
    override var layoutId: Int = R.layout.fragment_subscriptions
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = "Subscription Plan"
        binding.toolbar.sharedViewModel = sharedViewModel
        binding.sub1.setOnClickListener {
            lifecycleScope.launch {
                val skuDetailsResult = querySkuDetails()
                // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
                val flowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetailsResult.skuDetailsList!!.first())
                    .build()
                val responseCode = sharedViewModel.billingClient.launchBillingFlow(
                    requireActivity(),
                    flowParams
                ).responseCode
            }
        }
        binding.sub2.setOnClickListener {
            lifecycleScope.launch {
                val skuDetailsResult = querySkuDetails()
                // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
                val flowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetailsResult.skuDetailsList!![1])
                    .build()
                val responseCode = sharedViewModel.billingClient.launchBillingFlow(
                    requireActivity(),
                    flowParams
                ).responseCode
            }
        }
    }

    suspend fun querySkuDetails(): SkuDetailsResult {
        val skuList = ArrayList<String>()
        skuList.add("basic")
        skuList.add("ultimate")
//        skuList.add("gas")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)

        // leverage querySkuDetails Kotlin extension function
        val skuDetailsResult = withContext(Dispatchers.IO) {
            sharedViewModel.billingClient.querySkuDetails(params.build())
        }
        // Process the result.
        return skuDetailsResult
    }
}