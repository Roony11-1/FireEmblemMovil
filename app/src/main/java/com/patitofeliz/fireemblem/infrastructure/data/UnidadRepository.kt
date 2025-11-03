package com.patitofeliz.fireemblem.infrastructure.data

import com.patitofeliz.fireemblem.core.interfaces.IUnidadRepository
import com.patitofeliz.fireemblem.core.model.Unidad

class UnidadRepository : IUnidadRepository
{
    private val unidadesJugador = mutableListOf<Unidad>()

    override fun obtenerPorId(id: Int): Unidad?
    {
        return unidadesJugador.find { it.id == id }
    }

    override fun agregarUnidad(unidad: Unidad)
    {
        unidadesJugador.add(unidad)
    }

    override fun obtenerUnidades(): List<Unidad>
    {
        return unidadesJugador
    }

    override fun elimiarUnidadPorId(id: Int)
    {
        val unidad = obtenerPorId(id)
        unidadesJugador.remove(unidad)
    }
}