package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaSaveResult
import javax.inject.Inject

enum class ValidationResult{
    OK, NAME_EMPTY, DOCUMENT_ID_INVALID
}

object ValidatePersonaUseCase {

    fun getFormValidation(nombre: String, docID: String):MutableList<ValidationResult> {
        val validations = mutableListOf<ValidationResult>()
        if( nombre.isEmpty() ){
            validations.add(ValidationResult.NAME_EMPTY)
        }
        if( docID.length != Constantes.PERSON_DOCUMENT_LENGTH ){
            validations.add(ValidationResult.DOCUMENT_ID_INVALID)
        }
        if(validations.size == 0){
            validations.add(ValidationResult.OK)
        }
        return validations
    }
}


class SetPersonasUseCase @Inject constructor(private val repository: PersonaRepository) {

    suspend operator fun invoke(persona: Persona): PersonaSaveResult {
        val validation = ValidatePersonaUseCase.getFormValidation(persona.nombres, persona.documento)
        if( validation[0] == ValidationResult.OK ){
            val person = repository.setPersona(persona)
            return PersonaSaveResult(validation, person)
        }
        else{
            return PersonaSaveResult(validation, null)
        }
    }
}

class UpdPersonasUseCase @Inject constructor(private val repository: PersonaRepository){

    suspend operator fun invoke(persona: Persona): PersonaSaveResult {
        val validation = ValidatePersonaUseCase.getFormValidation(persona.nombres, persona.documento)
        if( validation[0] == ValidationResult.OK ){
            val person = repository.updatePersona(persona)
            return PersonaSaveResult(validation, person)
        }
        else{
            return PersonaSaveResult(validation, null)
        }
    }
}