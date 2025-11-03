package com.patitofeliz.fireemblem.core

import com.patitofeliz.fireemblem.core.services.UnidadService
import com.patitofeliz.fireemblem.core.usecase.CombateEngine
import com.patitofeliz.fireemblem.infrastructure.data.UnidadRepository
import com.patitofeliz.fireemblem.infrastructure.factory.ClaseFactory
import com.patitofeliz.fireemblem.infrastructure.factory.UnidadFactory

class main
{
    fun main()
    {
        println("Iniciando prueba de combate")
        // Inicialización
        val claseFactory = ClaseFactory(null)
        val unidadFactory = UnidadFactory(claseFactory)
        val unidadRepository = UnidadRepository()
        val combateEngine = CombateEngine()
        val unidadService = UnidadService(unidadRepository, combateEngine)

        // Crear unidades
        val ricardo = unidadFactory.crearUnidad(1, "Ricardo", "mirmidon")
        val mirmidon = unidadFactory.crearUnidad(2,"Enemigo", "mirmidon")

        // Agregar al repository
        unidadService.agregarUnidad(ricardo)
        unidadService.agregarUnidad(mirmidon)

        // Atacar
        unidadService.atacar(ricardo, mirmidon)
        println("HP Mirmidón: ${mirmidon.estadisticasActuales.pv}")
    }
}