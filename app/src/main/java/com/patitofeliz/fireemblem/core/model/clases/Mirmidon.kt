package com.patitofeliz.fireemblem.core.model.clases

import Animacion
import android.util.Log
import com.patitofeliz.fireemblem.core.model.Estadisticas
import com.patitofeliz.fireemblem.core.model.Sprite

import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.model.armas.Espada
import com.patitofeliz.fireemblem.core.usecase.CombateEngine

class Mirmidon : Clase(nombreClase = "Mirmidon",
    arma = Espada(),
    sprite = Sprite(
        spriteScreen = R.drawable.mirmidon_screen,
        spriteIdle = R.drawable.mirmidon_idle,
        spriteAtack = listOf(
            R.drawable.mirmidon_at1,
            R.drawable.mirmidon_at2,
            R.drawable.mirmidon_at3,
            R.drawable.mirmidon_at4,
            R.drawable.mirmidon_at5,
            R.drawable.mirmidon_at6,
            R.drawable.mirmidon_at7,
            R.drawable.mirmidon_at8,
            R.drawable.mirmidon_at9,
            R.drawable.mirmidon_at10,
            R.drawable.mirmidon_at11,
            R.drawable.mirmidon_at12
        ),
        spriteDodge = listOf(
            R.drawable.mirmidon_dodge1,
            R.drawable.mirmidon_dodge2
        )
    ),
    estadisticasBase = Estadisticas(22, 6, 11, 11, 5, 5, 0, 5),
    estadisticasMaximas = Estadisticas(60, 60, 60, 60, 30, 60, 30, 0))
{
    override var animAtaque: Animacion = Animacion(
        frames = sprite.spriteAtack,
        delays = listOf(131, 111, 78, 62, 99, 498, 69, 88, 113, 132, 104, 120),
        onFrame = null,
        onComplete = null
    )

    override var frameAtaque: Int = 5
}
