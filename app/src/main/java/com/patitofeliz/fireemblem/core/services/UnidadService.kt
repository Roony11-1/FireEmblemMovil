package com.patitofeliz.fireemblem.core.services

import com.patitofeliz.fireemblem.core.interfaces.IUnidadRepository
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.usecase.CombateEngine

class UnidadService(private val unidadRepository: IUnidadRepository,
    private val combateEngine: CombateEngine)
{
    fun agregarUnidad(unidad: Unidad)
    {
        unidadRepository.agregarUnidad(unidad)
    }

    fun obtenerUnidades(): List<Unidad>
    {
        return unidadRepository.obtenerUnidades()
    }

    fun obtenerUnidadPorId(id: Int):Unidad?
    {
        return unidadRepository.obtenerPorId(id)
    }

    /*fun atacar(atacante: Unidad, defensor: Unidad)
    {
        combateEngine.atacar(atacante, defensor)
    }*/
}