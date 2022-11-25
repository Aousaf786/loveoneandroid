package com.connaughttechnologies.lovedonce.ui.memorialsearch

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestSearchMemorialProfile
import com.connaughttechnologies.lovedonce.databinding.FragmentMemorialSearchBinding
import com.connaughttechnologies.lovedonce.extensions.getValue
import com.connaughttechnologies.lovedonce.ui.datepicker.DatePickerFragment

class MemorialSearchFragment :
    BaseFragment<MemorialSearchViewModel, FragmentMemorialSearchBinding>() {
    override var viewModelClass: Class<MemorialSearchViewModel> =
        MemorialSearchViewModel::class.java
    override var layoutId: Int = R.layout.fragment_memorial_search
    override var bindingVariable: Int = BR.viewModel
    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataYearSpinner = viewModel.getBirthYearData()
        var selectedBirthYear = ""
        var selectedDeathYear = ""
        binding.apply {
            toolbar.tvToolbarTitle.text = "Search"
            toolbar.sharedViewModel = sharedViewModel
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.country,
                R.layout.list_item_spinner
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerCountry.adapter = adapter
            }
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.state,
                R.layout.list_item_spinner
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.list_item_spinner)
                // Apply the adapter to the spinner
                spinnerState.adapter = adapter
            }
            btnSearch.setOnClickListener {
                val firstName = etFirstName.getValue()
                val midName = etMiddleName.getValue()
                val lastName = etLastName.getValue()
                val country = "UK"        //todo
                val state = "London"        //todo
                sharedViewModel.requestSearchMemorialProfile = RequestSearchMemorialProfile(
                    firstName,
                    midName,
                    lastName,
                    country,
                    state
                )
                findNavController().navigate(R.id.profileFragment)
            }
        }
    }

}