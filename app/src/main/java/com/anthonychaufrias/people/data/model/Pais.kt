package com.anthonychaufrias.people.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Pais(
    @Expose @SerializedName("idpais") val idPais: Int,
    @Expose @SerializedName("nombre") val nombre: String)