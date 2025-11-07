package com.patitofeliz.fireemblem.core.usecase

import com.patitofeliz.fireemblem.core.model.Unidad

class CombateEngine
{

    fun calcularDmg(atacante: Unidad, defensor: Unidad): Int
    {
        val fuerzaAtacante = atacante.estadisticasBase.fue
        val defensaDefensor = defensor.estadisticasBase.def
        val potenciaArma = atacante.clase.arma.potencia
        return ((fuerzaAtacante + potenciaArma) - defensaDefensor).coerceAtLeast(0)
    }

    fun aplicarDmg(defensor: Unidad, dmg: Int)
    {
        defensor.estadisticasActuales.pv = (defensor.estadisticasActuales.pv - dmg).coerceAtLeast(0)
    }

    // Tengo la logica directo en el activity mm la deberia mover?
    /*fun atacar(atacante: Unidad, defensor: Unidad):String
    {
        var dmg = calcularDmg(atacante, defensor)

        val probCrit = calcProbCritico(atacante, defensor).coerceIn(0, 100)
        if ((0..100).random() <= probCrit)
        {
            dmg *= 3
            return "${atacante.nombre} ha hecho un golpe critico! Daño: ${dmg}"
        }

        aplicarDmg(defensor, dmg)
        return "${atacante.nombre} ataca! Daño: ${dmg}"
    }*/

    fun calcularPrecision(atacante: Unidad): Int
    {
        return (atacante.estadisticasBase.hab * 2) + (atacante.estadisticasBase.sue / 2) +
                atacante.clase.arma.golpe
    }

    fun calcularEvasion(defensor: Unidad): Int
    {
        return (velocidadAtaque(defensor)*2+defensor.estadisticasActuales.sue)
    }

    fun velocidadAtaque(unidad: Unidad): Int
    {
        return unidad.estadisticasBase.vel - (unidad.clase.arma.peso - unidad.estadisticasBase.con)
    }

    fun calcularGolpe(atacante: Unidad, defensor: Unidad): Int
    {
        return (calcularPrecision(atacante) - calcularEvasion(defensor)).coerceAtLeast(0)
    }

    fun puedeAtacarDoble(atacante: Unidad, defensor: Unidad): Boolean
    {
        return velocidadAtaque(atacante) - velocidadAtaque(defensor) > 4
    }

    fun calcularHacerCritico(atacante: Unidad): Int
    {
        return (atacante.estadisticasBase.hab/2) + (atacante.clase.arma.critico)
    }

    fun calcularDodgeCritico(defensor: Unidad): Int
    {
        return defensor.estadisticasBase.sue
    }

    fun calcProbCritico(atacante: Unidad, defensor: Unidad): Int
    {
        return (calcularHacerCritico(atacante) - calcularDodgeCritico(defensor)).coerceAtLeast(0)
    }

    fun estaVivo(unidad: Unidad): Boolean = unidad.estadisticasActuales.pv > 0
}
