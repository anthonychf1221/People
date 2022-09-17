package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.data.model.Pais
import com.anthonychaufrias.people.data.model.PaisListResponse
import com.anthonychaufrias.people.data.service.Servicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaisViewModel : ViewModel(){
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constantes.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: Servicio = retrofit.create(Servicio::class.java)

    val liveDataCountriesList = MutableLiveData<List<Pais>>()
    var countriesList  = mutableListOf<Pais>()
    var countryNamesList = mutableListOf<String>()
    var selectedIndex: Int = 0

    fun getPaisesList(selectedId:Int? = 0){
        val call = service.getPaisesList()
        call.enqueue(object : Callback<PaisListResponse> {
            override fun onResponse(call: Call<PaisListResponse>, response: Response<PaisListResponse>) {
                response.body()?.respuesta?.let { list ->
                    countriesList.addAll(list)
                    //for(pais in list){
                    for (item in countriesList.indices) {
                        countryNamesList.add(countriesList[item].nombre)
                        if( countriesList[item].idPais == selectedId ){
                            selectedIndex = item
                        }
                    }
                    liveDataCountriesList.postValue(list)
                }
            }
            override fun onFailure(call: Call<PaisListResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

}