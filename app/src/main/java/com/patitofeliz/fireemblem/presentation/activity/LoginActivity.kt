package com.patitofeliz.fireemblem.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.LoginRequest
import com.patitofeliz.fireemblem.core.model.api.ResponseApi
import com.patitofeliz.fireemblem.core.model.api.UnidadApi
import com.patitofeliz.fireemblem.core.model.api.Usuario
import com.patitofeliz.fireemblem.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Validación rápida antes de enviar
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa email y contraseña", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            RetroFitClient.usuarioService.loginUsuario(LoginRequest(email, password))
                .enqueue(object : Callback<ResponseApi<Usuario>> {
                    override fun onResponse(
                        call: Call<ResponseApi<Usuario>>,
                        response: retrofit2.Response<ResponseApi<Usuario>>
                    ) {
                        if (response.isSuccessful)
                        {
                            val responseApi = response.body()

                            if (responseApi == null)
                            {
                                Toast.makeText(this@LoginActivity, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show()
                                return
                            }

                            if (responseApi.status == "200")
                            {
                                val usuario = responseApi.entity

                                if (usuario == null)
                                    return

                                Manager.loginService.idLogin = usuario.id
                                Manager.loginService.isLogged = true

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Logeado correctamente - ID: ${usuario.id}",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Ir a la actividad principal
                                val intent = Intent(this@LoginActivity, PrincipalActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else
                            {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Logeo incorrecto: ${responseApi.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        else
                            Toast.makeText(this@LoginActivity, "Error al intentar logear", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<ResponseApi<Usuario>>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)

            startActivity(intent)
        }

        binding.btnInvitado.setOnClickListener {
            val intent = Intent(this@LoginActivity, PrincipalActivity::class.java)
            Manager.loginService.isLogged = false
            Manager.unidadController.limpiar()
            startActivity(intent)
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}