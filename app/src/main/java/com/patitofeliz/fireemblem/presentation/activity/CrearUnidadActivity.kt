package com.patitofeliz.fireemblem.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.view.ArrayAdapterFactory
import com.patitofeliz.fireemblem.databinding.ActivityCrearUnidadBinding
import com.patitofeliz.fireemblem.presentation.viewmodel.CrearUnidadVIewModel
import com.patitofeliz.fireemblem.presentation.viewmodel.PrincipalViewModel
import kotlin.getValue

class CrearUnidadActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityCrearUnidadBinding
    private val viewModel: CrearUnidadVIewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCrearUnidadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.clases.observe(this) { opciones ->
            val adapter = ArrayAdapterFactory.create(this, opciones)
            binding.spSelectorClaseCrear.adapter = adapter
        }

        binding.spSelectorClaseCrear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                val claseSeleccionada = parent?.getItemAtPosition(position) as String
                viewModel.onClaseSeleccionada(claseSeleccionada)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.etNombreCrear.addTextChangedListener {
            viewModel.onNombreCambiado(it.toString())
        }

        viewModel.unidadPreview.observe(this) { unidad ->
            if (unidad == null)
                return@observe

            val spriteScreen = unidad.clase.sprite.spriteScreen
            binding.ivPrevieSpriteScreenCrear.setImageResource(spriteScreen)
            val spriteClase = unidad.clase.sprite.spriteIdle
            binding.ivPreviewSpriteClaseCrear.setImageResource(spriteClase)
        }

        binding.btnCrearUnidad.setOnClickListener {
            viewModel.crearUnidad(this@CrearUnidadActivity)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}