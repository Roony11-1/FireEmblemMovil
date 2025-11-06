package com.patitofeliz.fireemblem.core.model.clases

import Animacion
import android.util.Log
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

    override var frameAtaque: Int = 5
}