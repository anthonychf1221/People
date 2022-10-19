package com.anthonychaufrias.people.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Persona(
    @Expose @SerializedName("idp") var idPersona: Int,
    @Expose @SerializedName("nombres") var nombres: String,
    @Expose @SerializedName("documento") var documento: String,
    @Expose @SerializedName("idpais") var idPais: Int,
    @Expose @SerializedName("nombpais") var pais: String): Serializable

