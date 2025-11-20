package com.patitofeliz.fireemblem.core.interfaces

import com.patitofeliz.fireemblem.core.model.armas.Arma

interface IArmaFactory
{
    fun crearArma(nombre:String, tipo:String):Arma
}