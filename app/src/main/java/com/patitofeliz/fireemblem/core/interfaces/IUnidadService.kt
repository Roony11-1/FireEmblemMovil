package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.api.ResponseApi
import com.patitofeliz.fireemblem.core.model.api.UnidadApi
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IUnidadService
{
    @GET("unidades/propietario/{idPropietario}")
    fun findMyUnits(@Path("idPropietario") idPropietario: Int): Call<List<UnidadApi>>
    @PUT("unidades/update")
    fun updateUnit(@Body unidadApi: UnidadApi): Call<ResponseApi<UnidadApi>>
    @POST("unidades")
    fun saveUnit(@Body unidadApi: UnidadApi): Call<ResponseApi<UnidadApi>>
    @GET("unidades/propietario/not/{idPropietario}")
    fun findOtherUnits(@Path("idPropietario") idPropietario: Int):Call<List<UnidadApi>>
}