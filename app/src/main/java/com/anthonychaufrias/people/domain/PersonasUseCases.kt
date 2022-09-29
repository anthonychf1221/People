package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona
import javax.inject.Inject

class SetPersonasUseCase @Inject constructor(private val repository: PersonaRepository) {

    fun isDataValidated(docID: String):Boolean {
        if(docID.length != Constantes.PERSON_DOCUMENT_LENGTH){
            return false
        }
        return true
    }

    suspend operator fun invoke(persona: Persona): Persona = repository.setPersona(persona)
}

class UpdPersonasUseCase @Inject constructor(private val repository: PersonaRepository){

    suspend operator fun invoke(persona: Persona): Persona = repository.updatePersona(persona)
}