package com.demo.ike.doordashproject

import com.google.gson.annotations.SerializedName

class Restaurant {

    @SerializedName("id")
    val id: Int = 0

    @SerializedName("status")
    val status: String = ""

    @SerializedName("name")
    val name: String = ""

    @SerializedName("cover_img_url")
    val coverImgUrl: String = ""

    @SerializedName("description")
    val description: String = ""
}