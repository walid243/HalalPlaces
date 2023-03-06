package com.example.halalplaces.data.model

import com.example.halalplaces.data.DataBase
import com.google.android.gms.maps.model.LatLng
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


open class MarkerData(
    @PrimaryKey
    val _id: ObjectId = ObjectId.create(),
    val name: String = "",
    val coordinates: LatLng = LatLng(0.0,0.0),
    val ownerId: String = ""
): RealmObject {
    constructor(): this(name = "")
}