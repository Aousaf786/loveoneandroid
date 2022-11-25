package com.connaughttechnologies.lovedonce.ui.orderstatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestCreateOrder
import com.connaughttechnologies.lovedonce.data.models.requests.RequestOrderCancel
import com.connaughttechnologies.lovedonce.data.models.requests.RequestOrderDetail
import com.connaughttechnologies.lovedonce.data.models.responses.OrderDetail
import com.connaughttechnologies.lovedonce.databinding.FragmentOrderStatusBinding

class OrderStatusFragment : BaseFragment<OrderViewModel, FragmentOrderStatusBinding>() {
    override var viewModelClass: Class<OrderViewModel> = OrderViewModel::class.java
    override var layoutId: Int = R.layout.fragment_order_status
    override var bindingVariable: Int = BR.viewModel
    private var orderDetail: OrderDetail? = null

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.eventExeApiOrderDetail.value = RequestOrderDetail(sharedViewModel.orderId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = "Order Status"
        binding.toolbar.sharedViewModel = sharedViewModel
        binding.tvPlaceholder.visibility = View.INVISIBLE
        binding.layoutMainContent.visibility = View.INVISIBLE
        binding.btnCancelOrder.setOnClickListener {
            orderDetail?.apply {
                sharedViewModel.eventExeApiOrderCancel.value = RequestOrderCancel(id)
            }
        }
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.eventOrderDetailSuccess.observe(viewLifecycleOwner, Observer {
            orderDetail = it
            binding.tvPlaceholder.visibility = View.INVISIBLE
            binding.layoutMainContent.visibility = View.VISIBLE
            binding.tvDate.text = "${it.orderDate} ${it.orderTime}"
            binding.tvAddress.text = it.address
            binding.tvStatus.text = it.statusString
            if (it.status > 1) {
                binding.btnCancelOrder.visibility = View.GONE
            } else {
                binding.btnCancelOrder.visibility = View.VISIBLE
            }
        })

        sharedViewModel.eventOrderDetailFailed.observe(viewLifecycleOwner, Observer {
            binding.tvPlaceholder.visibility = View.VISIBLE
            binding.layoutMainContent.visibility = View.INVISIBLE
        })
        sharedViewModel.eventOrderCancelSuccess.observe(viewLifecycleOwner, Observer {
            sharedViewModel.eventExeApiOrderDetail.value =
                RequestOrderDetail(sharedViewModel.orderId)
        })
    }
}