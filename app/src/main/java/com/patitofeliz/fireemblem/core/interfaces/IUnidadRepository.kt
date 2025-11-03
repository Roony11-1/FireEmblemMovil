package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.Unidad

interface IUnidadRepository
{
    fun agregarUnidad(unidad: Unidad): Unit
    fun elimiarUnidadPorId(id: Int): Unit
    fun obtenerUnidades(): List<Unidad>
    fun obtenerPorId(id: Int): Unidad?
}