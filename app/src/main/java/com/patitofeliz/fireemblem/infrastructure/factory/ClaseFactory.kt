package com.patitofeliz.fireemblem.infrastructure.factory

import android.content.Context
import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.model.clases.Clase
import com.patitofeliz.fireemblem.core.model.clases.Mirmidon

class ClaseFactory(private val context: Context?) : IClaseFactory
{
    private val clasesRegistradas = mutableMapOf<String, () -> Clase>()

    init
    {
        registrarClase("Mirmidón") { Mirmidon() }
    }

    fun registrarClase(tipo: String, constructor: () -> Clase)
    {
        clasesRegistradas[tipo] = constructor
    }

    override fun crearClase(tipo: String): Clase
    {
        val constructor = clasesRegistradas[tipo]
            ?: throw IllegalArgumentException("Tipo de clase desconocido: $tipo")
        return constructor()
    }

    fun clasesRegistradas(): List<String>
    {
        return clasesRegistradas.keys.toList()
    }
}