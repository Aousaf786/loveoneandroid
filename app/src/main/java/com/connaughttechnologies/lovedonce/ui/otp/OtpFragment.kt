package com.connaughttechnologies.lovedonce.ui.otp

import android.os.Bundle
import android.view.View
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestResetPasswordOtpVerified
import com.connaughttechnologies.lovedonce.databinding.FragmentOtpBinding

class OtpFragment : BaseFragment<OtpViewModel, FragmentOtpBinding>() {
    override var viewModelClass: Class<OtpViewModel> = OtpViewModel::class.java
    override var layoutId: Int = R.layout.fragment_otp
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = ""
        binding.toolbar.sharedViewModel = sharedViewModel

        binding.btnSubmit.setOnClickListener {
            if (binding.otpView.text.toString().isNotEmpty()) {
                sharedViewModel.eventExeApiResetPasswordOtpVerified.value =
                    RequestResetPasswordOtpVerified(binding.otpView.text.toString())
            } else {
                showToast(resources.getString(R.string.str_otp_error))

            }
        }
    }
}