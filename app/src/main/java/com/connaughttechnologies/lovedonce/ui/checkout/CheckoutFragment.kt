package com.connaughttechnologies.lovedonce.ui.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentCheckoutBinding
import com.connaughttechnologies.lovedonce.extensions.formatPrice


class CheckoutFragment : BaseFragment<CheckoutViewModel, FragmentCheckoutBinding>() {
    override var viewModelClass: Class<CheckoutViewModel> = CheckoutViewModel::class.java
    override var layoutId: Int = R.layout.fragment_checkout
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolbar.tvToolbarTitle.text = "Checkout"
        binding.toolbar.sharedViewModel = sharedViewModel

        binding.tvServiceTitle.text = sharedViewModel.liveServiceDetail.value!!.name
        binding.tvAddress.text = sharedViewModel.address
        binding.tvDate.text = sharedViewModel.orderDate
        binding.tvTime.text = sharedViewModel.orderTimeText
        binding.tvImmediate.text = if (sharedViewModel.immediate == 1) "Yes" else "No"
        binding.tvPrice.text = sharedViewModel.liveServiceDetail.value!!.price.formatPrice()

        binding.btnPayment.setOnClickListener {
            findNavController().navigate(R.id.paymentFragment)
        }
    }
}