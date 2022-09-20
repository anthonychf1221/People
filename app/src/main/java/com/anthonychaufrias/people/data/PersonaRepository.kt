package com.anthonychaufrias.people.data

import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.service.PersonaService

class PersonaRepository {
    private val api = PersonaService()

    suspend fun getPersonaList(busqueda: String): MutableList<Persona> {
        val response: MutableList<Persona> = api.getPersonaList(busqueda)
        return response
    }

}