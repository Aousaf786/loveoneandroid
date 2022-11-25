package com.connaughttechnologies.lovedonce.ui.reminder

import android.os.Bundle
import android.view.View
import com.connaughttechnologies.lovedonce.BR
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.base.BaseFragment
import com.connaughttechnologies.lovedonce.data.models.requests.RequestSetReminder
import com.connaughttechnologies.lovedonce.databinding.FragmentReminderBinding
import com.connaughttechnologies.lovedonce.ui.timepicker.TimePickerFragment
import timber.log.Timber
import java.util.*


class ReminderFragment : BaseFragment<ReminderViewModel, FragmentReminderBinding>() {
    override var viewModelClass: Class<ReminderViewModel> = ReminderViewModel::class.java
    override var layoutId: Int = R.layout.fragment_reminder
    override var bindingVariable: Int = BR.viewModel

    private var reminderDate: String? = null
    private var reminderTime: String? = null

    override fun initViewModel() {
        binding.viewModel = viewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.tvToolbarTitle.text = "Reminder"
        binding.toolbar.sharedViewModel = sharedViewModel
        binding.calendarView.date = Date().time
        setCurrentDateTime()

        binding.calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            Timber.d("$year-$month-$dayOfMonth")
            val m = month + 1
            val strMonth = if (m < 10) "0$m" else "$m"
            val strDom = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
            reminderDate = "$year-$strMonth-$strDom"
        }
        binding.tvTime.setOnClickListener {
            TimePickerFragment(object : TimePickerFragment.TimeSetInterface {
                override fun onTimeSet(hourOfDay: Int, minute: Int, amPm: String) {
                    val strHod = if (hourOfDay < 10) "0$hourOfDay" else "$hourOfDay"
                    val strMinute = if (minute < 10) "0$minute" else "$minute"
                    reminderTime = "$strHod:$strMinute:00"
                    binding.tvTime.text = "$strHod:$strMinute $amPm"
                }
            }).show(requireActivity().supportFragmentManager, "TimePickerFragment")
        }

        binding.btnContinue.setOnClickListener {
            if (reminderDate != null && reminderTime != null) {
                sharedViewModel.eventExeApiSetReminder.value =
                    RequestSetReminder(
                        reminderDate!!,
                        reminderTime!!,
                        timezone = getTimeZone(),
                        fcmToken = sharedViewModel.fcmToken
                    )
            }
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
        reminderDate = "$year-$strMonth-$strDom"
        val strHod = if (hour12 < 10) "0$hour12" else "$hour12"
        val strMinute = if (minute < 10) "0$minute" else "$minute"
        reminderTime = "$strHod:$strMinute:00"
        sharedViewModel.orderTimeText = "$strHod:$strMinute $amPm"
        binding.tvTime.text = sharedViewModel.orderTimeText
        binding.calendarView.date = currentCal.time.time
    }

}