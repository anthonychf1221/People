package com.anthonychaufrias.people.di

import com.anthonychaufrias.people.data.service.IPaisService
import com.anthonychaufrias.people.data.service.IPersonaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://ticsolu.com/mvvm/api/v.1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providePersonaAPI(retrofit: Retrofit): IPersonaService {
        return retrofit.create(IPersonaService::class.java)
    }

    @Singleton
    @Provides
    fun providePaisAPI(retrofit: Retrofit): IPaisService {
        return retrofit.create(IPaisService::class.java)
    }
}