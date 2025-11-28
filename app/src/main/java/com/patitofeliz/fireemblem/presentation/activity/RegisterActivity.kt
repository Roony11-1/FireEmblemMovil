package com.patitofeliz.fireemblem.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.model.api.Usuario
import com.patitofeliz.fireemblem.databinding.ActivityRegisterBinding
import com.patitofeliz.fireemblem.presentation.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVolverLogin.setOnClickListener {
            finish()
        }

        binding.btnRegistrar.setOnClickListener {
            val usuarioRegistro = Usuario(0,
                binding.etNombre.text.toString(),
                binding.etEmail.text.toString(),
                binding.etContraseA.text.toString(),
                binding.etTelefono.text.toString(),
                binding.etComuna.text.toString(),
                binding.etRegion.text.toString(),
                "usuario",
                "nulo")

            viewModel.registrar(this, usuarioRegistro)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}