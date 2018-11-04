package com.demo.ike.doordashproject.data

import com.google.gson.annotations.SerializedName

class Restaurant {

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("status")
    val status: String = ""

    @SerializedName("name")
    val name: String = ""

    @SerializedName("cover_img_url")
    val coverImgUrl: String? = null

    @SerializedName("description")
    val description: String = ""
}

class RestaurantDetail {

    @SerializedName("phone_number")
    val phoneNumber: String = ""

    @SerializedName("yelp_review_count")
    val reviewCount: Int = 0

    @SerializedName("delivery_fee")
    val deliveryFee: Int = 0

    @SerializedName("average_rating")
    val rating: Float = 0f

    @SerializedName("name")
    val name: String = ""

    @SerializedName("offers_delivery")
    val deliveryAvailable: Boolean = false

    @SerializedName("cover_img_url")
    val coverImgUrl: String? = null

    @SerializedName("address")
    var address: Address =
        Address()

    fun getPrintableAddress(): String{
        return address.printableAddress
    }
}

class Address {
    @SerializedName("printable_address")
    val printableAddress: String = ""
}