package com.patitofeliz.fireemblem.infrastructure.factory

import android.content.Context
import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.model.clases.Clase
import com.patitofeliz.fireemblem.core.model.clases.Mirmidon

class ClaseFactory(private val context: Context?) : IClaseFactory
{
    override fun crearClase(tipo: String): Clase
    {
        return when(tipo.lowercase())
        {
            "mirmidon" -> Mirmidon()
            else -> throw IllegalArgumentException("Tipo de clase desconocido")
        }
    }
}