package com.anthonychaufrias.people.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.model.Pais
import com.anthonychaufrias.people.model.PaisListResponse
import com.anthonychaufrias.people.service.Servicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaisViewModel : ViewModel(){
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service: Servicio = retrofit.create(Servicio::class.java)

    var liveDataCountriesList = MutableLiveData<List<Pais>>()
    var countriesList  = mutableListOf<Pais>()
    var countryNamesList = mutableListOf<String>()
    var selectedIndex: Int = 0

    init {
        liveDataCountriesList = MutableLiveData<List<Pais>>()
    }

    fun loadPaisesList(selectedId:Int? = 0){
        val call = service.getPaisesList()
        call.enqueue(object : Callback<PaisListResponse> {
            override fun onResponse(call: Call<PaisListResponse>, response: Response<PaisListResponse>) {
                response.body()?.results?.let { list ->
                    countriesList.clear()
                    countriesList.addAll(list)
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