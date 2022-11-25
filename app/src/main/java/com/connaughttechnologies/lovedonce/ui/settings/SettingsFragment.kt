package com.connaughttechnologies.lovedonce.ui.settings

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {
    override var viewModelClass: Class<SettingsViewModel> = SettingsViewModel::class.java
    override var layoutId: Int = R.layout.fragment_settings
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.tvToolbarTitle.text = "Settings"
        binding.toolbar.sharedViewModel = sharedViewModel

        lifecycleScope.launch {
            viewModel.init(repositoryPref.user.first())
        }

        binding.tvUpdateProfile.setOnClickListener {
            findNavController().navigate(R.id.updateProfileFragment)
        }

        binding.tvOrderHistory.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }
        binding.tvSetReminders.setOnClickListener {
            findNavController().navigate(R.id.reminderFragment)
        }
        binding.tvMyDiary.setOnClickListener {
            findNavController().navigate(R.id.myDiariesFragment)
        }
        binding.tvAdditionalInfo.setOnClickListener {
            findNavController().navigate(R.id.additionalInfoFragment)
        }
        binding.tvGroupChat.setOnClickListener {
            sharedViewModel.isGroupChat = true
            findNavController().navigate(R.id.chatFragment)
        }
        binding.tvSubscription.setOnClickListener {
            findNavController().navigate(R.id.subscriptionsFragment)
        }
        binding.tvAddProfile.setOnClickListener {
            findNavController().navigate(R.id.addProfileFragment)
        }

        binding.tvLogout.setOnClickListener {
            sharedViewModel.eventLogout.call()
        }
    }
}