package com.connaughttechnologies.lovedonce.ui.resetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestResetPassword
import com.connaughttechnologies.lovedonce.databinding.FragmentResetPasswordBinding
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.extensions.validate

class ResetPasswordFragment : BaseFragment<ResetPasswordViewModel, FragmentResetPasswordBinding>() {
    override var viewModelClass: Class<ResetPasswordViewModel> = ResetPasswordViewModel::class.java
    override var layoutId: Int = R.layout.fragment_reset_password
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.tvToolbarTitle.text = ""
        binding.toolbar.sharedViewModel = sharedViewModel

        binding.btnReset.setOnClickListener {
            if (binding.etPassword.validate()) {
                if (binding.etConfirmPassword.validate()) {
                    if (binding.etPassword.getValue() == binding.etConfirmPassword.getValue()) {
                        sharedViewModel.eventExeApiResetPassword.value = RequestResetPassword(
                            sharedViewModel.otpVerifiedToken,
                            binding.etPassword.getValue(),
                            binding.etConfirmPassword.getValue()
                        )
                    } else {
                        showToast(resources.getString(R.string.str_sign_up_error_password_match))
                    }
                } else {
                    showToast(resources.getString(R.string.str_sign_up_error_password))
                }
            } else {
                showToast(resources.getString(R.string.str_sign_up_error_password))
            }
        }
    }
}