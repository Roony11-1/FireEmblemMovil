package com.patitofeliz.fireemblem.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.core.model.Unidad

class CrearUnidadVIewModel : ViewModel()
{
    private val _clases = MutableLiveData<List<String>>()
    val clases: LiveData<List<String>> get() = _clases

    private val _unidadPreview = MutableLiveData<Unidad?>()
    val unidadPreview: LiveData<Unidad?> get() = _unidadPreview

    private var nombreUnidad: String = ""
    private var claseSeleccionada: String? = null

    init
    {
        _clases.value = Manager.claseFactory.clasesRegistradas()
    }

    fun onClaseSeleccionada(nombreClase: String)
    {
        claseSeleccionada = nombreClase
        actualizarPreview()
    }

    fun onNombreCambiado(nombre: String)
    {
        nombreUnidad = nombre
        actualizarPreview()
    }

    fun crearUnidad(context: Context)
    {
        Log.d("DEBUG", "Crear unidad: nombre=$nombreUnidad, clase=$claseSeleccionada")
        Manager.unidadController.agregarUnidad(context, nombreUnidad, claseSeleccionada)
    }
    private fun actualizarPreview()
    {
        if (claseSeleccionada != null)
            _unidadPreview.value = Manager.unidadFactory.crearUnidad(0, "Plata",null, nombreUnidad, claseSeleccionada!!)
    }
}