package com.patitofeliz.fireemblem.infrastructure.factory

import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.interfaces.IUnidadFactory
import com.patitofeliz.fireemblem.core.model.Crecimientos
import com.patitofeliz.fireemblem.core.model.SistemaNivel
import com.patitofeliz.fireemblem.core.model.Unidad

class UnidadFactory(private val claseFactory: IClaseFactory) : IUnidadFactory
{
    override fun crearUnidad(id: Int, nombre: String, tipo: String?): Unidad
    {
        // Crecimientos aleatorios
        val creciPv = (50..250).random()
        val creciFue = (50..250).random()
        val creciHab = (50..250).random()
        val creciVel = (50..250).random()
        val creciSue = (50..250).random()
        val creciDef = (50..250).random()
        val creciRes = (50..250).random()

        val clase = claseFactory.crearClase(tipo)
        return Unidad(id = id, nombre = nombre,
            nivel = SistemaNivel(17, 100),
            crecimientos = Crecimientos(creciPv, creciFue, creciHab, creciVel, creciSue, creciDef, creciRes),
            clase = clase)
    }
}