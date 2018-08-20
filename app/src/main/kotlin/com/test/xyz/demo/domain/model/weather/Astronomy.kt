package com.test.xyz.demo.domain.model.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Astronomy {
    @SerializedName("sunrise")

    @Expose
    var sunrise: String? = null

    @SerializedName("sunset")
    @Expose
    var sunset: String? = null
}
