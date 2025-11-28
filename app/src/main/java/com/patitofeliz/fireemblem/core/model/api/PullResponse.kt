package com.patitofeliz.fireemblem.core.model.api

data class PullResponse(val pullId: Int,
    val usuarioId: Int,
    val bannerId: Long,
    val nombre: String,
    val clase: String,
    val tipo: String,
    val rareza: String) // crear el activity
