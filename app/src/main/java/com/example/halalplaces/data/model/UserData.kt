package com.example.halalplaces.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

open class UserData(
    @PrimaryKey
    var _id: String,
    var name: String,
    var email: String,
    var avatar: ByteArray? = null
): RealmObject {
    constructor(): this(_id="" ,name="", email = "")
}