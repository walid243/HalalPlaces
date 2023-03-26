package com.example.halalplaces.data.interfaces

interface LoginInterface {
        suspend fun login(email: String, password: String): Boolean
        suspend fun register(email: String, password: String): Boolean
        fun isUserLogged():Boolean





}