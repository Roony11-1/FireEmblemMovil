package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.Crecimientos
import com.patitofeliz.fireemblem.core.model.Unidad

interface IUnidadFactory
{
    fun crearUnidad(id: Int?, nombre: String, tipo: String?, nivel: Int=1, experiencia: Int=0, crecimiento: Crecimientos?=null): Unidad
}