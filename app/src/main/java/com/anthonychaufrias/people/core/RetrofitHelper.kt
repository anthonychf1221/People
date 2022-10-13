package com.anthonychaufrias.people.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://ticsolu.com/mvvm/api/v.1.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
