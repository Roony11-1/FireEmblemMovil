package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.api.PullResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface IGachaService
{
    @POST("/api/gacha/pull")
    fun pull(@Query("bannerId") bannerId: Int, @Query("usuarioId") usuarioId: Int?): Call<PullResponse>
}