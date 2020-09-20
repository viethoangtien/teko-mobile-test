package com.teko.hoangviet.androidtest.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductResponse(

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("brand")
    val brand: String? = null,

    @field:SerializedName("dateAdded")
    val dateAdded: String? = null,

    @field:SerializedName("dateUpdated")
    val dateUpdated: String? = null
) : Parcelable
