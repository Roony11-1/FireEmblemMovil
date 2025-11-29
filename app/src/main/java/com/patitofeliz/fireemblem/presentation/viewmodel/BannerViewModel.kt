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

    fun updateBanner(context: Context, id: Int, banner: Banner, onFinish: () -> Unit)
    {
        RetroFitClient.bannerService.updatearBanner(id, banner)
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
                            Toast.makeText(context, "Has actualizado el banner: " + bannerRes.nombre, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(context, "Error al intentar actualizar el banner", Toast.LENGTH_SHORT).show()
                    onFinish()
                }
            })
    }

    fun deleteBanner(context: Context, id: Int?, onFinish: () -> Unit)
    {
        if (id == null)
            return

        RetroFitClient.bannerService.deleteByIdBanner(id)
            .enqueue(object : Callback<Void>
            {
                override fun onResponse(call: Call<Void>, response: Response<Void>)
                {
                    Log.d("BANNER", "Response code: ${response.code()}")

                    if (response.isSuccessful)
                    {
                        Toast.makeText(context, "Banner con id: $id borrado", Toast.LENGTH_SHORT).show()
                        onFinish()
                    }
                    else
                    {
                        onFinish()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable)
                {
                    Toast.makeText(context, "Error al intentar borrar el banner", Toast.LENGTH_SHORT).show()
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