package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.Crecimientos

interface ICrecimientoFactory
{
    fun generarCrecimientos(clase: String?): Crecimientos
}