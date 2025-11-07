package com.patitofeliz.fireemblem.infrastructure.factory

import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.interfaces.ICrecimientoFactory
import com.patitofeliz.fireemblem.core.interfaces.IUnidadFactory
import com.patitofeliz.fireemblem.core.model.Crecimientos
import com.patitofeliz.fireemblem.core.model.SistemaNivel
import com.patitofeliz.fireemblem.core.model.Unidad

class UnidadFactory(private val claseFactory: IClaseFactory,
    private val crecimientoFactory: ICrecimientoFactory) : IUnidadFactory
{
    override fun crearUnidad(id: Int?, nombre: String, tipo: String?, nivel: Int, experiencia: Int, crecimiento: Crecimientos?): Unidad
    {
        val clase = claseFactory.crearClase(tipo)
        var crecimientoAsignado: Crecimientos? = crecimiento
        if (crecimientoAsignado == null)
            crecimientoAsignado = crecimientoFactory.generarCrecimientos(tipo)
        return Unidad(id = id, nombre = nombre,
            nivel = SistemaNivel(nivel, experiencia),
            crecimientos = crecimientoAsignado,
            clase = clase)
    }
}