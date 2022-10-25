package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaSaveResult
import com.anthonychaufrias.people.data.model.ValidationResult
import javax.inject.Inject

object ValidatePersonaUseCase {

    fun getFormValidation(nombre: String, docID: String):MutableList<ValidationResult> {
        val validations = mutableListOf<ValidationResult>()
        if( nombre.isEmpty() ){
            validations.add(ValidationResult.INVALID_NAME)
        }
        if( docID.length != Constantes.PERSON_DOCUMENT_LENGTH ){
            validations.add(ValidationResult.INVALID_DOCUMENT_ID)
        }
        if(validations.size == 0){
            validations.add(ValidationResult.OK)
        }
        return validations
    }
}


class SetPersonasUseCase @Inject constructor(private val repository: PersonaRepository) {

    suspend operator fun invoke(persona: Persona): PersonaSaveResult {
        val validations = ValidatePersonaUseCase.getFormValidation(persona.nombres, persona.documento)
        if( validations[0] == ValidationResult.OK ){
            val response = repository.setPersona(persona)
            if( response?.status.equals("Ok") ){
                return PersonaSaveResult.OK(response?.persona)
            }
            else{
                return PersonaSaveResult.OperationFailed(response?.message, ValidationResult.INVALID_DOCUMENT_ID)
            }
        }
        else{
            return PersonaSaveResult.InvalidInputs(validations)
        }
    }
}

class UpdPersonasUseCase @Inject constructor(private val repository: PersonaRepository){

    suspend operator fun invoke(persona: Persona): PersonaSaveResult {
        val validations = ValidatePersonaUseCase.getFormValidation(persona.nombres, persona.documento)
        if( validations[0] == ValidationResult.OK ){
            val response = repository.updatePersona(persona)
            return PersonaSaveResult.OK(response?.persona)
        }
        else{
            return PersonaSaveResult.InvalidInputs(validations)
        }
    }
}