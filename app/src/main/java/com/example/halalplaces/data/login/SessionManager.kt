package com.example.halalplaces.data.login

import com.example.halalplaces.data.DataBase
import com.example.halalplaces.data.interfaces.LoginInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionManager(): LoginInterface {
    override fun login(email: String, password: String) {
            CoroutineScope(Dispatchers.Default).launch {
                   val isSuccessful = DataBase.login(email, password)
                    if (!isSuccessful) DataBase.register(email,password)
            }
    }

    override fun register(email: String, password: String) {
        CoroutineScope(Dispatchers.Default).launch{
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