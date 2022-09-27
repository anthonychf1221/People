package com.anthonychaufrias.people.viewmodel

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

class PersonaViewModel : ViewModel(){
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constantes.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: Servicio = retrofit.create(Servicio::class.java)

    var liveDataPeopleList = MutableLiveData<MutableList<Persona>>()
    var peopleList  = mutableListOf<Persona>()
    var liveDataPeopleSave = MutableLiveData<Persona>()

    fun savePersona(persona: Persona, action: Int){
        try{
            if( action == Constantes.INSERT ){
                addPersona(persona)
            }
            else{
                updatePersona(persona)
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    private fun addPersona(persona: Persona){
        val call = service.addPersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                if( response.body()?.status.equals("Ok") ){
                    response.body()?.respuesta?.let { persona ->
                        liveDataPeopleSave.postValue(persona)
                        val lista : MutableList<Persona>  = mutableListOf()
                        lista.add(persona)
                        liveDataPeopleList.postValue(lista)
                    }
                }
                else{
                    liveDataPeopleSave.postValue(null)
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
                liveDataPeopleSave.postValue(null)
            }
        })
    }

    private fun updatePersona(persona: Persona){
        val call = service.updatePersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                if( response.body()?.status.equals("Ok") ){
                    response.body()?.respuesta?.let { persona ->
                        liveDataPeopleSave.postValue(persona)
                    }
                }
                else{
                    liveDataPeopleSave.postValue(null)
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
                liveDataPeopleSave.postValue(null)
            }
        })
    }

    fun deletePersona(persona: Persona){
        try{
            val call = service.deletePersona(persona)
            call.enqueue(object : Callback<PersonaSaveResponse>{
                override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                    if( response.body()?.status.equals("Ok") ){
                        response.body()?.respuesta?.let { p ->
                            removePersonaFromList(persona)
                        }
                    }
                }
                override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    fun loadListaPersonas(busqueda: String){
        val call = service.getPersonaList(busqueda)
        call.enqueue(object : Callback<PersonaListResponse>{
            override fun onResponse(call: Call<PersonaListResponse>,response: Response<PersonaListResponse>) {
                response.body()?.respuesta?.let { list ->
                    peopleList.addAll(list)
                    liveDataPeopleList.postValue(list)
                }
            }
            override fun onFailure(call: Call<PersonaListResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

    fun refreshList(persona: Persona, action: Int){
        if( action == Constantes.INSERT ){
            addPersonaToList(persona)
        }
        else{
            updatePersonaFromList(persona)
        }
    }

    private fun addPersonaToList(persona: Persona){
        peopleList.add(persona)
        liveDataPeopleList.value = peopleList
    }
    private fun updatePersonaFromList(persona: Persona){
        for (item in peopleList.indices) {
            if( peopleList[item].idPersona == persona.idPersona ){
                peopleList[item] = persona
            }
        }
        liveDataPeopleList.value = peopleList
    }

    //@RequiresApi(Build.VERSION_CODES.N)
    private fun removePersonaFromList(persona: Persona){
        // Esta forma de eliminar es más general
        // Más información sobre bucles aquí:
        // https://www.programiz.com/kotlin-programming/for-loop
        for (item in peopleList.indices) {
            if( peopleList[item].idPersona == persona.idPersona ){
                peopleList.removeAt(item)
            }
        }
        // Esta porción de código también se puede usar
        // Pero solo funcionará correctamente si la versión del dispositivo
        // es mayor a API LEVEL 24 (Android 7.0)
        /*var filter = Predicate { p: Persona -> p == persona }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listPersonas.removeIf(filter)
        }*/
        liveDataPeopleList.value = peopleList
    }
}