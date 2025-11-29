package com.patitofeliz.fireemblem.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.Banner
import com.patitofeliz.fireemblem.core.model.api.BannerItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemsViewModel : ViewModel()
{
    private val _items = MutableLiveData<List<BannerItem>>()
    public val items: LiveData<List<BannerItem>> get() = _items

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
}