package com.patitofeliz.fireemblem

import android.content.Context
import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.interfaces.IUnidadFactory
import com.patitofeliz.fireemblem.core.interfaces.IUnidadRepository
import com.patitofeliz.fireemblem.core.services.UnidadService
import com.patitofeliz.fireemblem.core.usecase.CombateEngine
import com.patitofeliz.fireemblem.infrastructure.data.UnidadRepository
import com.patitofeliz.fireemblem.infrastructure.factory.ClaseFactory
import com.patitofeliz.fireemblem.infrastructure.factory.UnidadFactory

object Manager
{
    lateinit var claseFactory: IClaseFactory
        private set
    lateinit var unidadFactory: IUnidadFactory
        private set

    lateinit var unidadRepository: IUnidadRepository
        private set

    lateinit var combateEngine: CombateEngine

        private set

    lateinit var unidadService: UnidadService
        private set

    // Inicialización
    fun init(context: Context)
    {
        claseFactory = ClaseFactory(context)
        unidadFactory = UnidadFactory(claseFactory)
        unidadRepository = UnidadRepository()
        combateEngine = CombateEngine()
        unidadService = UnidadService(unidadRepository, combateEngine)
    }
}