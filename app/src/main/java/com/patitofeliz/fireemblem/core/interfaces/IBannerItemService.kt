package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.api.BannerItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IBannerItemService
{
    @GET("/api/movil/banneritem")
    fun getAllBannerItems(): Call<List<BannerItem>>
    @GET("/api/movil/banneritem/activo/{activo}")
    fun getAllByActivoBannerItems(@Path("activo") activo: Boolean): Call<List<BannerItem>>
}