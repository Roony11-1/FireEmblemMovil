package com.patitofeliz.fireemblem.presentation.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.api.PullResponse
import com.patitofeliz.fireemblem.databinding.ActivityPullBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PullActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityPullBinding;
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPullBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idBanner: Int = intent.getIntExtra("idBannerSeleccionado", -1)

        if (idBanner == -1)
        {
            Toast.makeText(this@PullActivity, "No has seleccionado ningun banner", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnPullBanner.setOnClickListener {

            Log.d("GACHA", "=== INIT PULL ===")
            Log.d("GACHA", "bannerId = $idBanner")
            Log.d("GACHA", "usuarioId = ${Manager.loginService.idLogin}")
            Log.d("GACHA", "Llamando a: pull(idBanner, usuarioId)")

            RetroFitClient.gachaService.pull(idBanner, Manager.loginService.idLogin)
                .enqueue(object : Callback<PullResponse> {

                    override fun onResponse(
                        call: Call<PullResponse>,
                        response: Response<PullResponse>
                    ) {

                        Log.d("GACHA", "=== RESPONSE RECIBIDA ===")
                        Log.d("GACHA", "URL: ${call.request().url}")
                        Log.d("GACHA", "Método: ${call.request().method}")

                        // Headers request
                        Log.d("GACHA", "--- Request Headers ---")
                        call.request().headers.forEach {
                            Log.d("GACHA", "${it.first}: ${it.second}")
                        }

                        // Body request (si tiene)
                        Log.d("GACHA", "Request body: ${call.request().body}")

                        // Código HTTP
                        Log.d("GACHA", "Código HTTP: ${response.code()}")

                        // Headers response
                        Log.d("GACHA", "--- Response Headers ---")
                        response.headers().forEach {
                            Log.d("GACHA", "${it.first}: ${it.second}")
                        }

                        // Body
                        val body = response.body()
                        Log.d("GACHA", "Response body: $body")

                        if (!response.isSuccessful || body == null) {
                            val errorBody = response.errorBody()?.string()
                            Log.e("GACHA", "Respuesta inválida o nula")
                            Log.e("GACHA", "Código: ${response.code()}")
                            Log.e("GACHA", "ErrorBody: $errorBody")
                            return
                        }

                        Log.d("GACHA", "→ Tipo: ${body.tipo}")
                        Log.d("GACHA", "→ Nombre: ${body.nombre}")
                        Log.d("GACHA", "→ Clase: ${body.clase}")

                        if (body.tipo == "personaje") {

                            val nombre = body.nombre ?: run {
                                Log.e("GACHA", "body.nombre NULL")
                                return
                            }

                            val clase = body.clase ?: run {
                                Log.e("GACHA", "body.clase NULL")
                                return
                            }

                            Log.d("GACHA", "Revisando si ya tienes al personaje: $nombre")

                            if (Manager.unidadController.obtenerUnidadNombre(nombre) != null) {
                                Log.d("GACHA", "El usuario ya tiene al personaje $nombre")
                                Toast.makeText(this@PullActivity, "Ya tienes a $nombre", Toast.LENGTH_SHORT).show()
                                return
                            }

                            Log.d("GACHA", "Agregando personaje: $nombre ($clase)")
                            Manager.unidadController.agregarUnidad(this@PullActivity, nombre, clase)
                        }
                    }

                    override fun onFailure(call: Call<PullResponse>, t: Throwable) {
                        Log.e("GACHA", "=== ERROR EN LA PETICIÓN ===")
                        Log.e("GACHA", "URL: ${call.request().url}")
                        Log.e("GACHA", "Método: ${call.request().method}")

                        Log.e("GACHA", "Mensaje: ${t.message}", t)
                        t.printStackTrace()
                    }
                })
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}