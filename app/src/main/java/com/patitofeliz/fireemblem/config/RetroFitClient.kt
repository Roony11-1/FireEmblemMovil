package com.patitofeliz.fireemblem.config

import com.patitofeliz.fireemblem.core.interfaces.IUnidadService
import com.patitofeliz.fireemblem.core.interfaces.IUsuarioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitClient
{
    private const val BASE_URL = "http://192.168.1.8:8001/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val usuarioService: IUsuarioService by lazy {
        retrofit.create(IUsuarioService::class.java)
    }

    val unidadService: IUnidadService by lazy {
        retrofit.create(IUnidadService::class.java)
    }
}