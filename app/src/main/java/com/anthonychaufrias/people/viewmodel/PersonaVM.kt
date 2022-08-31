package com.anthonychaufrias.people.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.config.Constantes
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.model.PersonaListResponse
import com.anthonychaufrias.people.service.Servicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonaVM : ViewModel(){
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constantes.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: Servicio = retrofit.create(Servicio::class.java)

    val lstPersonas = MutableLiveData<MutableList<Persona>>()
    /*init {
        lstPersonas.value = ArrayList()
    }*/

    fun getPersonasList(){
        val call = service.getPersonaList()
        call.enqueue(object : Callback<PersonaListResponse>{
            override fun onResponse(call: Call<PersonaListResponse>,response: Response<PersonaListResponse>) {
                response.body()?.respuesta?.let { list ->
                    lstPersonas.postValue(list)
                }
            }
            override fun onFailure(call: Call<PersonaListResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

}