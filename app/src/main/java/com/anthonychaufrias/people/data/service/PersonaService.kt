package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PersonaService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getPersonaList(busqueda: String): MutableList<Persona> {
        return withContext(Dispatchers.IO){
            val service = retrofit.create(IPersonaService::class.java)
            val response: Response<PersonaListResponse> = service.getPersonaList(busqueda)
            response.body()?.respuesta ?: mutableListOf<Persona>()
        }
    }

}