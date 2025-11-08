package com.patitofeliz.fireemblem.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.view.ArrayAdapterFactory
import com.patitofeliz.fireemblem.databinding.ActivityPrepararCombateBinding
import com.patitofeliz.fireemblem.presentation.viewmodel.PrepararCombateViewModel

class PrepararCombateActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityPrepararCombateBinding
    private val viewModel: PrepararCombateViewModel by viewModels()

    private lateinit var unidadSeleccionadaNombre:String

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPrepararCombateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.unidades.observe(this) { unidades ->
            val adapter = ArrayAdapterFactory.create(this, unidades)
            binding.spPrepararCombateUnidad.adapter = adapter
        }

        viewModel.unidad.observe(this) { unidad ->
            if(unidad != null)
            {
                binding.ivPrepararCombatePreview.setImageResource(unidad.clase.sprite.spriteIdle)
                binding.tvInfo.text = viewModel.infoUnidad()
            }
            else
                binding.ivPrepararCombatePreview.setImageDrawable(null)
        }

        binding.spPrepararCombateUnidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                unidadSeleccionadaNombre = parent?.getItemAtPosition(position) as String

                viewModel.onUnidadSeleccionada(unidadSeleccionadaNombre)

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnIrBatalla.setOnClickListener {
            val intent = Intent(this@PrepararCombateActivity, CombateActivity::class.java)
            intent.putExtra("nombreUnidad", unidadSeleccionadaNombre)
            intent.putExtra("modo", "offLine")
            startActivity(intent)
            finish()
        }

        binding.btnEnLinea.setOnClickListener {
            if (!Manager.loginService.isLogged)
                Toast.makeText(this, "Solo se puede con una sesión activa!", Toast.LENGTH_SHORT).show()
            else
            {
                val intent = Intent(this@PrepararCombateActivity, CombateActivity::class.java)
                intent.putExtra("nombreUnidad", unidadSeleccionadaNombre)
                intent.putExtra("modo", "onLine")
                startActivity(intent)
                finish()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}