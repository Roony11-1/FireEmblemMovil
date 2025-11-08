package com.patitofeliz.fireemblem.core.model.api

data class ResponseApi<T>(
    val success: Boolean,
    val message: String,
    val status: String,
    val entity: T?)
