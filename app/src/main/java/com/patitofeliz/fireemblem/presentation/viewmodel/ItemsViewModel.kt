package com.patitofeliz.fireemblem.presentation.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.BannerItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemsViewModel : ViewModel()
{
    private val _items = MutableLiveData<List<BannerItem>>()
    public val items: LiveData<List<BannerItem>> get() = _items
    private val _clases = MutableLiveData<List<String>>()
    public val clases: LiveData<List<String>> get() = _clases

    init
    {
        _clases.value = Manager.claseFactory.clasesRegistradas()
        cargarBannerItems()
    }

    fun cargarBannerItems()
    {
        RetroFitClient.bannerItemService.getAllBannerItems()
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

    fun updateBannerItem(context: Context, id: Int?, bannerItem: BannerItem, onFinish: () -> Unit)
    {
        RetroFitClient.bannerItemService.updateBannerItem(id, bannerItem)
            .enqueue(object : Callback<BannerItem>
            {
                override fun onResponse(call: Call<BannerItem>, response: Response<BannerItem>)
                {
                    if (response.isSuccessful)
                    {
                        Toast.makeText(context, "Has actualizado el item: ${bannerItem.nombre}", Toast.LENGTH_SHORT).show()
                        onFinish()
                    }
                }

                override fun onFailure(call: Call<BannerItem>, t: Throwable)
                {
                    Toast.makeText(context, "Error de conexión al intentar actualizar el item", Toast.LENGTH_SHORT).show()
                    onFinish()
                }
            })
    }

    fun saveBannerItem(context: Context, bannerItem: BannerItem, onFinish: () -> Unit)
    {
        RetroFitClient.bannerItemService.saveBannerItem(bannerItem)
            .enqueue(object : Callback<BannerItem>
            {
                override fun onResponse(call: Call<BannerItem>, response: Response<BannerItem>)
                {
                    if (response.isSuccessful)
                    {
                        Toast.makeText(context, "Has guardado el item: ${bannerItem.nombre}", Toast.LENGTH_SHORT).show()
                        onFinish()
                    }
                }

                override fun onFailure(call: Call<BannerItem>, t: Throwable)
                {
                    Toast.makeText(context, "Error de conexión al intentar actualizar el item", Toast.LENGTH_SHORT).show()
                    onFinish()
                }
            })
    }
}