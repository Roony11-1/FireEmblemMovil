package com.patitofeliz.fireemblem.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.Manager.unidadController
import com.patitofeliz.fireemblem.Manager.unidadRepositorySqLite
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.model.api.UnidadApi
import com.patitofeliz.fireemblem.core.view.ArrayAdapterFactory
import com.patitofeliz.fireemblem.databinding.ActivityPrincipalBinding
import com.patitofeliz.fireemblem.helper.DBHelper
import com.patitofeliz.fireemblem.presentation.viewmodel.PrincipalViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrincipalActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityPrincipalBinding
    private val viewModel: PrincipalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.opcionesMenu.observe(this) { opciones ->
            val adapter = ArrayAdapterFactory.create(this, opciones)
            binding.lvPrincipal.adapter = adapter
        }

        /*val context: Context = this // tu context
        context.deleteDatabase(DBHelper.DATABASE_NAME)*/

        if (Manager.loginService.isLogged)
        {
            RetroFitClient.unidadService.findMyUnits(Manager.loginService.idLogin!!)
                .enqueue(object : Callback<List<UnidadApi>> {
                    override fun onResponse(
                        call: Call<List<UnidadApi>>,
                        response: Response<List<UnidadApi>>)
                    {
                        if (response.isSuccessful)
                        {
                            val lista = response.body()
                            Log.d("APIUnidad", "Lista: ${lista?.size}")
                            if (!lista.isNullOrEmpty())
                            {
                                lista.forEach { unidad ->
                                    Log.d("APIUnidad", "Lista: ${lista?.size} - Unidad: ${unidad.nombre}")
                                    unidadController.agregarUnidadApi(unidad)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<UnidadApi>>, t: Throwable)
                    {
                        t.printStackTrace()
                    }
                })
        }
        else
        {
            val unidadesSqLite: List<Unidad> = unidadRepositorySqLite.obtenerUnidades()

            for (unidad in unidadesSqLite)
            {
                unidadController.agregarUnidadDB(unidad)
            }
        }

        val menuAcciones = mapOf(
            "Crear Unidad" to { irCrearUnidad() },
            "Ver Unidades" to { irVerUnidades() },
            "Combate" to { irCombate() },
            "Salir" to { salir() }
        )

        binding.lvPrincipal.setOnItemClickListener { parent, view, position, id ->
            val opcionSeleccionada = parent.getItemAtPosition(position) as String

            menuAcciones[opcionSeleccionada]?.invoke()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun irCrearUnidad()
    {
        val intent = Intent(this, CrearUnidadActivity::class.java)

        startActivity(intent)
    }

    private fun irVerUnidades()
    {
        if (unidadController.obtenerUnidades().size == 0)
            return
        val intent = Intent(this, VerUnidadActivity::class.java)

        startActivity(intent)
    }

    private fun irCombate()
    {
        if (unidadController.obtenerUnidades().size == 0)
            return
        val intent = Intent(this, PrepararCombateActivity::class.java)

        startActivity(intent)
    }

    private fun salir()
    {
        val intent = Intent(this, LoginActivity::class.java)
        unidadController.limpiar()
        startActivity(intent)
        finish()
    }
}