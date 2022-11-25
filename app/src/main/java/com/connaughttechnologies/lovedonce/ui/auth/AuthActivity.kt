package com.connaughttechnologies.lovedonce.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseActivity
import com.connaughttechnologies.lovedonce.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity<AuthViewModel, ActivityAuthBinding>() {
    override var viewModelClass: Class<AuthViewModel> = AuthViewModel::class.java
    override var layoutId: Int = R.layout.activity_auth
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        viewModel.initDataLayer(dataLayer)
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.eventShowLoading.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        viewModel.eventResetPasswordOtpVerifiedSuccess.observe(this, Observer {
            sharedViewModel.otpVerifiedToken = it.token
            findNavController(R.id.nav_host_fragment).navigate(R.id.resetPasswordFragment)
        })

        viewModel.eventSuccessApiForgotPassword.observe(this, Observer {
            findNavController(R.id.nav_host_fragment).navigate(R.id.otpFragment)
        })
        viewModel.eventResetPasswordSuccess.observe(this, Observer {
            findNavController(R.id.nav_host_fragment).popBackStack(R.id.loginFragment, false)
        })
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()

        sharedViewModel.eventToolbarBackedClicked.observe(this, Observer {
            findNavController(R.id.nav_host_fragment).popBackStack()
        })

        sharedViewModel.eventExeSignUpApi.observe(this, Observer {
            viewModel.signUp(it)
        })

        sharedViewModel.eventExeApiSignIn.observe(this, Observer {
            viewModel.signIn(it)
        })

        sharedViewModel.eventExeApiForgotPassword.observe(this, Observer {
            viewModel.forgotPassword(it)
        })

        sharedViewModel.eventExeApiResetPasswordOtpVerified.observe(this, Observer {
            viewModel.resetPasswordOtpVerify(it)
        })

        sharedViewModel.eventExeApiResetPassword.observe(this, Observer {
            viewModel.resetPassword(it)
        })
    }
}