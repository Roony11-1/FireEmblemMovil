package com.patitofeliz.fireemblem.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.view.ArrayAdapterFactory
import com.patitofeliz.fireemblem.databinding.ActivityVerUnidadBinding
import com.patitofeliz.fireemblem.presentation.viewmodel.VerUnidadViewModel

class VerUnidadActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityVerUnidadBinding
    private val viewModel: VerUnidadViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVerUnidadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivFloorVerUnidad.setImageResource(R.drawable.background)

        viewModel.unidades.observe(this) { unidades ->
            val adapter = ArrayAdapterFactory.create(this, unidades)
            binding.spVerUnidad.adapter = adapter
        }

        viewModel.unidad.observe(this) { unidad ->
            if(unidad != null)
            {
                binding.ivVerUnidadSpriteIdle.setImageResource(unidad.clase.sprite.spriteIdle)
                binding.tvInfoVerUnidad.text = viewModel.infoUnidad()
            }
            else
                binding.ivVerUnidadSpriteIdle.setImageDrawable(null)
        }

        binding.spVerUnidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                val unidadSelecionada = parent?.getItemAtPosition(position) as String

                viewModel.onUnidadSeleccionada(unidadSelecionada)

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}