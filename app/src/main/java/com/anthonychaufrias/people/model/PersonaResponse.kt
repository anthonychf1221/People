package com.anthonychaufrias.people.model

import com.anthonychaufrias.people.viewmodel.PersonaViewModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersonaListResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("respuesta") val respuesta: MutableList<Persona>
)

data class PersonaSaveResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("respuesta") val respuesta: Persona
)

data class PersonaSaveResult(
    var validation: MutableList<PersonaViewModel.ValidationResult>,
    var persona: Persona?
)
