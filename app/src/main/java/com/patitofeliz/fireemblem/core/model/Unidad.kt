package com.patitofeliz.fireemblem.core.model

import com.patitofeliz.fireemblem.core.model.clases.Clase

class Unidad(var id: Int? = null,
             var nombre: String,
             var clase: Clase,
             var estadisticasBase: Estadisticas = Estadisticas(1,1,1,1,1,1,1,1),
             var estadisticasActuales: Estadisticas = Estadisticas(1,1,1,1,1,1,1,1))
{
    init
    {
        this.estadisticasBase = this.clase.estadisticasBase
        this.estadisticasActuales = this.estadisticasBase
    }
}