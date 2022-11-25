package com.connaughttechnologies.lovedonce.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.base.SharedViewModel
import com.connaughttechnologies.lovedonce.data.models.requests.RequestGetServiceDetail
import com.connaughttechnologies.lovedonce.databinding.FragmentServicesDetailsBinding
import com.connaughttechnologies.lovedonce.extensions.formatPrice

class ServicesDetailsFragment :
    BaseFragment<ServicesDetailsViewModel, FragmentServicesDetailsBinding>() {
    override var viewModelClass: Class<ServicesDetailsViewModel> =
        ServicesDetailsViewModel::class.java
    override var layoutId: Int = R.layout.fragment_services_details
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.eventExeApiGetServiceDetail.value =
            RequestGetServiceDetail(sharedViewModel.serviceId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = "Services Details"
        binding.toolbar.sharedViewModel = sharedViewModel
        binding.btnBuy.setOnClickListener {
            showToast("Item Added in Cart.")
            sharedViewModel.liveServiceDetail.value?.let { it1 -> addToCart(it1) }
        }
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.liveServiceDetail.observe(viewLifecycleOwner, Observer {
            binding.tvTitleDetails.text = it.name
            binding.tvPrice.text = it.price.formatPrice()
            binding.tvDesc.text = it.description
            it.coverImg?.let { coverImg ->
                Glide.with(binding.ivService).load(coverImg).into(binding.ivService)
            }
        })
    }
}