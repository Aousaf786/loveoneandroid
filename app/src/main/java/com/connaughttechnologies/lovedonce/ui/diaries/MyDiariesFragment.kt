package com.connaughttechnologies.lovedonce.ui.diaries

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestDiaryData
import com.connaughttechnologies.lovedonce.data.models.requests.RequestMyDiaries
import com.connaughttechnologies.lovedonce.databinding.FragmentMyDiariesBinding
import com.connaughttechnologies.lovedonce.ui.diaries.adapter.MyDiariesAdapter

class MyDiariesFragment : BaseFragment<MyDiariesViewModel, FragmentMyDiariesBinding>() {
    override var viewModelClass: Class<MyDiariesViewModel> = MyDiariesViewModel::class.java
    override var layoutId: Int = R.layout.fragment_my_diaries
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.tvToolbarTitle.text = "My Diaries"
            toolbar.sharedViewModel = sharedViewModel
            tvBtnNewDiary.setOnClickListener {
                sharedViewModel.diaryId = null
                findNavController().navigate(R.id.diaryFragment)
            }
        }
        getMyDiariesData()
    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.eventApiResponseGetAllDiariesData.observe(viewLifecycleOwner) {
            it?.data?.data?.apply {
                if (isEmpty()) {
                    binding.tvPlaceholder.visibility = View.VISIBLE
                } else {
                    binding.tvPlaceholder.visibility = View.INVISIBLE
                }
                val adapter = MyDiariesAdapter(this, object : MyDiariesAdapter.ClickListener {
                    override fun onClick(id: Int) {
                        sharedViewModel.diaryId = id
                        findNavController().navigate(R.id.diaryFragment)
                    }
                })
                binding.rvMyDiaries.adapter = adapter
            }
        };
    }

    private fun getMyDiariesData() {
        sharedViewModel.eventExeApiMyDiaries.value = RequestMyDiaries()
    }
}