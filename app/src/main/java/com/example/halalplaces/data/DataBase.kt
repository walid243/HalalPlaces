package com.example.halalplaces.data

import com.example.halalplaces.data.model.MarkerData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow

object DataBase {
    val app: App = App.create(
        AppConfiguration.Builder("halalplaces-svtez")
            .log(LogLevel.ERROR)
            .build()
    )
    var currentUser: User? = null
     var realm: Realm? = null


    fun loggedIn() = currentUser?.loggedIn ?: false

    suspend fun login(userName: String, password: String): Boolean {
        val credentials = Credentials.emailPassword(userName, password)
        try {
            app.login(credentials)
        } catch(error:Exception) {
            return false
        }
        configureRealm()
        println("$currentUser <----------------------")
        return true


    }

    suspend fun register(userName: String, password: String) {
        app.emailPasswordAuth.registerUser(userName, password)
        login(userName, password)
    }

    suspend fun configureRealm(){
        currentUser = app.currentUser!!
        openRealm(config())
        subscribeToRealm()

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

    suspend fun subscribeToRealm() {
        requireNotNull(realm)
        realm!!.subscriptions.waitForSynchronization()
    }

    fun insert(markerData: MarkerData) {
        requireNotNull(realm)
        realm!!.writeBlocking {
            copyToRealm(markerData)
        }
    }

    fun getAllMarkers(): RealmResults<MarkerData> {
        requireNotNull(realm)
         return realm!!.query<MarkerData>().find()
    }

}