package com.connaughttechnologies.lovedonce.ui.diary

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestDiaryData
import com.connaughttechnologies.lovedonce.data.models.requests.RequestUpdateDiaryData
import com.connaughttechnologies.lovedonce.data.models.responses.CriticalTasks
import com.connaughttechnologies.lovedonce.databinding.FragmentDiaryBinding

class DiaryFragment : BaseFragment<DiaryViewModel, FragmentDiaryBinding>() {
    override var viewModelClass: Class<DiaryViewModel> = DiaryViewModel::class.java
    override var layoutId: Int = R.layout.fragment_diary
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.tvToolbarTitle.text = "My Diary"
            toolbar.sharedViewModel = sharedViewModel
            etDailyAffirmation.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    update()
                }
                return@setOnEditorActionListener false
            }
            etNotes.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    update()
                }
                return@setOnEditorActionListener false
            }
            etTodayWin.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    update()
                }
                return@setOnEditorActionListener false
            }
            etToImprove.setOnEditorActionListener { textView, i, keyEvent ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    update()
                }
                return@setOnEditorActionListener false
            }
            if (sharedViewModel.diaryId == null) {
                btnSave.text = "Save"
            } else {
                btnSave.text = "Save Changes"
            }
            btnSave.setOnClickListener { update() }
        }
        sharedViewModel.diaryId?.let {
            getDiaryData(it)
        }
    }

    private fun getDiaryData(id: Int) {
        sharedViewModel.eventExeApiGetDiaryData.value = RequestDiaryData(id)
    }

    private fun update() {
        val id = sharedViewModel.diaryId
        val affirmation = viewModel.obDailyAffirmation.get()
        val notes = viewModel.obNotes.get()
        val wins = viewModel.obTodayWins.get()
        val improve = viewModel.obImprove.get()
        val task1: Int
        val task2: Int
        val task3: Int
        binding.apply {
            task1 = if (cbTask1.isChecked) 1 else 0
            task2 = if (cbTask2.isChecked) 1 else 0
            task3 = if (cbTask3.isChecked) 1 else 0
        }
        sharedViewModel.eventExeApiUpdateDiaryData.value =
            RequestUpdateDiaryData(
                id,
                affirmation ?: "",
                CriticalTasks(task1, task2, task3),
                notes ?: "",
                wins ?: "",
                improve ?: ""
            )

    }

    override fun observeSharedViewModel() {
        super.observeSharedViewModel()
        sharedViewModel.eventApiResponseGetDiaryData.observe(viewLifecycleOwner, {
            it?.data?.apply {
                binding.etDailyAffirmation.setText(dailyAffirmation)
                binding.cbTask1.isChecked = criticalTasks.task1 == 1
                binding.cbTask2.isChecked = criticalTasks.task2 == 1
                binding.cbTask3.isChecked = criticalTasks.task3 == 1
                binding.etNotes.setText(notes)
                binding.etTodayWin.setText(todayWins)
                binding.etToImprove.setText(toImprove)
            }
        })
    }
}