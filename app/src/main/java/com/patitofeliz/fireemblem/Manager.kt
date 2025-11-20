package com.patitofeliz.fireemblem

import android.content.Context
import com.patitofeliz.fireemblem.core.controller.UnidadController
import com.patitofeliz.fireemblem.core.interfaces.IArmaFactory
import com.patitofeliz.fireemblem.core.interfaces.IClaseFactory
import com.patitofeliz.fireemblem.core.interfaces.ICrecimientoFactory
import com.patitofeliz.fireemblem.core.interfaces.IUnidadController
import com.patitofeliz.fireemblem.core.interfaces.IUnidadFactory
import com.patitofeliz.fireemblem.core.interfaces.IUnidadRepository
import com.patitofeliz.fireemblem.core.services.LoginService
import com.patitofeliz.fireemblem.core.services.UnidadService
import com.patitofeliz.fireemblem.core.usecase.CombateEngine
import com.patitofeliz.fireemblem.infrastructure.data.UnidadRepository
import com.patitofeliz.fireemblem.infrastructure.data.UnidadRepositorySqLite
import com.patitofeliz.fireemblem.infrastructure.factory.ArmaFactory
import com.patitofeliz.fireemblem.infrastructure.factory.ClaseFactory
import com.patitofeliz.fireemblem.infrastructure.factory.CrecimientoFactory
import com.patitofeliz.fireemblem.infrastructure.factory.UnidadFactory

object Manager
{
    lateinit var claseFactory: IClaseFactory
        private set
    lateinit var crecimientoFactory: ICrecimientoFactory
    lateinit var armaFactory: IArmaFactory
    lateinit var unidadFactory: IUnidadFactory
        private set

    lateinit var unidadRepository: IUnidadRepository
        private set

    lateinit var combateEngine: CombateEngine

        private set

    lateinit var unidadService: UnidadService
        private set

    lateinit var unidadController: IUnidadController
        private set

    lateinit var unidadRepositorySqLite: UnidadRepositorySqLite

    lateinit var loginService: LoginService

    // Inicialización
    fun init(context: Context)
    {
        claseFactory = ClaseFactory()
        crecimientoFactory = CrecimientoFactory()
        armaFactory = ArmaFactory()
        unidadFactory = UnidadFactory(claseFactory, crecimientoFactory, armaFactory)
        unidadRepository = UnidadRepository()
        combateEngine = CombateEngine()
        unidadService = UnidadService(unidadRepository, combateEngine)
        unidadController = UnidadController()

        unidadRepositorySqLite = UnidadRepositorySqLite(context)

        loginService = LoginService()
    }
}