package com.anthonychaufrias.people.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.config.Constantes
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.model.PersonaListResponse
import com.anthonychaufrias.people.model.PersonaSaveResponse
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

    var lstPersonas = MutableLiveData<MutableList<Persona>>()
    var savePersona = MutableLiveData<Persona>()
    /*init {
        lstPersonas.value = ArrayList()
    }*/

    fun addPersona(persona: Persona){
        //fun addPersona(nomb: String, doc: String, idps: Int?){
        //val call = service.addPersona(nomb, doc, idps)
        val call = service.addPersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                Log.e("status", "statusstatus == "+response.body()?.toString())
                if( response.body()?.status.equals("Ok") ){
                    Log.e("onResponse", "ififififif == ")
                    // satisfactoria
                    //https://stackoverflow.com/questions/47941537/notify-observer-when-item-is-added-to-list-of-livedata/49022687#49022687
                    response.body()?.respuesta?.let { persona ->
                        savePersona.postValue(persona)

                        /*val lst = mutableListOf<Persona>()
                        //lst.addAll(lstPersonas.value)
                        lstPersonas.value?.let { lst.addAll(it) }
                        lst.add(persona)
                        lstPersonas.value = lst*/

                        //lstPersonas.value?.add(persona)
                        //lstPersonas.postValue(lstPersonas.value)
                        //lstPersonas.value = lstPersonas.value // notify observers

                        //Log.e("ultimoooo", "nuevoo-lis == "+lstPersonas.value?.last().toString())
                        var lista : MutableList<Persona>  = mutableListOf()
                        lista.add(persona)
                        lstPersonas.postValue(lista)
                        //Log.e("ultimoooo", "nuevoo-lis == "+lstPersonas.value?.last().toString())
                    }
                }
                else{
                    savePersona.postValue(null)
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
                savePersona.postValue(null)
            }
        })
    }

    fun updatePersona(persona: Persona){
        val call = service.updatePersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                Log.e("status", "statusstatus == "+response.body()?.toString())
                if( response.body()?.status.equals("Ok") ){
                    Log.e("onResponse", "ififififif == ")
                    response.body()?.respuesta?.let { persona ->
                        savePersona.postValue(persona)

                        /*var lista : MutableList<Persona>  = mutableListOf()
                        lista.add(persona)
                        lstPersonas.postValue(lista)*/
                    }
                }
                else{
                    savePersona.postValue(null)
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
                savePersona.postValue(null)
            }
        })
    }

    fun deletePersona(persona: Persona){
        val call = service.deletePersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                Log.e("status", "statusstatus == "+response.body()?.toString())
                if( response.body()?.status.equals("Ok") ){
                    Log.e("onResponse", "ififififif == ")
                    response.body()?.respuesta?.let { persona ->
                        lstPersonas = MutableLiveData<MutableList<Persona>>()
                        val lst = mutableListOf<Persona>()
                        lst.add(persona)
                        lstPersonas.postValue(lst)
                    }
                }
                else{
                    //savePersona.postValue(null)
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
                //savePersona.postValue(null)
            }
        })
    }

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