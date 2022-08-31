package com.anthonychaufrias.people.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Persona(
    @Expose @SerializedName("idp") val idp: Int,
    @Expose @SerializedName("nombres") val nombres: String,
    @Expose @SerializedName("documento") val documento: String,
    @Expose @SerializedName("idpais") val idpais: Int?): Serializable

