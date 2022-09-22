package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona

class SetPersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(persona: Persona): Persona = repository.setPersona(persona)
}