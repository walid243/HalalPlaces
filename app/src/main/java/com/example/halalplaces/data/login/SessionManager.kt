package com.example.halalplaces.data.login

import com.example.halalplaces.data.DataBase
import com.example.halalplaces.data.interfaces.LoginInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SessionManager : LoginInterface {
    override suspend fun login(email: String, password: String): Boolean {
                   var isSuccessful = DataBase.login(email, password)
                    if (!isSuccessful) isSuccessful = DataBase.register(email,password)
                    if (!isSuccessful) return false
                    return true
    }

    override suspend fun register(email: String, password: String): Boolean {
        return withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            DataBase.register(email, password)
        }
    }

    override fun isUserLogged(): Boolean {
        return DataBase.currentUser?.loggedIn ?: false
    }

    fun logOut(){
        if (DataBase.app.currentUser?.loggedIn == true) {
            CoroutineScope(Dispatchers.Default).launch {
                DataBase.app.currentUser!!.logOut()

            }
        }
    }

}