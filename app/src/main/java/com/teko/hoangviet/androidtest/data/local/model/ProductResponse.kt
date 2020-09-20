package com.teko.hoangviet.androidtest.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product",
    indices = [Index("id")]
)
data class ProductResponse(

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("price")
    val price: Double? = null,

    @field:SerializedName("imageUrl")
    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,

    @field:SerializedName("brand")
    val brand: String? = null,

    @field:SerializedName("dateAdded")
    @ColumnInfo(name = "date_added")
    val dateAdded: String? = null,

    @field:SerializedName("dateUpdated")
    @ColumnInfo(name = "date_updated")
    val dateUpdated: String? = null
) : Parcelable
