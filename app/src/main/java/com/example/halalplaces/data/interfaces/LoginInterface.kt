package com.example.halalplaces.data.interfaces

interface LoginInterface {
        fun login(email: String, password: String)
        fun register(email: String, password: String)
        fun isUserLogged():Boolean





}