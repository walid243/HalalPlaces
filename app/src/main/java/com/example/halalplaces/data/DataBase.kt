package com.example.halalplaces.data

import com.example.halalplaces.data.model.MarkerData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.sync.SyncConfiguration

object DataBase {
    val app: App = App.create(
        AppConfiguration.Builder("halalplaces-svtez")
            .log(LogLevel.ERROR)
            .build()
    )
    var currentUser: User? = null
    lateinit var realm: Realm

    suspend fun login(userName: String, password: String): Boolean {
        val credentials = Credentials.emailPassword(userName, password)
        try {
            app.login(credentials)
        } catch(error:Exception) {
            return false
        }
        currentUser = app.currentUser!!
        println("$currentUser <----------------------")
        openRealm(config())
        subscripToRealm()

        return true


    }

    suspend fun register(userName: String, password: String) {
        app.emailPasswordAuth.registerUser(userName, password)
        login(userName, password)
    }

    fun config(): SyncConfiguration {
        println("$currentUser <----------------------")
        return SyncConfiguration.Builder(currentUser!! , setOf(MarkerData::class))
            .initialSubscriptions { realm ->
                add(
                    realm.query<MarkerData>(), "All Markers"
                )
            }
            .waitForInitialRemoteData()
            .build()

    }

    fun openRealm(config: SyncConfiguration) {
        realm = Realm.open(config)
    }

    suspend fun subscripToRealm() {
        realm.subscriptions.waitForSynchronization()
    }

    fun insert() {
        val sport = MarkerData(name = "", latitude = 0.0, longitude = 0.0, ownerId = currentUser!!.id)
        realm.writeBlocking {
            copyToRealm(sport)
        }
    }

}