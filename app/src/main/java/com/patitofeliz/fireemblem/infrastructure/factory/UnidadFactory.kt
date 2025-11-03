package com.patitofeliz.fireemblem.infrastructure.factory

import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.interfaces.IUnidadFactory
import com.patitofeliz.fireemblem.core.model.Unidad

class UnidadFactory(private val claseFactory: IClaseFactory) : IUnidadFactory
{
    override fun crearUnidad(id: Int, nombre: String, tipo: String): Unidad
    {
        val clase = claseFactory.crearClase(tipo)
        return Unidad(id = id, nombre = nombre, clase = clase)
    }
}