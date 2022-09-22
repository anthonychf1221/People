package com.anthonychaufrias.people.data

import com.anthonychaufrias.people.data.model.Pais
import com.anthonychaufrias.people.data.service.PaisService

class PaisRepository {
    private val api = PaisService()

    suspend fun getPaisesList(): List<Pais> {
        val response: List<Pais> = api.getPaisesList()
        return response
    }
}