package com.example.halalplaces.data.interfaces

interface LoginInterface {
        suspend fun login(email: String, password: String)
        suspend fun register(email: String, password: String)
        fun isUserLogged():Boolean





}