package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.Unidad

interface IUnidadController
{
    fun obtenerUnidades(): List<Unidad>
    fun agregarUnidad(nombre: String, tipoClase: String?): String
    fun agregarUnidadDB(unidad: Unidad)
    fun eliminarUnidadid(id: Int): Boolean
    fun obtenerUnidadId(id: Int): Unidad?
    fun obtenerUnidadNombre(nombre: String): Unidad?
    fun limpiar(): Unit
}