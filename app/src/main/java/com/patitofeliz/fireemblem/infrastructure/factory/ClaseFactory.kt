package com.patitofeliz.fireemblem.infrastructure.factory

import android.content.Context
import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.model.clases.Clase
import com.patitofeliz.fireemblem.core.model.clases.Mirmidon
import com.patitofeliz.fireemblem.core.model.clases.Roy

class ClaseFactory() : IClaseFactory
{
    private val clasesMap = mutableMapOf<String, () -> Clase>()

    init
    {
        registrarClase("Mirmidón") { Mirmidon() }
        registrarClase("Lord (Roy)") { Roy() }
    }

    fun registrarClase(tipo: String, constructor: () -> Clase)
    {
        clasesMap[tipo] = constructor
    }

    override fun crearClase(tipo: String?): Clase
    {
        val constructor = clasesMap[tipo]
            ?: throw IllegalArgumentException("Tipo de clase desconocido: $tipo")
        return constructor()
    }

    override fun clasesRegistradas(): List<String> = clasesMap.keys.toList()
}