package com.patitofeliz.fireemblem.presentation.viewmodel

import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.Manager

class PrincipalViewModel : ViewModel()
{
    private val _opcionesMenu = MutableLiveData<List<String>>()
    val opcionesMenu: LiveData<List<String>> get() = _opcionesMenu

    init {
        // Aquí defines las opciones del menú principal
        _opcionesMenu.value = if (Manager.loginService.isLogged)
        {
            // Admin
            if (Manager.loginService.tipo == "admin")
            {
                mutableListOf(
                    "Crear Unidad",
                    "Banners",
                    "Items",
                    "Ver Unidades",
                    "Combate",
                    "Cámara",
                    "Salir")
            }
            else
            {
                // Usuario normal logueado
                mutableListOf(
                    "Banners",
                    "Ver Unidades",
                    "Combate",
                    "Cámara",
                    "Salir")
            }
        }
        else
        {
            listOf(
                "Crear Unidad",
                "Ver Unidades",
                "Combate",
                "Cámara",
                "Salir"
            )
        }
    }
}