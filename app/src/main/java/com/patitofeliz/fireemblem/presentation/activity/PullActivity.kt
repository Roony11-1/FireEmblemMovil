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

            RetroFitClient.gachaService.pull(idBanner, Manager.loginService.idLogin)
                .enqueue(object : Callback<PullResponse>
                {
                    override fun onResponse(
                        call: Call<PullResponse>,
                        response: Response<PullResponse>)
                    {
                        val body = response.body()

                        if (!response.isSuccessful || body == null)
                        {
                            Log.e("GACHA", "Respuesta inválida o nula: ${response.code()}")
                            return
                        }

                        Log.d("GACHA", "→ ${body.nombre}")

                        if (body.tipo == "personaje")
                        {
                            val nombre = body.nombre ?: return
                            val clase = body.clase ?: return

                            if (Manager.unidadController.obtenerUnidadNombre(nombre) != null)
                            {
                                Toast.makeText(this@PullActivity, "Ya tienes a $nombre", Toast.LENGTH_SHORT).show()
                                return
                            }

                            Manager.unidadController.agregarUnidad(this@PullActivity, nombre, clase)
                        }
                    }

                    override fun onFailure(call: Call<PullResponse>, t: Throwable)
                    {
                        Log.e("GACHA", "Error pull", t)
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