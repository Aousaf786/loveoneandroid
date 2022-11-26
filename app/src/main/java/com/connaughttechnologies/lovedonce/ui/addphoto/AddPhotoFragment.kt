package com.connaughttechnologies.lovedonce.ui.addphoto

import android.os.Bundle
import android.view.View
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentAddPhotoBinding

class AddPhotoFragment : BaseFragment<AddPhotoViewModel, FragmentAddPhotoBinding>() {
    override var viewModelClass: Class<AddPhotoViewModel> = AddPhotoViewModel::class.java
    override var layoutId: Int = R.layout.fragment_add_photo
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        //TODO("Not yet implemented")
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivPhoto.setOnClickListener {
            pickPhoto()
        }

        binding.tvDesc.setOnClickListener {
            pickPhoto()
        }
        if (viewModel.isPhotoAdded()) {
            binding.tvDesc.text = resources.getString(R.string.add_photo_new)
        } else {
            binding.tvDesc.text = resources.getString(R.string.add_photo)
        }
        binding.btnSave.setOnClickListener {
            viewModel.onClickSave()
        }
    }

    private fun pickPhoto() {
        if (getMainActivity()?.checkWriteStoragePermission() == true) {
            getMainActivity()?.pickImageFromGallery()
        } else {
            getMainActivity()?.requestWriteStoragePermission()
        }
    }
}