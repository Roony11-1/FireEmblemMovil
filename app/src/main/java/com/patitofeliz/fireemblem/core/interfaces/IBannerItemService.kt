package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.api.BannerItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IBannerItemService
{
    @GET("/api/movil/banneritem")
    fun getAllBannerItems(): Call<List<BannerItem>>
    @GET("/api/movil/banneritem/activo/{activo}")
    fun getAllByActivoBannerItems(@Path("activo") activo: Boolean): Call<List<BannerItem>>
    @POST
    fun saveBannerItem(@Body bannerItem: BannerItem): Call<BannerItem>
    @PUT("/api/movil/banneritem/id/{id}")
    fun updateBannerItem(@Path("id") id: Int?, @Body bannerItem: BannerItem): Call<BannerItem>
}