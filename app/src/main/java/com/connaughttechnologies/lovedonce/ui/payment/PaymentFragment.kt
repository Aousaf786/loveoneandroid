package com.connaughttechnologies.lovedonce.ui.payment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestCreateOrder
import com.connaughttechnologies.lovedonce.data.models.responses.Service
import com.connaughttechnologies.lovedonce.databinding.FragmentPaymentBinding
import com.connaughttechnologies.lovedonce.extensions.formatPrice
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.extensions.validate
import com.connaughttechnologies.lovedonce.extensions.validateCard
import com.connaughttechnologies.lovedonce.ui.datepicker.DatePickerFragment


class PaymentFragment : BaseFragment<PaymentViewModel, FragmentPaymentBinding>() {
    override var viewModelClass: Class<PaymentViewModel> = PaymentViewModel::class.java
    override var layoutId: Int = R.layout.fragment_payment
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = "PAYMENT"
        binding.toolbar.sharedViewModel = sharedViewModel

        binding.tvAmount.text = sharedViewModel.liveServiceDetail.value!!.price.formatPrice()
//        binding.tvExpDate.setOnClickListener {
//            DatePickerFragment(object : DatePickerFragment.DateSetInterface {
//                override fun dateSet(day: Int, month: Int, year: Int) {
//                    binding.tvExpDate.text = "$month / $year"
//                    sharedViewModel.cardExpYear = year
//                    sharedViewModel.cardExpMonth = month
//                }
//
//            }).show(requireActivity().supportFragmentManager, "DatePickerFragment")
//        }
        binding.btnPay.setOnClickListener {
            if (binding.etCardNumber.validateCard()) {
                if (binding.tvExpDate.getValue().isNotEmpty()) {
                    if (binding.etCvv.validate()) {
                        sharedViewModel.cardNumber = binding.etCardNumber.getValue()
                        sharedViewModel.cardCvv = binding.etCvv.getValue()
                        val expiryDate = binding.tvExpDate.getValue()
                        val split = expiryDate.split("/")
                        try {
                            sharedViewModel.cardExpMonth = split[0].toInt()
                            sharedViewModel.cardExpYear = split[1].toInt()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        val services = mutableListOf<Service>()
                        var totalPayableAmount = 0.0
                        sharedViewModel.listCart.forEach {
                            if (services.contains(it)) {
                                val index = services.indexOf(it)
                                services[index].apply {
                                    quantity++
                                    payableAmount = price * quantity
                                    services[index] = this
                                }
                            } else {
                                it.quantity = 1
                                it.payableAmount = it.price * it.quantity
                                services.add(it)
                            }
                            totalPayableAmount += it.price
                        }
                        sharedViewModel.eventExeApiCreateOrder.value = RequestCreateOrder(
                            services,
                            totalPayableAmount,
                            sharedViewModel.serviceId,
                            sharedViewModel.immediate,
                            sharedViewModel.orderDate,
                            sharedViewModel.orderTime,
                            sharedViewModel.address,
                            sharedViewModel.lat,
                            sharedViewModel.lng,
                            sharedViewModel.cardNumber,
                            sharedViewModel.cardExpMonth,
                            sharedViewModel.cardExpYear,
                            sharedViewModel.cardCvv
                        )
                    } else {
                        showToast(resources.getString(R.string.str_payment_error_cvv))
                    }
                } else {
                    showToast(resources.getString(R.string.str_payment_error_exp))
                }
            } else {
                showToast(resources.getString(R.string.str_payment_error_card))
            }
        }
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.eventCreateOrderSuccess.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(R.id.successFragment)
        })
    }
}