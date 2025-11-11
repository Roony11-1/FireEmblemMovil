package com.patitofeliz.fireemblem.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.core.model.api.ResponseApi
import com.patitofeliz.fireemblem.core.model.api.Usuario
import retrofit2.Call
import retrofit2.Callback
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.config.RetroFitClient

class RegisterActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        fun limpiarETText(et: EditText): String
        {
            return et.text.toString().trim()
        }

        var irVentanaLogin = Intent(this, PrincipalActivity::class.java)

        // Botones quier ocomitear ctm apooo
        var btnVISesion: Button = findViewById(R.id.btnVolverLogin)
        var btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        // EditText
        var etNombreUsuario: EditText = findViewById(R.id.etNombre)
        var etEmail: EditText = findViewById(R.id.etEmail)
        var etConfirmarEmail: EditText = findViewById(R.id.etConfirmarEmail)
        var etContraseña: EditText = findViewById(R.id.etContraseña)
        var etConfirmarContraseña: EditText = findViewById(R.id.etConfirmarContraseña)
        var etTelefono: EditText = findViewById(R.id.etTelefono)
        var region: EditText = findViewById(R.id.etRegion)
        var comuna: EditText = findViewById(R.id.etComuna)

        btnRegistrar.setOnClickListener {
            val nombreUsuario: String = limpiarETText(etNombreUsuario)
            val email: String = limpiarETText(etEmail)
            val confirmarEmail: String = limpiarETText(etConfirmarEmail)
            val contraseña: String = limpiarETText(etContraseña)
            val confirmarContraseña: String = limpiarETText(etConfirmarContraseña)
            val telefono: String = limpiarETText(etTelefono)
            val regionS: String = limpiarETText(region)
            val comunaS:String = limpiarETText(comuna)

            if (nombreUsuario.isEmpty() || email.isEmpty() || confirmarEmail.isEmpty() || contraseña.isEmpty()
                || confirmarContraseña.isEmpty() || telefono.isEmpty())
            {
                Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.equals(confirmarEmail))
            {
                Toast.makeText(this, "Los correos no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!contraseña.equals(confirmarContraseña))
            {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuario: Usuario = Usuario(null, nombreUsuario, email, contraseña, telefono, comunaS, regionS, "usuario", "none")

            RetroFitClient.usuarioService.registrarUsuario(usuario)
                .enqueue(object : Callback<ResponseApi<Usuario>> {
                    override fun onResponse(
                        call: Call<ResponseApi<Usuario>>,
                        response: retrofit2.Response<ResponseApi<Usuario>>
                    ) {
                        if (response.isSuccessful) {
                            val res = response.body()
                            Toast.makeText(this@RegisterActivity, res?.message, Toast.LENGTH_SHORT).show()
                            if (res?.success == true)
                            {
                                startActivity(irVentanaLogin)
                                finish()
                            }
                        } else {
                            Toast.makeText(this@RegisterActivity, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseApi<Usuario>>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })



        }

        btnVISesion.setOnClickListener {
            startActivity(irVentanaLogin)
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}