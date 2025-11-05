package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.clases.Clase

interface IClaseFactory
{
    fun crearClase(tipo: String?): Clase
    fun clasesRegistradas(): List<String>
}