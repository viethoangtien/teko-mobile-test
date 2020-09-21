package com.teko.hoangviet.androidtest.data.local.model

import androidx.room.*

@Entity(
    tableName = "cart",
    foreignKeys = [
        ForeignKey(
            entity = ProductResponse::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]
        )
    ],
    indices = [Index("product_id")]
)
data class Cart(
    @ColumnInfo(name = "product_id") val productId: Int,
    @ColumnInfo(name = "quantity") var quantity: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var cartId: Int = 0
}