package com.example.halalplaces.data

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.halalplaces.data.DataBase.loggedIn
import com.example.halalplaces.data.login.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class AppViewModel : ViewModel() {
    val sessionManager = SessionManager()
    val isLoggedIn = MutableLiveData<Boolean>()

    init {
        start()
    }

    fun setIsLoggedIn() {
        isLoggedIn.postValue(true)
    }

    fun setLoggedOut() {
        isLoggedIn.postValue(false)
    }

    private fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            if (loggedIn()) {
                DataBase.configureRealm()
                setIsLoggedIn()
            } else {
                setLoggedOut()
            }
        }
    }


}
