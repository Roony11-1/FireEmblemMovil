package com.patitofeliz.fireemblem.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.core.model.Unidad

class PrepararCombateViewModel : ViewModel()
{
    private val _unidades = MutableLiveData<List<Unidad>>()
    val unidades: LiveData<List<Unidad>> get() = _unidades

    private val _unidad = MutableLiveData<Unidad?>()
    val unidad: LiveData<Unidad?> get() = _unidad

    init
    {
        _unidades.value = Manager.unidadController.obtenerUnidades()
    }

    fun onUnidadSeleccionada(nombre: String)
    {
        val unidad = Manager.unidadController.obtenerUnidadNombre(nombre)
        _unidad.value = unidad
    }

    fun infoUnidad(): String
    {
        val unidadSeleccionada = unidad.value

        if (unidadSeleccionada == null)
            return "Info (No deberias ver esto)"

        val mensaje = """
        Nombre: ${unidadSeleccionada.nombre}
        Clase: ${unidadSeleccionada.clase.nombreClase}
        Nivel: ${unidadSeleccionada.nivel.nivel}
        Experiencia: ${unidadSeleccionada.nivel.experiencia}
        PV: ${unidadSeleccionada.estadisticasBase.pv} - Crecimiento: ${unidadSeleccionada.crecimientos.pv}
        Fuerza: ${unidadSeleccionada.estadisticasBase.fue} - Crecimiento: ${unidadSeleccionada.crecimientos.fue}
        Habilidad: ${unidadSeleccionada.estadisticasBase.hab} - Crecimiento: ${unidadSeleccionada.crecimientos.hab}
        Velocidad: ${unidadSeleccionada.estadisticasBase.vel} - Crecimiento: ${unidadSeleccionada.crecimientos.vel}
        Defensa: ${unidadSeleccionada.estadisticasBase.def} - Crecimiento: ${unidadSeleccionada.crecimientos.def}
        Resistencia: ${unidadSeleccionada.estadisticasBase.res} - Crecimiento: ${unidadSeleccionada.crecimientos.res}
        Suerte: ${unidadSeleccionada.estadisticasBase.sue} - Crecimiento: ${unidadSeleccionada.crecimientos.sue}
        Constitución: ${unidadSeleccionada.estadisticasBase.con}
    """.trimIndent()

        return mensaje
    }
}