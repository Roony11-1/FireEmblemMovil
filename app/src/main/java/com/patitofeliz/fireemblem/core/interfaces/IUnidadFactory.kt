package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.Unidad

interface IUnidadFactory
{
    fun crearUnidad(id: Int, nombre: String, tipo: String): Unidad
}