package com.connaughttechnologies.lovedonce.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.databinding.FragmentScheduleBinding
import com.connaughttechnologies.lovedonce.ui.timepicker.TimePickerFragment
import timber.log.Timber
import java.util.*

class ScheduleFragment : BaseFragment<ScheduleViewModel, FragmentScheduleBinding>() {
    override var viewModelClass: Class<ScheduleViewModel> = ScheduleViewModel::class.java
    override var layoutId: Int = R.layout.fragment_schedule
    override var bindingVariable: Int = BR.viewModel

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = "Schedule"
        binding.toolbar.sharedViewModel = sharedViewModel


        binding.calendarView.date = Date().time
        setCurrentDateTime()
        binding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            Timber.d("$year-$month-$dayOfMonth")
            val m = month + 1
            val strMonth = if (m < 10) "0$m" else "$m"
            val strDom = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
            sharedViewModel.orderDate = "$year-$strMonth-$strDom"
            sharedViewModel.immediate = 0
            binding.tvImmediate.background =
                resources.getDrawable(R.drawable.bg_filled_corner_primary_dark)
        }

        binding.tvTime.setOnClickListener {
            TimePickerFragment(object : TimePickerFragment.TimeSetInterface {
                override fun onTimeSet(hourOfDay: Int, minute: Int, amPm: String) {
                    val strHod = if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
                    val strMinute = if (minute < 10) "0$minute" else "$minute"
                    sharedViewModel.orderTime = "$strHod:$strMinute:00"
                    sharedViewModel.orderTimeText = "$strHod:$strMinute $amPm"
                    binding.tvTime.text = "$strHod:$strMinute $amPm"
                }
            }).show(requireActivity().supportFragmentManager, "TimePickerFragment")
        }

        binding.tvImmediate.setOnClickListener {
            binding.tvImmediate.background = resources.getDrawable(R.drawable.bg_schedule_selected)
            setCurrentDateTime()
            sharedViewModel.immediate = 1
        }

        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.mapFragment)
        }
    }

    private fun setCurrentDateTime() {
        val currentCal = Calendar.getInstance()
        val year = currentCal.get(Calendar.YEAR)
        val month = currentCal.get(Calendar.MONTH)
        val dayOfMonth = currentCal.get(Calendar.DAY_OF_MONTH)
        val hourOfDay = currentCal.get(Calendar.HOUR_OF_DAY)
        val minute = currentCal.get(Calendar.MINUTE)
        var amPm: String
        var hour12 = 0
        if (hourOfDay >= 12) {
            hour12 = hourOfDay % 12
            amPm = "PM"
        } else {
            hour12 = hourOfDay
            amPm = "AM"

        }
        val m = month + 1
        val strMonth = if (m < 10) "0$m" else "$m"
        val strDom = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
        sharedViewModel.orderDate = "$year-$strMonth-$strDom"
        val strHod = if (hour12 < 10) "0$hour12" else "$hour12"
        val strMinute = if (minute < 10) "0$minute" else "$minute"
        sharedViewModel.orderTime = "$strHod:$strMinute:00"
        sharedViewModel.orderTimeText = "$strHod:$strMinute $amPm"
        binding.tvTime.text = sharedViewModel.orderTimeText
        binding.calendarView.date = currentCal.time.time
    }
}