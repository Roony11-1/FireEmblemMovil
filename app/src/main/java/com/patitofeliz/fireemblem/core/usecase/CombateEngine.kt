package com.patitofeliz.fireemblem.core.usecase

import com.patitofeliz.fireemblem.core.model.Unidad

class CombateEngine
{
    fun calcularDmg(atacante: Unidad, defensor: Unidad): Int
    {
        val fuerzaAtacante = atacante.estadisticasActuales.fue
        val defensaDefensor = defensor.estadisticasActuales.def
        val resistenciaDefensor = defensor.estadisticasActuales.res

        return (fuerzaAtacante - defensaDefensor).coerceAtLeast(0)
    }

    fun aplicarDmg(defensor: Unidad, dmg: Int)
    {
        defensor.estadisticasActuales.pv = (defensor.estadisticasActuales.pv - dmg).coerceAtLeast(0)
    }

    fun atacar(atacante: Unidad, defensor: Unidad)
    {
        val dmg = calcularDmg(atacante, defensor)

        aplicarDmg(defensor, dmg)
    }

    fun estaVivo(unidad: Unidad): Boolean
    {
        return unidad.estadisticasActuales.pv > 0
    }
}