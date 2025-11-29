package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.api.Banner
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IBannerService
{
    @GET("movil/banner")
    fun obtenerBanners(): Call<List<Banner>>
    @GET("movil/banner/activo/{activo}")
    fun obtenerBannersActivos(@Path("activo") activo: Boolean): Call<List<Banner>>
    @POST("movil/banner")
    fun guardarBanner(@Body banner: Banner): Call<Banner>
}