package com.connaughttechnologies.lovedonce.ui.history

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
import com.connaughttechnologies.lovedonce.data.models.requests.RequestOrderListing
import com.connaughttechnologies.lovedonce.data.models.responses.OrderListing
import com.connaughttechnologies.lovedonce.databinding.FragmentHistoryBinding
import com.connaughttechnologies.lovedonce.ui.history.adapter.HistoryAdapter

class HistoryFragment : BaseFragment<HistoryViewModel, FragmentHistoryBinding>() {
    override var viewModelClass: Class<HistoryViewModel> = HistoryViewModel::class.java
    override var layoutId: Int = R.layout.fragment_history
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.eventExeApiOrderListing.value = RequestOrderListing()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = "Services History"
        binding.toolbar.sharedViewModel = sharedViewModel
        binding.tvPlaceholder.visibility = View.INVISIBLE
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.liveOrderListing.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.tvPlaceholder.visibility = View.VISIBLE
            } else {
                binding.tvPlaceholder.visibility = View.INVISIBLE
            }
            val adapter = HistoryAdapter(it, object : HistoryAdapter.ClickListener {
                override fun onClick(item: OrderListing) {
                    sharedViewModel.orderId = item.id
                    findNavController().navigate(R.id.orderStatusFragment)
                }

            })
            binding.rvHistory.adapter = adapter
        })
    }
}