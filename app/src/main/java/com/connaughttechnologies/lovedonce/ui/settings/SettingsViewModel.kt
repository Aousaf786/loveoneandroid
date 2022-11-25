package com.connaughttechnologies.lovedonce.ui.settings

import com.connaughttechnologies.lovedonce.base.BaseViewModel
import com.connaughttechnologies.lovedonce.data.models.responses.User

class SettingsViewModel : BaseViewModel() {

    lateinit var user: User

    fun init(user: User){
        this.user = user
    }
}