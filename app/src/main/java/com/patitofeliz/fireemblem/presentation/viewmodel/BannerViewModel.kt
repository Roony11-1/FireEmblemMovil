package com.patitofeliz.fireemblem.presentation.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.Banner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BannerViewModel : ViewModel()
{
    private val _banners = MutableLiveData<List<Banner>>()
    public val banners: LiveData<List<Banner>> get() = _banners

    fun guardarBanner(context: Context, banner: Banner, onFinish: () -> Unit)
    {
        RetroFitClient.bannerService.guardarBanner(banner)
            .enqueue(object : Callback<Banner>
            {
                override fun onResponse(call: Call<Banner>, response: Response<Banner>)
                {
                    Log.d("BANNER", "Response code: ${response.code()}")

                    if (response.isSuccessful)
                    {
                        val bannerRes = response.body()
                        Log.d("BANNER", "Body: $bannerRes")

                        if (bannerRes != null) {
                            Toast.makeText(context, "Has agregado el banner: " + bannerRes.nombre, Toast.LENGTH_SHORT).show()
                            onFinish()
                        }
                    }
                    else
                    {
                        Log.e("BANNER", "Error body: ${response.errorBody()?.string()}")
                        Toast.makeText(context, "Error del servidor", Toast.LENGTH_SHORT).show()
                        onFinish()
                    }
                }

                override fun onFailure(call: Call<Banner>, t: Throwable)
                {
                    Toast.makeText(context, "Error al intentar guardar el banner", Toast.LENGTH_SHORT).show()
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
}