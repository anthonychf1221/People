package com.anthonychaufrias.people.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pais(
    @Expose @SerializedName("idpais") val idpais: Int,
    @Expose @SerializedName("nombre") val nombre: String)