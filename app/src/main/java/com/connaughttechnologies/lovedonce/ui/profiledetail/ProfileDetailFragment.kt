package com.connaughttechnologies.lovedonce.ui.profiledetail

import android.os.Bundle
import android.view.View
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.responses.MemorialProfile
import com.connaughttechnologies.lovedonce.databinding.FragmentProfileDetailBinding
import com.connaughttechnologies.lovedonce.ui.profiledetail.adapter.ProfileDetailAdapter

class ProfileDetailFragment : BaseFragment<ProfileDetailViewModel, FragmentProfileDetailBinding>() {
    override var viewModelClass: Class<ProfileDetailViewModel> = ProfileDetailViewModel::class.java
    override var layoutId: Int = R.layout.fragment_profile_detail
    override var bindingVariable: Int = BR.viewModel

    private var profile: MemorialProfile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profile = sharedViewModel.selectedProfile
    }

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.tvToolbarTitle.text = "Profile Detail"
            toolbar.sharedViewModel = sharedViewModel
            profile?.let { memorialProfile ->
                tvName.text = memorialProfile.firstName
                rvProfileDetail.adapter =
                    ProfileDetailAdapter(memorialProfile.images,object : ProfileDetailAdapter.ClickListener {
                        override fun onClick() {

                        }

                    })
            }
        }
    }
}