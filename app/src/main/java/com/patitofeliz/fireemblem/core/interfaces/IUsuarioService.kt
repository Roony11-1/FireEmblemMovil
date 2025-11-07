package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.api.LoginRequest
import com.patitofeliz.fireemblem.core.model.api.ResponseApi
import com.patitofeliz.fireemblem.core.model.api.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IUsuarioService
{
    @POST("usuarios/registro")
    fun registrarUsuario(@Body usuario: Usuario): Call<ResponseApi<Usuario>>
    @POST("usuarios/login")
    fun loginUsuario(@Body loginRequest: LoginRequest): Call<ResponseApi<Usuario>>
}
