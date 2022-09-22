package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.data.model.Pais
import com.anthonychaufrias.people.data.model.PaisListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaisService {
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service = retrofit.create(IPaisService::class.java)

    suspend fun getPaisesList(): List<Pais> {
        return withContext(Dispatchers.IO){
            val response: Response<PaisListResponse> = service.getPaisesList()
            response.body()?.respuesta ?: emptyList()
        }
    }

}