package com.patitofeliz.fireemblem.core.model.clases

import Animacion
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.model.Estadisticas
import com.patitofeliz.fireemblem.core.model.Sprite
import com.patitofeliz.fireemblem.core.model.armas.Espada

class Roy : Clase(nombreClase = "Lord (Roy)",
    arma = Espada(),
    sprite = Sprite(
        spriteScreen = R.drawable.roy_screen,
        spriteIdle = R.drawable.roy_idle,
        spriteAtack = listOf(
            R.drawable.roy_at1,
            R.drawable.roy_at2,
            R.drawable.roy_at3,
            R.drawable.roy_at4,
            R.drawable.roy_at5,
            R.drawable.roy_at6,
            R.drawable.roy_at7,
            R.drawable.roy_at8,
            R.drawable.roy_at9,
            R.drawable.roy_at10,
            R.drawable.roy_at11,
            R.drawable.roy_at12,
            R.drawable.roy_at13,
            R.drawable.roy_at14,
            R.drawable.roy_at15,
            R.drawable.roy_at16,
            R.drawable.roy_at17,
            R.drawable.roy_at18,
            R.drawable.roy_at19,
            R.drawable.roy_at20,
            R.drawable.roy_at21,
            R.drawable.roy_at22,
            R.drawable.roy_at23
        ),
        spriteCritAtack = listOf(
            R.drawable.roy_crit1,
            R.drawable.roy_crit2,
            R.drawable.roy_crit3,
            R.drawable.roy_crit4,
            R.drawable.roy_crit5,
            R.drawable.roy_crit6,
            R.drawable.roy_crit7,
            R.drawable.roy_crit8,
            R.drawable.roy_crit9,
            R.drawable.roy_crit10,
            R.drawable.roy_crit11,
            R.drawable.roy_crit12,
            R.drawable.roy_crit13,
            R.drawable.roy_crit14,
            R.drawable.roy_crit15,
            R.drawable.roy_crit16,
            R.drawable.roy_crit17,
            R.drawable.roy_crit18,
            R.drawable.roy_crit19,
            R.drawable.roy_crit20,
            R.drawable.roy_at9,
            R.drawable.roy_at10,
            R.drawable.roy_at11,
            R.drawable.roy_at12,
            R.drawable.roy_at13,
            R.drawable.roy_at14,
            R.drawable.roy_at15,
            R.drawable.roy_at16,
            R.drawable.roy_at17,
            R.drawable.roy_at18,
            R.drawable.roy_at19,
            R.drawable.roy_at20,
            R.drawable.roy_at21,
            R.drawable.roy_at22,
            R.drawable.roy_at23
        ),
        spriteDodge = listOf(
            R.drawable.roy_dodge1,
            R.drawable.roy_dodge2
        )
    ),
    estadisticasBase = Estadisticas(18, 5, 5, 7, 7, 5, 0, 6),
    estadisticasMaximas = Estadisticas(60, 60, 60, 60, 30, 60, 30, 0))
{
    override var animAtaque: Animacion = Animacion(
        frames = this.sprite.spriteAtack,
        delays = listOf(
            131, 111, 78, 62, 99, 498, 69, 88, 113, 132, 104,
            120, 95, 110, 105, 100, 90, 85, 115, 120, 130, 140, 150
        ),
        onFrame = null,
        onComplete = null
    )

    override var animCritAtaque: Animacion = Animacion(
        frames = sprite.spriteCritAtack,
        delays = listOf(
            130, 110, 80, 60, 50,
            500, 70, 90, 110, 130,
            160, 90, 80, 70, 60,
            50, 120, 100, 90, 100,
            113, 132, 104, 120, 95,
            110, 105, 100, 90, 85,
            115, 120, 130, 140, 150, 150
        ),
        onFrame = null,
        onComplete = null
    )

    override var frameAtaque: Int = 5
    override var frameCritAtaque: Int = 20
}