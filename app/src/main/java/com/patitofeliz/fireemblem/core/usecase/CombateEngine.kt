package com.patitofeliz.fireemblem.core.usecase

import com.patitofeliz.fireemblem.core.model.Unidad

class CombateEngine
{

    fun calcularDmg(atacante: Unidad, defensor: Unidad): Int
    {
        val arma = atacante.clase.arma
            ?: throw IllegalStateException("El atacante no tiene arma")

        val fuerzaAtacante = atacante.estadisticasBase.fue
        val defensaDefensor = defensor.estadisticasBase.def
        val potenciaArma = arma.potencia

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
        val hab = atacante.estadisticasBase.hab
        val sue = atacante.estadisticasBase.sue

        val golpeArma = atacante.clase.arma?.golpe ?: 0

        return (hab * 2) + (sue / 2) + golpeArma
    }

    fun calcularEvasion(defensor: Unidad): Int
    {
        return (velocidadAtaque(defensor)*2+defensor.estadisticasActuales.sue)
    }

    fun velocidadAtaque(unidad: Unidad): Int
    {
        val pesoArma = unidad.clase.arma?.peso ?: 0
        val vel = unidad.estadisticasBase.vel
        val con = unidad.estadisticasBase.con

        return vel - (pesoArma - con)
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
        val hab = atacante.estadisticasBase.hab
        val criticoArma = atacante.clase.arma?.critico ?: 0

        return (hab / 2) + criticoArma
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
