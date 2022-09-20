package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaListResponse
import com.anthonychaufrias.people.data.model.PersonaSaveResponse
import com.anthonychaufrias.people.data.service.IPersonaService
import com.anthonychaufrias.people.domain.GetPersonasUseCase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonaViewModel : ViewModel(){
    var liveDataPeopleList = MutableLiveData<MutableList<Persona>>()
    var peopleList  = mutableListOf<Persona>()
    var liveDataPeopleSave = MutableLiveData<Persona>()

    var getPersonasUseCase = GetPersonasUseCase()


    fun savePersona(persona: Persona){
        try{
            if( persona.idPersona == 0 ){
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
        /*val call = service.addPersona(persona)
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
        })*/
    }

    private fun updatePersona(persona: Persona){
        /*val call = service.updatePersona(persona)
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
        })*/
    }

    fun deletePersona(persona: Persona){
        /*val call = service.deletePersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                if( response.body()?.status.equals("Ok") ){
                    response.body()?.respuesta?.let { p ->
                        removeElement(persona)
                        //lstPersonas.postValue(lst)
                        liveDataPeopleList.value = peopleList
                    }
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
            }
        })*/
    }

    fun getListaPersonas(busqueda: String){
        viewModelScope.launch {
            val list: MutableList<Persona> = getPersonasUseCase(busqueda)
            peopleList.addAll(list)
            liveDataPeopleList.postValue(list)
        }
    }

    //@RequiresApi(Build.VERSION_CODES.N)
    fun removeElement(persona: Persona){
        try{
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
        }
        catch (e: Exception){
            print(e.message)
        }
    }
}