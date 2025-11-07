package com.patitofeliz.fireemblem.core.model.api

data class UnidadApi(val id: Int,
    val idPropietario: Int,
    val nombre: String,
    val clase: String,
    val nivel: Int,
    val experiencia: Int,
    val crePv: Int,
    val creFue: Int,
    val creHab: Int,
    val creVel: Int,
    val creSue: Int,
    val creDef: Int,
    val creRes: Int)
