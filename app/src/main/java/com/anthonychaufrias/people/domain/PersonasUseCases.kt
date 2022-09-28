package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona

class GetPersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(busqueda: String): MutableList<Persona> = repository.getPersonaList(busqueda)
}

class SetPersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(persona: Persona): Persona = repository.setPersona(persona)
}

class UpdPersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(persona: Persona): Persona = repository.updatePersona(persona)
}

class DeletePersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(persona: Persona): Persona = repository.deletePersona(persona)
}