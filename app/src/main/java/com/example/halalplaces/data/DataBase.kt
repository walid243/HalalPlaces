package com.example.halalplaces.data

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.Image
import android.media.ImageReader
import android.media.ThumbnailUtils
import com.example.halalplaces.data.model.MarkerData
import com.example.halalplaces.data.model.UserData
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
import kotlin.io.path.Path

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
        } catch (error: Exception) {
            return false
        }
        configureRealm()
        return true
    }

    suspend fun register(userEmail: String, password: String): Boolean {
        try {
            app.emailPasswordAuth.registerUser(userEmail, password)
            login(userEmail, password)
        } catch (error: Exception) {
            return false
        }
        if (currentUser != null) {

            insertUser(
                UserData(
                    _id = app.currentUser!!.id,
                    name = userEmail.substringBefore("@"),
                    email = userEmail,
                    avatar = null
                )
            )
        }
        return true
    }

    fun configureRealm() {
        currentUser = app.currentUser!!
        openRealm(getConfig())

    }

    private fun getConfig(): SyncConfiguration {
        return SyncConfiguration.Builder(currentUser!!, setOf(MarkerData::class, UserData::class))
            .initialSubscriptions { realm ->
                add(
                    realm.query<MarkerData>(), "All Markers"
                )
                add(
                    realm.query<UserData>("_id == $0", currentUser!!.id), "Current user data"
                )
            }
            .waitForInitialRemoteData()
            .build()
    }

    private fun openRealm(config: SyncConfiguration) {
        realm = Realm.open(config)

    }

    suspend fun subscribeToRealmAsync(): Deferred<Boolean> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                realm!!.subscriptions.waitForSynchronization()
                true
            } catch (ex: Exception) {
                false
            }
        }
    }

    fun insertMarker(markerData: MarkerData) {
        requireNotNull(realm)
        realm!!.writeBlocking {
            copyToRealm(markerData)
        }
    }

    fun insertUser(userData: UserData) {
        realm!!.writeBlocking {
            copyToRealm(userData)
        }
    }

    fun getAllMarkers(): RealmResults<MarkerData> {
        requireNotNull(realm)
        return realm!!.query<MarkerData>().find()
    }

    fun getUserData(): UserData? {
        requireNotNull(realm)
        return realm!!.query<UserData>("_id == $0", currentUser!!.id).find().first() ?: null
    }

}