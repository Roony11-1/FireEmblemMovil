package com.patitofeliz.fireemblem.presentation.viewmodel

import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.ViewModel
import com.patitofeliz.fireemblem.Manager

class PrincipalViewModel : ViewModel()
{
    // El adaptador para el LV Principal
    private var opcionesMenuPrincipal: Array<String> = arrayOf("Hola")
    // Un Metido auxiliar para cargar el adapter¿?

    // Necesito cargar el adapter basicamente
    private fun cargarAdapter(arrayMenu:Array<String>): android.widget.ArrayAdapter
    {

    }

    private fun cargarAdapterListView(listView:ListView, adaptador:android.widget.ArrayAdapter)
    {

    }
}