package com.patitofeliz.fireemblem.core.model.api

data class Usuario(
    val id: Int?,
    val nombreUsuario: String,
    val email: String,
    val contraseña: String,
    val telefono: String,
    val comuna: String,
    val region: String,
    val tipo: String,
    val profilePhoto:String)
