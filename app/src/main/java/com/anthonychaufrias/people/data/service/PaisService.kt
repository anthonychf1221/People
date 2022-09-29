package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.Pais
import com.anthonychaufrias.people.data.model.PaisListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class PaisService @Inject constructor(private val service: IPaisService){

    suspend fun getPaisesList(): List<Pais> {
        return withContext(Dispatchers.IO){
            val response: Response<PaisListResponse> = service.getPaisesList()
            response.body()?.respuesta ?: emptyList()
        }
    }

}