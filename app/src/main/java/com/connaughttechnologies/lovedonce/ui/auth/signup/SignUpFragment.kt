package com.connaughttechnologies.lovedonce.ui.auth.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestSignUp
import com.connaughttechnologies.lovedonce.databinding.FragmentSignUpBinding
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.extensions.validate
import com.connaughttechnologies.lovedonce.extensions.validateEmail
import com.connaughttechnologies.lovedonce.extensions.validatePhone

class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignUpBinding>() {
    override var viewModelClass: Class<SignUpViewModel> = SignUpViewModel::class.java
    override var layoutId: Int = R.layout.fragment_sign_up
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.toolbar.tvToolbarTitle.text = ""
//        binding.toolbar.sharedViewModel = sharedViewModel

        binding.btnSignUp.setOnClickListener {
            if (binding.etFirstName.validate()) {
                if (binding.etEmail.validateEmail()) {
                        if (binding.etPassword.validate()) {
                            if (binding.etConfirmPassword.validate()) {
                                if (binding.etPassword.getValue() == binding.etConfirmPassword.getValue()) {
                                    sharedViewModel.eventExeSignUpApi.value = RequestSignUp(
                                        binding.etFirstName.getValue(),
                                        binding.etEmail.getValue(),
                                        binding.etPassword.getValue(),
                                        binding.etConfirmPassword.getValue(),
                                        sharedViewModel.fcmToken,
                                        "",
                                        ""
                                    )
                                } else {
                                    showToast(resources.getString(R.string.str_sign_up_error_password_match))
                                }
                            } else {
                                showToast(resources.getString(R.string.str_sign_up_error_confirm_password))
                            }
                        } else {
                            showToast(resources.getString(R.string.str_sign_up_error_password))
                        }
                } else {
                    showToast(resources.getString(R.string.str_login_error_email))
                }
            } else {
                showToast(resources.getString(R.string.str_sign_up_error_name))
            }
        }
        binding.layoutLogin.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}