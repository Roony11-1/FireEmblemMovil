package com.patitofeliz.fireemblem.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.Banner
import com.patitofeliz.fireemblem.core.model.api.BannerItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BannerViewModel : ViewModel()
{
    private val _banners = MutableLiveData<List<Banner>>()
    public val banners: LiveData<List<Banner>> get() = _banners
    private val _items = MutableLiveData<List<BannerItem>>()
    public val items: LiveData<List<BannerItem>> get() = _items

    fun guardarBanner(context: Context, banner: Banner, onFinish: () -> Unit)
    {
        Log.d("BANNER", "Intentando guardar banner")
        Log.d("BANNER", "Banner enviado: $banner")

        val call = RetroFitClient.bannerService.guardarBanner(banner)

        // Debug: URL completa y headers
        Log.d("BANNER", "URL de request: ${call.request().url}")
        Log.d("BANNER", "Headers enviados: ${call.request().headers}")
        Log.d("BANNER", "Body enviado: ${banner}") // esto imprime el objeto, útil para ver items

        call.enqueue(object : Callback<Banner>
        {
            override fun onResponse(call: Call<Banner>, response: Response<Banner>) {
                Log.d("BANNER", "Response code: ${response.code()}")
                Log.d("BANNER", "Response message: ${response.message()}")
                Log.d("BANNER", "Response body: ${response.body()}")
                Log.d("BANNER", "Response errorBody: ${response.errorBody()?.string()}")

                when
                {
                    response.isSuccessful -> {
                        val bannerRes = response.body()
                        Log.i("BANNER", "Banner guardado exitosamente: $bannerRes")
                        Toast.makeText(context, "Has agregado el banner: ${bannerRes?.nombre}", Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 403 -> {
                        Log.e("BANNER", "403 Forbidden: verificar roles o token de autenticación")
                        Toast.makeText(context, "No tienes permisos para guardar este banner", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Log.e("BANNER", "Error desconocido al guardar banner")
                        Toast.makeText(context, "Error al guardar el banner: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
                onFinish()
            }

            override fun onFailure(call: Call<Banner>, t: Throwable)
            {
                Log.e("BANNER", "Fallo al hacer request guardarBanner", t)
                Toast.makeText(context, "Error de conexión al intentar guardar el banner", Toast.LENGTH_SHORT).show()
                onFinish()
            }
        })
    }

    fun updateBanner(context: Context, id: Int, banner: Banner, onFinish: () -> Unit)
    {
        Log.d("BANNER", "Intentando actualizar banner con id: $id")
        Log.d("BANNER", "Banner enviado: $banner")

        val call = RetroFitClient.bannerService.updatearBanner(id, banner)

        // Debug: URL completa y headers
        Log.d("BANNER", "URL de request: ${call.request().url}")
        Log.d("BANNER", "Headers enviados: ${call.request().headers}")

        call.enqueue(object : Callback<Banner>
        {
            override fun onResponse(call: Call<Banner>, response: Response<Banner>)
            {
                Log.d("BANNER", "Response code: ${response.code()}")
                Log.d("BANNER", "Response message: ${response.message()}")
                Log.d("BANNER", "Response body: ${response.body()}")
                Log.d("BANNER", "Response errorBody: ${response.errorBody()?.string()}")

                when
                {
                    response.isSuccessful -> {
                        val bannerRes = response.body()
                        Log.i("BANNER", "Banner actualizado exitosamente: $bannerRes")
                        Toast.makeText(context, "Has actualizado el banner: ${bannerRes?.nombre}", Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 403 -> {
                        Log.e("BANNER", "403 Forbidden: verificar roles o token de autenticación")
                        Toast.makeText(context, "No tienes permisos para actualizar este banner", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Log.e("BANNER", "Error desconocido al actualizar banner")
                        Toast.makeText(context, "Error al actualizar el banner: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
                onFinish()
            }

            override fun onFailure(call: Call<Banner>, t: Throwable) {
                Log.e("BANNER", "Fallo al hacer request updateBanner", t)
                Toast.makeText(context, "Error de conexión al intentar actualizar el banner", Toast.LENGTH_SHORT).show()
                onFinish()
            }
        })
    }

    fun deleteBanner(context: Context, id: Int?, onFinish: () -> Unit)
    {
        if (id == null)
        {
            Log.w("BANNER", "deleteBanner llamado con id nulo")
            return
        }

        Log.d("BANNER", "Intentando borrar banner con id: $id")

        val call = RetroFitClient.bannerService.deleteByIdBanner(id)

        // Debug URL completa y headers
        Log.d("BANNER", "URL de request: ${call.request().url}")
        Log.d("BANNER", "Headers enviados: ${call.request().headers}")

        call.enqueue(object : Callback<Void>
        {
            override fun onResponse(call: Call<Void>, response: Response<Void>)
            {
                Log.d("BANNER", "Response code: ${response.code()}")
                Log.d("BANNER", "Response message: ${response.message()}")
                Log.d("BANNER", "Response body: ${response.body()}")
                Log.d("BANNER", "Response errorBody: ${response.errorBody()?.string()}")

                when
                {
                    response.isSuccessful -> {
                        Toast.makeText(context, "Banner con id: $id borrado", Toast.LENGTH_SHORT).show()
                        Log.i("BANNER", "Banner borrado exitosamente")
                    }
                    response.code() == 403 -> {
                        Toast.makeText(context, "No tienes permisos para borrar este banner", Toast.LENGTH_LONG).show()
                        Log.e("BANNER", "403 Forbidden: verificar roles o token de autenticación")
                    }
                    else -> {
                        Toast.makeText(context, "Error al borrar el banner: ${response.code()}", Toast.LENGTH_SHORT).show()
                        Log.e("BANNER", "Error desconocido al borrar banner")
                    }
                }
                onFinish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable)
            {
                Log.e("BANNER", "Fallo al hacer request deleteBanner", t)
                Toast.makeText(context, "Error de conexión al intentar borrar el banner", Toast.LENGTH_SHORT).show()
                onFinish()
            }
        })
    }

    fun cargarBanners()
    {
        RetroFitClient.bannerService.obtenerBanners()
            .enqueue(object : Callback<List<Banner>>
            {
                override fun onResponse(call: Call<List<Banner>>, response: Response<List<Banner>>)
                {
                    if (response.isSuccessful)
                    {
                        _banners.value = response.body() ?: listOf()
                    }
                }

                override fun onFailure(call: Call<List<Banner>?>, t: Throwable)
                {
                    _banners.value = listOf()
                }
            })
    }

    fun cargarBannersActivos()
    {
        RetroFitClient.bannerService.obtenerBannersActivos(true)
            .enqueue(object : Callback<List<Banner>>
            {
                override fun onResponse(call: Call<List<Banner>>, response: Response<List<Banner>>)
                {
                    if (response.isSuccessful)
                    {
                        _banners.value = response.body() ?: listOf()
                    }
                }

                override fun onFailure(call: Call<List<Banner>?>, t: Throwable)
                {
                    _banners.value = listOf()
                }
            })
    }

    fun cargarBannerItemsActivos()
    {
        RetroFitClient.bannerItemService.getAllByActivoBannerItems(true)
            .enqueue(object : Callback<List<BannerItem>>
            {
                override fun onResponse(call: Call<List<BannerItem>>, response: Response<List<BannerItem>>)
                {
                    if (response.isSuccessful)
                    {
                        _items.value = response.body() ?: listOf()
                    }
                }

                override fun onFailure(call: Call<List<BannerItem>?>, t: Throwable)
                {
                    _items.value = listOf()
                }
            })
    }
}