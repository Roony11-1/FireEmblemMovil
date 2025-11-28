package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.api.PullResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IGachaService
{
    @POST("/api/movil/gacha/banner/{bId}/usuario/{uId}")
    fun pull(@Path("bId") bId: Int, @Path("uId") uId: Int?): Call<PullResponse>
}