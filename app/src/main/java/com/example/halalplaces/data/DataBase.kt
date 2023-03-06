package com.example.halalplaces.data

import com.example.halalplaces.data.model.MarkerData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.sync.SyncConfiguration

object DataBase {
    val app: App = App.create(
        AppConfiguration.Builder("halalplaces-wfubt")
            .log(LogLevel.ERROR)
            .build()
    )
    lateinit var currentUser: User
    lateinit var realm: Realm

    suspend fun login(userName: String, password: String) {
        val credentials = Credentials.emailPassword(userName, password)
        app.login(credentials)

        currentUser = app.currentUser!!
        openRealm(config())
        subscriptRealm()
    }

    suspend fun register(userName: String, password: String) {
        app.emailPasswordAuth.registerUser(userName, password)
        login(userName, password)
    }

    fun config(): SyncConfiguration {
        return SyncConfiguration.Builder(currentUser, setOf(MarkerData::class))
            .initialSubscriptions { realm ->
                add(
                    realm.query<MarkerData>(), "All Markers"
                )
            }
//            .waitForInitialRemoteData()
            .build()

    }

    fun openRealm(config: SyncConfiguration) {
        realm = Realm.open(config)
    }

    suspend fun subscriptRealm() {
        realm.subscriptions.waitForSynchronization()
    }

    fun insert() {
        val sport = MarkerData(ownerId = DataBase.currentUser.id)
        realm.writeBlocking {
            copyToRealm(sport)
        }
    }

}