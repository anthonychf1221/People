package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaListResponse
import com.anthonychaufrias.people.data.model.PersonaSaveResponse
import com.anthonychaufrias.people.data.service.IPersonaService
import com.anthonychaufrias.people.domain.DltPersonasUseCase
import com.anthonychaufrias.people.domain.GetPersonasUseCase
import com.anthonychaufrias.people.domain.SetPersonasUseCase
import com.anthonychaufrias.people.domain.UpdPersonasUseCase
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
    var setPersonasUseCase = SetPersonasUseCase()
    var updPersonasUseCase = UpdPersonasUseCase()
    var dltPersonasUseCase = DltPersonasUseCase()


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

    fun getListaPersonas(busqueda: String){
        viewModelScope.launch {
            val list: MutableList<Persona> = getPersonasUseCase(busqueda)
            peopleList.addAll(list)
            liveDataPeopleList.postValue(list)
        }
    }

    private fun addPersona(persona: Persona){
        viewModelScope.launch {
            val newPersona: Persona = setPersonasUseCase(persona)
            liveDataPeopleSave.postValue(newPersona)
        }
    }
    private fun updatePersona(persona: Persona){
        viewModelScope.launch {
            val updatedPersona: Persona = updPersonasUseCase(persona)
            liveDataPeopleSave.postValue(updatedPersona)
        }
    }

    fun deletePersona(persona: Persona){
        viewModelScope.launch {
            val deletedPersona: Persona = dltPersonasUseCase(persona)
            removeElement(persona)
            liveDataPeopleList.value = peopleList
        }
    }

    /*fun deletePersona(persona: Persona){
        val call = service.deletePersona(persona)
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
        })
    }*/

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