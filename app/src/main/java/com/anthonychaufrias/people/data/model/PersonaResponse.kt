package com.anthonychaufrias.people.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersonaListResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("respuesta") val respuesta: MutableList<Persona>
)

data class PersonaSaveResponse(
    @Expose @SerializedName("status") val status: String,
    @Expose @SerializedName("respuesta") val persona: Persona,
    @Expose @SerializedName("message") val message: String
)

sealed class PersonaSaveResult {
    class OK(val persona: Persona?): PersonaSaveResult()
    class InvalidInputs(val errors: List<ValidationResult>): PersonaSaveResult()
    class OperationFailed(val message: String?, val type: ValidationResult) : PersonaSaveResult()
}
enum class ValidationResult{
    OK, INVALID_NAME, INVALID_DOCUMENT_ID, FAILURE
}