package com.patitofeliz.fireemblem.core.model.api

data class Banner(var id: Int?,
    var activo: Boolean?,
    var descripcion: String?,
    var nombre: String?,
    var items: List<BannerItem>?)
{
    override fun toString(): String
    {
        return nombre ?: ""
    }
}
