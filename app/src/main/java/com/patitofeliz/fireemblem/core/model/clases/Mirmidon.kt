package com.patitofeliz.fireemblem.core.model.clases

import Animacion
import android.util.Log
import com.patitofeliz.fireemblem.core.model.Estadisticas
import com.patitofeliz.fireemblem.core.model.Sprite

import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.model.armas.Espada
import com.patitofeliz.fireemblem.core.usecase.CombateEngine

class Mirmidon : Clase(nombreClase = "Mirmidón",
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
        spriteCritAtack = listOf(
            R.drawable.mirmidon_crit1,
            R.drawable.mirmidon_crit2,
            R.drawable.mirmidon_crit3,
            R.drawable.mirmidon_crit4,
            R.drawable.mirmidon_crit5,
            R.drawable.mirmidon_crit6,
            R.drawable.mirmidon_crit7,
            R.drawable.mirmidon_crit8,
            R.drawable.mirmidon_crit9,
            R.drawable.mirmidon_crit10,
            R.drawable.mirmidon_crit11,
            R.drawable.mirmidon_crit12,
            R.drawable.mirmidon_crit13,
            R.drawable.mirmidon_crit14,
            R.drawable.mirmidon_crit15,
            R.drawable.mirmidon_crit16,
            R.drawable.mirmidon_crit17,
            R.drawable.mirmidon_crit18,
            R.drawable.mirmidon_crit19,
            R.drawable.mirmidon_crit20,
            R.drawable.mirmidon_crit21,
            R.drawable.mirmidon_crit22,
            R.drawable.mirmidon_crit23,
            R.drawable.mirmidon_crit24,
            R.drawable.mirmidon_crit25,
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

    override var animCritAtaque: Animacion = Animacion(
        frames = sprite.spriteCritAtack,
        delays = listOf(
            50, 50, 50, 50, 50,
            50, 35, 35, 35, 50,
            40, 45, 50, 45, 45,
            50, 45, 50, 90, 80,
            70, 100, 70, 70, 70,
            498, 69, 88, 113, 132,
            104, 120, 128
        ),
        onFrame = null,
        onComplete = null
    )

    override var frameAtaque: Int = 5
    override var frameCritAtaque: Int = 25
}
