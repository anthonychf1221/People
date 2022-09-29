package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona

class SetPersonasUseCase {
    private val repository = PersonaRepository()

    fun isDataValidated(docID: String):Boolean {
        if(docID.length != Constantes.PERSON_DOCUMENT_LENGTH){
            return false
        }
        return true
    }

    suspend operator fun invoke(persona: Persona): Persona = repository.setPersona(persona)
}

class UpdPersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(persona: Persona): Persona = repository.updatePersona(persona)
}

/*class GetPersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(busqueda: String): MutableList<Persona> = repository.getPersonaList(busqueda)
}
class DeletePersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(persona: Persona): Persona = repository.deletePersona(persona)
}*/