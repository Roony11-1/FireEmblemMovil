package com.patitofeliz.fireemblem.core.interfaces

import android.content.Context
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.model.api.UnidadApi

interface IUnidadController
{
    fun obtenerUnidades(): List<Unidad>
    fun agregarUnidad(context: Context, nombre: String, tipoClase: String?)
    fun agregarUnidadDB(unidad: Unidad)
    fun eliminarUnidadid(id: Int): Boolean
    fun obtenerUnidadId(id: Int): Unidad?
    fun obtenerUnidadNombre(nombre: String): Unidad?
    fun limpiar(): Unit

    fun actualizarUnidad(unidad: Unidad): Boolean

    fun agregarUnidadApi(unidadApi: UnidadApi)
    fun updateWithApi(context: Context, unidad: Unidad)
    fun getEnemyFromApi(idJugador: Int, onSuccess: (Unidad) -> Unit, onError: (Throwable) -> Unit)
}