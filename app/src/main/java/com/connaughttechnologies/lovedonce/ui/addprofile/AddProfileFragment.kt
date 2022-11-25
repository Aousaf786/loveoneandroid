package com.connaughttechnologies.lovedonce.ui.addprofile

import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.MainActivity
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestAddMemorialProfile
import com.connaughttechnologies.lovedonce.data.models.requests.RequestImageUploading
import com.connaughttechnologies.lovedonce.databinding.FragmentAddProfileBinding
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.ui.datepicker.DatePickerFragment
import com.connaughttechnologies.lovedonce.utils.helpers.FileUtilsJava
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddProfileFragment : BaseFragment<AddProfileViewModel, FragmentAddProfileBinding>() {
    override var viewModelClass: Class<AddProfileViewModel> = AddProfileViewModel::class.java
    override var layoutId: Int = R.layout.fragment_add_profile
    override var bindingVariable: Int = BR.viewModel

    private lateinit var images: MutableList<String>

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        images = mutableListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataYearSpinner = viewModel.getBirthYearData()
        var selectedBirthYear = ""
        var selectedDeathYear = ""
        binding.apply {
            toolbar.tvToolbarTitle.text = "Add Profile"
            toolbar.sharedViewModel = sharedViewModel
            val adapterBirthYear = ArrayAdapter(
                requireContext(),
                R.layout.list_item_spinner,
                dataYearSpinner
            )
            spinnerInBirth.adapter = adapterBirthYear
            spinnerInBirth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedBirthYear = dataYearSpinner[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
            etBirth.setOnClickListener {
                DatePickerFragment(object : DatePickerFragment.DateSetInterface {
                    override fun dateSet(day: Int, month: Int, year: Int) {
                        binding.etBirth.text = "$day/$month"
                    }

                }).show(requireActivity().supportFragmentManager, "DatePickerFragment")
            }
            val adapterDeathYear = ArrayAdapter(
                requireContext(),
                R.layout.list_item_spinner,
                dataYearSpinner
            )
            spinnerInDeath.adapter = adapterDeathYear
            spinnerInDeath.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedDeathYear = dataYearSpinner[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
            etDeath.setOnClickListener {
                DatePickerFragment(object : DatePickerFragment.DateSetInterface {
                    override fun dateSet(day: Int, month: Int, year: Int) {
                        binding.etDeath.text = "$day/$month"
                    }

                }).show(requireActivity().supportFragmentManager, "DatePickerFragment")
            }
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.country,
                R.layout.list_item_spinner,
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerCountry.adapter = adapter
            }
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.state,
                R.layout.list_item_spinner,
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerState.adapter = adapter
            }

            tvBtnAddImages.setOnClickListener {
                if ((requireActivity() as MainActivity).checkWriteStoragePermission()) {
                    pickImageFromGallery()
                } else {
                    getMainActivity()!!.requestWriteStoragePermission()
                }
            }

            btnAdd.setOnClickListener {
                val firstName = etFirstName.getValue()
                val midName = etMiddleName.getValue()
                val lastName = etLastName.getValue()
                val birth = "${etBirth.text}/$selectedBirthYear"
                val death = "${etDeath.text}/$selectedDeathYear"
                val country = "America"        //todo
                val state = "London"        //todo
                sharedViewModel.eventExeApiAddProfile.value = RequestAddMemorialProfile(
                    firstName,
                    midName,
                    lastName,
                    birth,
                    death,
                    country,
                    state,
                    this@AddProfileFragment.images
                )
            }
        }
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.pickedImageUri.observe(viewLifecycleOwner, {
            it?.apply {
                val file = FileUtilsJava.getFile(requireContext(), this)
                binding.tvImages.text = file.name

                val ext = MimeTypeMap.getFileExtensionFromUrl(toString())
                val requestBody = file?.asRequestBody(
                    "multipart/form-data".toMediaTypeOrNull()
                )
                val partBody =
                    MultipartBody.Part.createFormData("image", file!!.name, requestBody!!)
                val requestBodyType = "memorial_img".toRequestBody("text/plain".toMediaTypeOrNull())
                sharedViewModel.eventExeApiImageUploading.value =
                    RequestImageUploading(image = partBody, type = requestBodyType)
            }
        })
        sharedViewModel.eventApiResponseImageUploading.observe(viewLifecycleOwner, {
            images.add(it.data.data)
        })
        sharedViewModel.eventApiResponseAddProfile.observe(viewLifecycleOwner, {
            showToast(it.message)
        })
    }

    override fun observeSingleLiveEvent() {
        super.observeSingleLiveEvent()
        getMainActivity()?.eventWriteStoragePermissionsResult?.observe(viewLifecycleOwner, {
            if (it) {
                pickImageFromGallery()
            }
        })
    }

    private fun pickImageFromGallery() {
        (requireActivity() as MainActivity).pickImageFromGallery()
    }
}