package com.connaughttechnologies.lovedonce.ui.cart

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.responses.Service
import com.connaughttechnologies.lovedonce.databinding.FragmentCartBinding
import com.connaughttechnologies.lovedonce.extensions.formatPrice
import com.connaughttechnologies.lovedonce.ui.cart.adapter.CartAdapter

class CartFragment : BaseFragment<CartViewModel, FragmentCartBinding>() {
    override var viewModelClass: Class<CartViewModel> = CartViewModel::class.java
    override var layoutId: Int = R.layout.fragment_cart
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.tvToolbarTitle.text = "Cart"
            toolbar.sharedViewModel = sharedViewModel
            btnCheckout.setOnClickListener {
                findNavController().navigate(R.id.scheduleFragment)
            }
        }
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.liveListCart.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.tvPlaceholder.visibility = View.VISIBLE
                binding.layoutCheckout.visibility = View.INVISIBLE
            } else {
                binding.tvPlaceholder.visibility = View.INVISIBLE
                binding.layoutCheckout.visibility = View.VISIBLE
            }
            val adapter = CartAdapter(it, object : CartAdapter.ClickListener {
                override fun onCartRemove(item: Service) {
                    removeFromCart(item)
                }
            })
            binding.rvCart.adapter = adapter
            refreshTotal(it)
        })
    }

    private fun refreshTotal(list: MutableList<Service>) {
        var total = 0.0
        list.forEach {
            total += it.price
        }
        binding.tvTotalAmount.text = total.formatPrice()
    }
}