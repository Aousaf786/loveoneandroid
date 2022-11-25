package com.connaughttechnologies.lovedonce.ui.updateprofile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestSignUp
import com.connaughttechnologies.lovedonce.data.models.requests.RequestUpdateProfile
import com.connaughttechnologies.lovedonce.databinding.FragmentUpdateProfileBinding
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.extensions.validate
import com.connaughttechnologies.lovedonce.extensions.validateEmail

class UpdateProfileFragment : BaseFragment<UpdateProfileViewModel, FragmentUpdateProfileBinding>() {
    override var viewModelClass: Class<UpdateProfileViewModel> = UpdateProfileViewModel::class.java
    override var layoutId: Int = R.layout.fragment_update_profile
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.tvToolbarTitle.text = "Update"
        binding.toolbar.sharedViewModel = sharedViewModel
        sharedViewModel.eventExeApiGetProfileDetail.call()
        binding.btnSave.setOnClickListener {
            if (binding.etFirstName.validate()) {
                if (binding.etPhone.validate()) {
                    if (binding.etAddress.validate()) {
                        if (binding.etPassword.getValue()
                                .isNotEmpty() && binding.etConfirmPassword.getValue().isNotEmpty()
                        ) {
                            if (binding.etPassword.getValue() == binding.etConfirmPassword.getValue()) {
                                updateProfile()
                            } else {
                                showToast(resources.getString(R.string.str_sign_up_error_password_match))
                            }

                        } else {
                            updateProfile()
                        }
                    } else {
                        showToast(resources.getString(R.string.str_sign_up_error_address))
                    }
                } else {
                    showToast(resources.getString(R.string.str_sign_up_error_phone))
                }
            } else {
                showToast(resources.getString(R.string.str_sign_up_error_name))
            }
        }
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.liveProfileDetail.observe(viewLifecycleOwner, Observer {
            it?.apply {
                binding.etFirstName.setText(name)
                binding.etPhone.setText(phoneNumber)
                binding.etAddress.setText(address)
            }
        })
    }

    private fun updateProfile() {
        sharedViewModel.eventExeApiUpdateProfile.value = RequestUpdateProfile(
            binding.etFirstName.getValue(),
            binding.etPassword.getValue(),
            binding.etConfirmPassword.getValue(),
            binding.etPhone.getValue(),
            binding.etAddress.getValue()
        )
    }
}