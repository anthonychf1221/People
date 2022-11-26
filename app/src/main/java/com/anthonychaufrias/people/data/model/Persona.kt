package com.anthonychaufrias.people.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Persona(
    @Expose @SerializedName("idPersona") var idPersona: Int,
    @Expose @SerializedName("nombres") var nombres: String,
    @Expose @SerializedName("documento") var documento: String,
    @Expose @SerializedName("idPais") var idPais: Int?,
    @Expose @SerializedName("pais") var pais: String?): Parcelable

