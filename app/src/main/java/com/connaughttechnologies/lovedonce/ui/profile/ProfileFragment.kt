package com.connaughttechnologies.lovedonce.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestGetMyMemorialProfiles
import com.connaughttechnologies.lovedonce.data.models.responses.MemorialProfile
import com.connaughttechnologies.lovedonce.databinding.FragmentProfileBinding
import com.connaughttechnologies.lovedonce.ui.profile.adapter.ProfileAdapter

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {
    override var viewModelClass: Class<ProfileViewModel> = ProfileViewModel::class.java
    override var layoutId: Int = R.layout.fragment_profile
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = "Profiles"
        binding.toolbar.sharedViewModel = sharedViewModel
        if (sharedViewModel.requestSearchMemorialProfile == null) {
            sharedViewModel.eventExeApiGetMyMemorialProfiles.value = RequestGetMyMemorialProfiles()
        } else {
            sharedViewModel.eventExeApiSearchMemorialProfiles.value =
                sharedViewModel.requestSearchMemorialProfile
        }
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.liveMyMemorialProfiles.observe(
            viewLifecycleOwner,
            { responseGetMyMemorialProfiles ->
                responseGetMyMemorialProfiles.data.data.let {
                    setAdapter(it)
                }
            })
        sharedViewModel.liveSearchMemorialProfiles.observe(
            viewLifecycleOwner,
            { responseGetMyMemorialProfiles ->
                responseGetMyMemorialProfiles.data.data.let {
                    setAdapter(it)
                }
            })
    }

    private fun setAdapter(list: List<MemorialProfile>) {
        binding.rvProfile.adapter =
            ProfileAdapter(list, object : ProfileAdapter.ClickListener {
                override fun onClick(profile: MemorialProfile) {
                    sharedViewModel.selectedProfile = profile
                    findNavController().navigate(R.id.profileDetailFragment)
                }

            })
    }
}