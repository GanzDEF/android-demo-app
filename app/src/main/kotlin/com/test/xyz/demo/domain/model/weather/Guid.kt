package com.test.xyz.demo.domain.model.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Guid {

    @SerializedName("isPermaLink")
    @Expose
    var isPermaLink: String? = null

}
