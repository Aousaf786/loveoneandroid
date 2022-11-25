package com.connaughttechnologies.lovedonce.ui.datepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val listener: DateSetInterface) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    interface DateSetInterface {
        fun dateSet(day: Int, month: Int, year: Int)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, dayOfMonth)
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        var month = p2 + 1
        if (month < 10) {
            month = "0$month".toInt()
        }
        listener.dateSet(p3, month, p1 - 2000)
    }
}