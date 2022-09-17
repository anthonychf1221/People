package com.anthonychaufrias.people.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaisListResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("respuesta") val respuesta: List<Pais>
)