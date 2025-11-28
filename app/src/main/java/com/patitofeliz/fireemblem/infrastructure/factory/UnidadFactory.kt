package com.patitofeliz.fireemblem.infrastructure.factory

import com.patitofeliz.fireemblem.core.interfaces.IArmaFactory
import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.interfaces.ICrecimientoFactory
import com.patitofeliz.fireemblem.core.interfaces.IUnidadFactory
import com.patitofeliz.fireemblem.core.model.Crecimientos
import com.patitofeliz.fireemblem.core.model.SistemaNivel
import com.patitofeliz.fireemblem.core.model.Unidad

class UnidadFactory(private val claseFactory: IClaseFactory,
    private val crecimientoFactory: ICrecimientoFactory, private val armaFactory: IArmaFactory) : IUnidadFactory
{
    override fun crearUnidad(
        id: Int?,
        tipoArma: String,
        idPropietario: Int?,
        nombre: String,
        tipo: String?,
        nivel: Int,
        experiencia: Int,
        crecimiento: Crecimientos?): Unidad
    {

        val clase = claseFactory.crearClase(tipo)

        val crecimientoAsignado = crecimiento ?: crecimientoFactory.generarCrecimientos(tipo)

        val unidad = Unidad(
            id = id,
            idPropietario = idPropietario,
            nombre = nombre,
            nivel = SistemaNivel(nivel, experiencia),
            crecimientos = crecimientoAsignado,
            clase = clase
        )

        unidad.clase.arma = armaFactory.crearArma(clase.tipoArma, tipoArma)

        return unidad
    }
}