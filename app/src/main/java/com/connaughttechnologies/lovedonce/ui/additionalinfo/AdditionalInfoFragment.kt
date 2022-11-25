package com.connaughttechnologies.lovedonce.ui.additionalinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentAdditionalInfoBinding

class AdditionalInfoFragment :
    BaseFragment<AdditionalInfoViewModel, FragmentAdditionalInfoBinding>() {
    override var viewModelClass: Class<AdditionalInfoViewModel> =
        AdditionalInfoViewModel::class.java
    override var layoutId: Int = R.layout.fragment_additional_info
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.tvToolbarTitle.text = "Additional Info"
            toolbar.sharedViewModel = sharedViewModel
            etAdditionalInfo.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    this@AdditionalInfoFragment.viewModel.addAdditionalInfo()
                }
                return@setOnEditorActionListener false
            }
        }
    }

    override fun observeViewModel() {
        super.observeViewModel()
        viewModel.eventExeApi.observe(viewLifecycleOwner, {
            sharedViewModel.eventExeApiUpdateAdditionalInfo.value = it
        })
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.eventApiResponseUpdateAdditionalInfo.observe(viewLifecycleOwner, {
            fetchProfileDetail()
            navigateBack()
        })
        sharedViewModel.liveProfileDetail.observe(viewLifecycleOwner, Observer {
            it?.apply {
                binding.etAdditionalInfo.setText(additionalInfo)
            }
        })
    }
}