package com.patitofeliz.fireemblem.core.model.api

data class ResponseApi<T>(
    val message: String,
    val data: T?)
