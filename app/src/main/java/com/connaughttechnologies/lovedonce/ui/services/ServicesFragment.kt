package com.connaughttechnologies.lovedonce.ui.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestGetAllServices
import com.connaughttechnologies.lovedonce.databinding.FragmentServicesBinding
import com.connaughttechnologies.lovedonce.ui.services.adapter.ServicesAdapter
import com.connaughttechnologies.lovedonce.utils.GridSpacingItemDecoration

class ServicesFragment : BaseFragment<ServicesViewModel, FragmentServicesBinding>() {
    override var viewModelClass: Class<ServicesViewModel> = ServicesViewModel::class.java
    override var layoutId: Int = R.layout.fragment_services
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.tvToolbarTitle.text = "Services"
        binding.toolbar.sharedViewModel = sharedViewModel
        sharedViewModel.eventExeApiGetAllServices.value = RequestGetAllServices()
        binding.rvServices.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                resources.getDimensionPixelSize(R.dimen.default_margin)
            )
        )
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.liveListServices.observe(viewLifecycleOwner, Observer {
            val adapter =
                ServicesAdapter(it, object : ServicesAdapter.ClickListener {
                    override fun onClick(id: Int) {
                        sharedViewModel.serviceId = id
                        findNavController().navigate(R.id.servicesDetailsFragment)
                    }
                })
            binding.rvServices.adapter = adapter
        })
    }
}