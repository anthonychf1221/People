package com.anthonychaufrias.people.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaisListResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("results") val results: List<Pais>
)