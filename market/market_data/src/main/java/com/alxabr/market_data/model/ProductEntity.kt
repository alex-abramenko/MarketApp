package com.alxabr.market_data.model

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity
internal data class ProductEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    val price: Price,
    val feedback: Feedback,
    val tags: List<String>,
    val available: Long,
    val description: String,
    val infoList: List<Info>,
    val ingredients: String,
    val isFavorite: Boolean = false
) {

    data class Price(
        val price: Long,
        @IntRange(from = 0L, to = 100L) val discount: Int,
        val priceWithDiscount: Long,
        val unit: String
    )

    data class Feedback(
        val count: Long,
        @FloatRange(from = 0.0, to = 5.0) val rating: Float
    )

    data class Info(
        val title: String,
        val value: String
    )

    class Converters {

        private val gson = Gson()

        @TypeConverter
        fun priceFromType(price: Price): String =
            gson.toJson(price)

        @TypeConverter
        fun priceToType(json: String): Price =
            gson.fromJson(json, Price::class.java)

        @TypeConverter
        fun feedbackFromType(feedback: Feedback): String =
            gson.toJson(feedback)

        @TypeConverter
        fun feedbackToType(json: String): Feedback =
            gson.fromJson(json, Feedback::class.java)

        @TypeConverter
        fun infoFromType(infoList: List<Info>): String =
            gson.toJson(infoList.toTypedArray())

        @TypeConverter
        fun infoToType(json: String): List<Info> =
            gson.fromJson(json, Array<Info>::class.java).toList()
    }
}

