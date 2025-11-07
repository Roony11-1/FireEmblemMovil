package com.patitofeliz.fireemblem.core.model

import android.util.Log
import com.patitofeliz.fireemblem.core.model.clases.Clase

class Unidad(var id: Int? = null,
             var idPropietario: Int? = null,
             var nombre: String,
             var nivel: SistemaNivel,
             var clase: Clase,
             var crecimientos: Crecimientos,
             var estadisticasBase: Estadisticas = Estadisticas(1,1,1,1,1,1,1,1),
             var estadisticasActuales: Estadisticas = Estadisticas(1,1,1,1,1,1,1,1))
{
    init
    {
        this.estadisticasBase = this.clase.estadisticasBase
        this.estadisticasActuales = this.estadisticasBase.clon()

        // Verificamos si es nivel superior a 1
        if (this.nivel.nivel > 1)
        {
            for (i in 1..this.nivel.nivel)
            {
                Log.d("NivelDebug", "Aplicando crecimiento para el nivel $i")
                aplicarCrecimiento()
            }
        }


        this.agregarExperiencia(0)
    }

    fun agregarExperiencia(exp: Int)
    {
        if (this.nivel.agregarExperiencia(exp))
        {
            aplicarCrecimiento()
        }
    }

    private fun aplicarCrecimiento()
    {
            val crecimientos = this.crecimientos.crecimientos
            val stats = this.estadisticasBase.stats
            val statsMaximas = this.clase.estadisticasMaximas.stats

        // Debug: Muestra el estado inicial de los crecimientos y las estadísticas.
        Log.d("CrecimientoDebug", "======================")
        Log.d("CrecimientoDebug", "Crecimientos: $crecimientos")
        Log.d("CrecimientoDebug", "Estadísticas iniciales: $stats")

        for (key in stats.keys)
        {
            var crecimiento = crecimientos[key] ?: 0

            // Debug: Muestra el crecimiento inicial para cada stat.
            Log.d("CrecimientoDebug", "Procesando $key: Crecimiento inicial = $crecimiento")
            Log.d("CrecimientoDebug", "==========")
            while (crecimiento > 0 && stats[key]!! < statsMaximas[key]!!)
            {
                // Debug: Verifica la probabilidad antes de aplicar el crecimiento.
                val probabilidad = calcularProbabilidad(crecimiento)
                Log.d("CrecimientoDebug", "Probabilidad para $key con crecimiento $crecimiento: $probabilidad")

                if (probabilidad) {
                    // Debug: Muestra el valor de la estadística antes de incrementarla.
                    Log.d("CrecimientoDebug", "Incrementando $key. Valor actual: ${stats[key]}")

                    stats[key] = stats[key]!!.plus(1)

                    // Debug: Muestra el valor de la estadística después de incrementarla.
                    Log.d("CrecimientoDebug", "Nuevo valor de $key: ${stats[key]}")
                }

                // Reducir el crecimiento en 100.
                crecimiento -= 100
                // Debug: Muestra el nuevo valor de crecimiento después de la reducción.
                Log.d("CrecimientoDebug", "Crecimiento restante para $key: $crecimiento")
            }
        }

        // Actualiza las estadísticas finales.
        this.estadisticasBase.actualizarStats()

        // Debug: Muestra las estadísticas finales después de aplicar el crecimiento.
        Log.d("CrecimientoDebug", "Estadísticas finales: ${this.estadisticasBase.stats}")
    }


    private fun calcularProbabilidad(prob: Int): Boolean
    {
        // Genera un número aleatorio entre 0 y 100
        val randomValue = (0..100).random()

        return randomValue <= prob.coerceAtLeast(0)
    }
}