package com.example.halalplaces.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


open class PlaceData(
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke(),
    var name: String,
    var latitude: Double,
    var longitude: Double,
    var ownerId: String,
    var description: String? = null,
    var image: ByteArray? = null
): RealmObject {
    constructor(): this(name = "", longitude = 0.0, latitude = 0.0, ownerId = "")
}