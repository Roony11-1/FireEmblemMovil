package com.patitofeliz.fireemblem.core.model.api

data class Banner(val id: Int?,
    val activo: Boolean,
    val descripcion: String,
    val nombre: String)
{
    override fun toString(): String
    {
        return "Banner: $nombre - Descripción: $descripcion"
    }
}
