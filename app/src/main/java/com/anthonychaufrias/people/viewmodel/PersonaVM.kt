package com.anthonychaufrias.people.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.util.function.Predicate

class PersonaVM : ViewModel(){
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constantes.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: Servicio = retrofit.create(Servicio::class.java)

    var ldLstPersonas = MutableLiveData<MutableList<Persona>>()
    var listPersonas  = mutableListOf<Persona>()
    var ldSavePersona = MutableLiveData<Persona>()
    /*init {
        lstPersonas.value = ArrayList()
    }*/

    fun addPersona(persona: Persona){
        val call = service.addPersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                if( response.body()?.status.equals("Ok") ){
                    //Log.e("ffffff===nuevooo====", "++"+listPersonas.size)
                    //https://stackoverflow.com/questions/47941537/notify-observer-when-item-is-added-to-list-of-livedata/49022687#49022687
                    response.body()?.respuesta?.let { persona ->
                        ldSavePersona.postValue(persona)

                        /*val lst = mutableListOf<Persona>()
                        //lst.addAll(lstPersonas.value)
                        lstPersonas.value?.let { lst.addAll(it) }
                        lst.add(persona)
                        lstPersonas.value = lst*/

                        //lstPersonas.value?.add(persona)
                        //lstPersonas.postValue(lstPersonas.value)
                        //lstPersonas.value = lstPersonas.value // notify observers

                        var lista : MutableList<Persona>  = mutableListOf()
                        lista.add(persona)
                        ldLstPersonas.postValue(lista)
                    }
                }
                else{
                    ldSavePersona.postValue(null)
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
                ldSavePersona.postValue(null)
            }
        })
    }

    fun updatePersona(persona: Persona){
        val call = service.updatePersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                if( response.body()?.status.equals("Ok") ){
                    response.body()?.respuesta?.let { persona ->
                        ldSavePersona.postValue(persona)

                        /*var lista : MutableList<Persona>  = mutableListOf()
                        lista.add(persona)
                        lstPersonas.postValue(lista)*/
                    }
                }
                else{
                    ldSavePersona.postValue(null)
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
                ldSavePersona.postValue(null)
            }
        })
    }

    fun deletePersona(persona: Persona){
        val call = service.deletePersona(persona)
        call.enqueue(object : Callback<PersonaSaveResponse>{
            override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                if( response.body()?.status.equals("Ok") ){
                    response.body()?.respuesta?.let { p ->
                        removeElement(persona)
                        //lstPersonas.postValue(lst)
                        ldLstPersonas.value = listPersonas
                        //lstPersonas.value = lstPersonas.value
                    }
                }
            }
            override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

    fun getPersonasList(busqueda: String){
        val call = service.getPersonaList(busqueda)
        call.enqueue(object : Callback<PersonaListResponse>{
            override fun onResponse(call: Call<PersonaListResponse>,response: Response<PersonaListResponse>) {
                response.body()?.respuesta?.let { list ->
                    listPersonas.addAll(list)
                    ldLstPersonas.postValue(list)
                }
            }
            override fun onFailure(call: Call<PersonaListResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

    //@RequiresApi(Build.VERSION_CODES.N)
    fun removeElement(persona: Persona){
        try{
            // Esta forma de eliminar es más general
            // Más información sobre bucles aquí:
            // https://www.programiz.com/kotlin-programming/for-loop
            //for(p in listPersonas){
            for (item in listPersonas.indices) {
                if( listPersonas[item].idp == persona.idp ){
                    listPersonas.removeAt(item)
                }
            }
            // Esta porción de códig también se puede usar
            // Pero solo funcionará correctamente si la versión del dispositivo
            // es mayor a API LEVEL 24 (Android 7.0)
            /*var filter = Predicate { p: Persona -> p == persona }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listPersonas.removeIf(filter)
            }*/
        }
        catch (e: Exception){

        }
    }
}