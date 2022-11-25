package com.connaughttechnologies.lovedonce.ui.diary

import androidx.databinding.ObservableField
import com.connaughttechnologies.lovedonce.base.BaseViewModel

class DiaryViewModel : BaseViewModel() {

    val obDailyAffirmation = ObservableField<String?>()
    val obNotes = ObservableField<String?>()
    val obTodayWins = ObservableField<String?>()
    val obImprove = ObservableField<String?>()
}