package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.api.Banner
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IBannerService
{
    @GET("/api/movil/banner")
    fun obtenerBanners(): Call<List<Banner>>
    @GET("/api/movil/banner/activo/{activo}")
    fun obtenerBannersActivos(@Path("activo") activo: Boolean): Call<List<Banner>>
    @POST("/api/movil/banner")
    fun guardarBanner(@Body banner: Banner): Call<Banner>
    @PUT("/api/movil/banner/id/{id}")
    fun updatearBanner(@Path("id") id: Int, @Body banner: Banner): Call<Banner>
    @DELETE("/api/movil/banner/id/{id}")
    fun deleteByIdBanner(@Path("id") id: Int):Call<Void>
}