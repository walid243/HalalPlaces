package com.example.halalplaces.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.halalplaces.data.login.SessionManager

class AppViewModel: ViewModel() {
    val sessionManager = SessionManager()
    val isLoggedIn = MutableLiveData(false)

    fun setIsLoggedIn() {
        isLoggedIn.postValue(true)
    }
    fun setLoggedOut() {
        isLoggedIn.postValue(false)
    }


}
