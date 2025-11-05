package com.patitofeliz.fireemblem.core.usecase

import android.util.Log
import com.patitofeliz.fireemblem.core.model.Unidad
import kotlinx.coroutines.*

class CombateEngine
{

    fun calcularDmg(atacante: Unidad, defensor: Unidad): Int
    {
        val fuerzaAtacante = atacante.estadisticasActuales.fue
        val defensaDefensor = defensor.estadisticasActuales.def
        val potenciaArma = atacante.clase.arma.potencia
        return ((fuerzaAtacante + potenciaArma) - defensaDefensor).coerceAtLeast(0)
    }

    fun aplicarDmg(defensor: Unidad, dmg: Int)
    {
        defensor.estadisticasActuales.pv = (defensor.estadisticasActuales.pv - dmg).coerceAtLeast(0)
    }

    fun atacar(atacante: Unidad, defensor: Unidad)
    {
        var dmg = calcularDmg(atacante, defensor)

        val probCrit = calcProbCritico(atacante, defensor).coerceIn(0, 100)
        if ((0..100).random() <= probCrit)
            dmg *= 3

        aplicarDmg(defensor, dmg)
    }

    fun calcularPrecision(atacante: Unidad): Int
    {
        return (atacante.estadisticasActuales.hab * 2) + (atacante.estadisticasActuales.sue / 2) +
                atacante.clase.arma.golpe
    }

    fun calcularEvasion(defensor: Unidad): Int
    {
        return (velocidadAtaque(defensor)*2+defensor.estadisticasActuales.sue)
    }

    fun velocidadAtaque(unidad: Unidad): Int
    {
        return unidad.estadisticasActuales.vel - (unidad.clase.arma.peso - unidad.estadisticasActuales.con)
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
        return (atacante.estadisticasActuales.hab/2) + (atacante.clase.arma.critico)
    }

    fun calcularDodgeCritico(defensor: Unidad): Int
    {
        return defensor.estadisticasActuales.sue
    }

    fun calcProbCritico(atacante: Unidad, defensor: Unidad): Int
    {
        return (calcularHacerCritico(atacante) - calcularDodgeCritico(defensor)).coerceAtLeast(0)
    }

    fun estaVivo(unidad: Unidad): Boolean = unidad.estadisticasActuales.pv > 0
}
