package com.connaughttechnologies.lovedonce.ui.home

import com.connaughttechnologies.lovedonce.base.BaseViewModel
import com.connaughttechnologies.lovedonce.data.models.responses.User

class HomeViewModel : BaseViewModel() {

    lateinit var user: User

    fun init(user: User){
        this.user = user
    }
}