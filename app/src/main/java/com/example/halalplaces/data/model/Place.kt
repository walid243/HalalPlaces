package com.example.halalplaces.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Place(val name: String, var description: String?, var image: ByteArray? ): Parcelable