package com.patitofeliz.fireemblem.infrastructure.factory

import com.patitofeliz.fireemblem.core.interfaces.IArmaFactory
import com.patitofeliz.fireemblem.core.model.armas.Arma
import com.patitofeliz.fireemblem.core.model.armas.Espada

class ArmaFactory : IArmaFactory
{
    override fun crearArma(nombre: String, tipo: String): Arma
    {
        val estadisticasArma:List<Int> = estadisticasArma(tipo)

        return when (nombre)
        {
            "Espada" -> Espada(tipo, estadisticasArma[0], estadisticasArma[1], estadisticasArma[2], estadisticasArma[3])
            else -> throw IllegalArgumentException("No existe esa arma: $nombre")
        }
    }

    private fun estadisticasArma(tipo: String):List<Int>
    {
        return when (tipo)
        {
            "Hierro" -> listOf(5, 5, 90, 0)
            "Asesina" -> listOf(7, 9, 75, 30)
            else -> throw IllegalArgumentException("No existe ese tipo: $tipo")
        }
    }
}