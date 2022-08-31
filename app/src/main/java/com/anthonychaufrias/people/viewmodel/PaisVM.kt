package com.anthonychaufrias.people.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.config.Constantes
import com.anthonychaufrias.people.model.Pais
import com.anthonychaufrias.people.model.PaisListResponse
import com.anthonychaufrias.people.service.Servicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaisVM : ViewModel(){
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constantes.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: Servicio = retrofit.create(Servicio::class.java)

    val lstPaises = MutableLiveData<List<Pais>>()

    fun getPaisesList(){
        val call = service.getPaisesList()
        call.enqueue(object : Callback<PaisListResponse> {
            override fun onResponse(call: Call<PaisListResponse>, response: Response<PaisListResponse>) {
                response.body()?.respuesta?.let { list ->
                    lstPaises.postValue(list)
                }
            }
            override fun onFailure(call: Call<PaisListResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

}