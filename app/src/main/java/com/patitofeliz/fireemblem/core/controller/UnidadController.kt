package com.patitofeliz.fireemblem.core.controller

import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.core.interfaces.IUnidadController
import com.patitofeliz.fireemblem.core.model.Unidad

class UnidadController : IUnidadController
{
    private val unidades = mutableListOf<Unidad>()
    private var nextId = 0

    override fun agregarUnidad(nombre: String, tipoClase: String?): String
    {
        if (nombre.isEmpty())
            return "No puedes registrar una Unidad sin nombre"
        
        val unidadExistente: Unidad? = this.obtenerUnidadNombre(nombre)

        if (unidadExistente != null)
            return "Ya existe una unidad con el nombre ${nombre}"

        val unidad = Manager.unidadFactory.crearUnidad(nextId++, nombre, tipoClase)

        unidades.add(unidad)

        return "Alistaste a ${nombre} - Clase: ${tipoClase}"
    }

    override fun eliminarUnidadid(id: Int): Boolean
    {
        return unidades.removeIf { it.id == id }
    }

    override fun limpiar()
    {
        unidades.clear()
        nextId = 0
    }

    override fun obtenerUnidadId(id: Int): Unidad?
    {
        return unidades.find { it.id == id }
    }

    override fun obtenerUnidadNombre(nombre: String): Unidad?
    {
        return unidades.find { it.nombre == nombre }
    }

    override fun obtenerUnidades(): List<Unidad> = unidades.toList()
}