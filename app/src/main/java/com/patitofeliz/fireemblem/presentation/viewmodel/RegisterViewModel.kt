package com.patitofeliz.fireemblem.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.LoginRequest
import com.patitofeliz.fireemblem.core.model.api.ResponseApi
import com.patitofeliz.fireemblem.core.model.api.Usuario
import com.patitofeliz.fireemblem.presentation.activity.PrincipalActivity
import retrofit2.Call
import retrofit2.Callback

class RegisterViewModel : ViewModel()
{
    public fun registrar(context: Context, usuario: Usuario)
    {
        RetroFitClient.usuarioService.registrarUsuario(usuario)
            .enqueue(object : Callback<ResponseApi<Usuario>> {
                override fun onResponse(
                    call: Call<ResponseApi<Usuario>>,
                    response: retrofit2.Response<ResponseApi<Usuario>>
                ) {
                    if (response.isSuccessful)
                    {
                        Toast.makeText(context,
                                "Registrado correctamente", Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(context, "Error al intentar registrar", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseApi<Usuario>>, t: Throwable) {
                    Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}