package com.example.halalplaces.data

import com.example.halalplaces.data.model.MarkerData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object DataBase {
    val app: App = App.create(
        AppConfiguration.Builder("halalplaces-svtez")
            .log(LogLevel.ERROR)
            .build()
    )
    var currentUser: User? = null
     var realm: Realm? = null


    fun loggedIn() = app.currentUser?.loggedIn ?: false

    suspend fun login(userName: String, password: String): Boolean {
        val credentials = Credentials.emailPassword(userName, password)
        try {
            app.login(credentials)
        } catch(error:Exception) {
            return false
        }
        configureRealm()
        return true


    }

    suspend fun register(userName: String, password: String) {
        app.emailPasswordAuth.registerUser(userName, password)
        login(userName, password)
    }

    suspend fun configureRealm(){
        currentUser = app.currentUser!!
        openRealm(getConfig())
        subscribeToRealm()

    }
    private fun getConfig(): SyncConfiguration {
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

    private fun openRealm(config: SyncConfiguration) {
        realm = Realm.open(config)
    }

    suspend fun subscribeToRealm():Deferred<Boolean> {
        requireNotNull(realm)
        return CoroutineScope(Dispatchers.IO).async {
            try {
                realm!!.subscriptions.waitForSynchronization()
                true
            } catch (ex:Exception){
                false
            }
        }
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