package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona

class GetPersonasUseCase {
    private val repository = PersonaRepository()

    suspend operator fun invoke(busqueda: String): MutableList<Persona> = repository.getPersonaList(busqueda)
}