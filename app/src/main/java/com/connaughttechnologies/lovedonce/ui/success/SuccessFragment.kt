package com.connaughttechnologies.lovedonce.ui.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentSuccessBinding


class SuccessFragment : BaseFragment<SuccessViewModel, FragmentSuccessBinding>() {
    override var viewModelClass: Class<SuccessViewModel> = SuccessViewModel::class.java
    override var layoutId: Int = R.layout.fragment_success
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDone.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
    }
}