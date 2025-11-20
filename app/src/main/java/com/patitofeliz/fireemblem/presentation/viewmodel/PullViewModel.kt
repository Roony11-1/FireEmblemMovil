package com.patitofeliz.fireemblem.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.PullResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullViewModel
{
    fun pull(context: Context, bannerId:Int)
    {
        RetroFitClient.gachaService.pull(bannerId, Manager.loginService.idLogin)
            .enqueue(object : Callback<PullResponse>
            {

                override fun onResponse(call: Call<PullResponse>, response: Response<PullResponse>)
                {
                    val body = response.body()

                    if (!response.isSuccessful || body == null)
                    {
                        Log.e("GACHA", "Respuesta inválida o nula: ${response.code()}")
                        return
                    }

                    Log.d("GACHA", "→ ${body.nombre}")

                    if (body.tipo == "personaje")
                    {
                        val nombre = body.nombre ?: return
                        val clase = body.clase ?: return

                        if (Manager.unidadController.obtenerUnidadNombre(nombre) != null)
                        {
                            Toast.makeText(context, "Ya tienes a $nombre", Toast.LENGTH_SHORT).show()
                            return
                        }

                        Manager.unidadController.agregarUnidad(context, nombre, clase)
                    }
                }

                override fun onFailure(call: Call<PullResponse>, t: Throwable)
                {
                    Log.e("GACHA", "Error pull", t)
                }
            })
    }
}