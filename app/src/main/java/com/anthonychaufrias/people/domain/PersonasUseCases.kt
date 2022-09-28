package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona

class GetPersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(busqueda: String): MutableList<Persona> = repository.getPersonaList(busqueda)
}

class SetPersonasUseCasee {
    private val repository = PersonaRepository()

    suspend operator fun invoke(persona: Persona): Persona = repository.setPersona(persona)
}

class UpdPersonasUseCasee {
    private val repository = PersonaRepository()

    suspend operator fun invoke(persona: Persona): Persona = repository.updatePersona(persona)
}