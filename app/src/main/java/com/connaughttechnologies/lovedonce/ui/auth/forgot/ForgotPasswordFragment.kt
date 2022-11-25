package com.connaughttechnologies.lovedonce.ui.auth.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestForgotPassword
import com.connaughttechnologies.lovedonce.databinding.FragmentForgotPasswordBinding
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.extensions.validate
import com.connaughttechnologies.lovedonce.extensions.validateEmail

class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordViewModel, FragmentForgotPasswordBinding>() {
    override var viewModelClass: Class<ForgotPasswordViewModel> =
        ForgotPasswordViewModel::class.java
    override var layoutId: Int = R.layout.fragment_forgot_password
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.toolbar.tvToolbarTitle.text = ""
//        binding.toolbar.sharedViewModel = sharedViewModel

        binding.btnSubmit.setOnClickListener {
            if (binding.etEmail.validateEmail()) {
                sharedViewModel.eventExeApiForgotPassword.value =
                    RequestForgotPassword(binding.etEmail.getValue())
            } else {
                showToast(resources.getString(R.string.str_login_error_email))
            }
        }
    }
}