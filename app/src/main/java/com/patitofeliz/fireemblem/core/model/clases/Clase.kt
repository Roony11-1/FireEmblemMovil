package com.patitofeliz.fireemblem.core.model.clases

import Animacion
import com.patitofeliz.fireemblem.core.model.Estadisticas
import com.patitofeliz.fireemblem.core.model.Sprite
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.model.armas.Arma
import com.patitofeliz.fireemblem.core.usecase.CombateEngine

abstract class Clase(
    var nombreClase: String,
    var arma: Arma?,
    var tipoArma: String,
    var sprite: Sprite,
    var estadisticasBase: Estadisticas,
    var estadisticasMaximas: Estadisticas)
{
    open lateinit var animAtaque: Animacion
    open lateinit var animCritAtaque: Animacion
    open var frameAtaque: Int = 5
    open var frameCritAtaque: Int = 5

    var animEsquive: Animacion = Animacion(
        frames = sprite.spriteDodge,
        delays = listOf(250, 250), // ejemplo de delays
        onFrame = null,
        onComplete = { animEsquive.onUpdateDisplay?.invoke(sprite.spriteIdle) }
    )

    // Función para iniciar ataque
    fun atacarConAnimacion(atacante: Unidad, defensor: Unidad, combateEngine: CombateEngine)
    {
        // Guardamos el callback original de onFrame si existiera
        val originalOnFrame = animAtaque.onFrame

        animAtaque.onFrame = null

        animAtaque.comenzar()
    }
}