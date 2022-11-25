package com.connaughttechnologies.lovedonce.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestLogin
import com.connaughttechnologies.lovedonce.databinding.FragmentLoginBinding
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.extensions.validate
import com.connaughttechnologies.lovedonce.extensions.validateEmail

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    override var viewModelClass: Class<LoginViewModel> = LoginViewModel::class.java
    override var layoutId: Int = R.layout.fragment_login
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            if (binding.etEmail.validateEmail()) {
                if (binding.etPassword.validate()) {
                    sharedViewModel.eventExeApiSignIn.value = RequestLogin(
                        binding.etEmail.getValue(),
                        binding.etPassword.getValue(),
                        sharedViewModel.fcmToken
                    )
                } else {
                    showToast(resources.getString(R.string.str_login_error_password))
                }
            } else {
                showToast(resources.getString(R.string.str_login_error_email))
            }
        }

        binding.tvForgot.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }

        binding.layoutSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
    }
}