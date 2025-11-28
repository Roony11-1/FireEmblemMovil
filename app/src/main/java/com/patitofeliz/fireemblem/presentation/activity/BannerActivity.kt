package com.patitofeliz.fireemblem.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.model.api.Banner
import com.patitofeliz.fireemblem.core.model.api.BannerItem
import com.patitofeliz.fireemblem.databinding.ActivityBannerBinding
import com.patitofeliz.fireemblem.presentation.viewmodel.BannerViewModel
import kotlin.getValue
class BannerActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityBannerBinding
    private val viewModel: BannerViewModel by viewModels()
    private val esAdmin = Manager.loginService.tipo == "admin"

    private var idBannerSeleccionado: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vistaPorRol()

        // Lista de Banners
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf<Banner>()
        )
        binding.lvBanners.adapter = adapter

        viewModel.banners.observe(this) { lista ->
            if (lista.isEmpty() && !esAdmin)
            {
                Toast.makeText(this, "No hay Banners en el sistema", Toast.LENGTH_SHORT).show()
                finish()
            } else
            {
                adapter.clear()
                adapter.addAll(lista)
                adapter.notifyDataSetChanged()
            }
        }

        binding.lvBanners.setOnItemClickListener { parent, view, position, _ ->
            val bannerSeleccionado: Banner = parent.getItemAtPosition(position) as Banner

            if (bannerSeleccionado == null)
                    return@setOnItemClickListener

            mostrarDetalleBanner(bannerSeleccionado)

            Toast.makeText(this,
                "Has seleccionado el banner con id: $idBannerSeleccionado",
                Toast.LENGTH_SHORT).show()
        }

        binding.btnPull.setOnClickListener {
            // Ir al activity pull con los el banner seleccionado
            val intent = Intent(this, PullActivity::class.java)
            intent.putExtra("idBannerSeleccionado", idBannerSeleccionado)

            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun cargarBanners()
    {
        if (esAdmin)
        {
            viewModel.cargarBanners()
        }
        else
        {
            viewModel.cargarBannersActivos()
        }
    }

    private fun vistaPorRol()
    {
        cargarBanners()
        if (esAdmin)
        {
            binding.btnNuevoBanner.visibility = android.view.View.VISIBLE
            binding.btnNuevoBanner.setOnClickListener {
                mostrarDialogoCrearBanner()
            }
        }
        else
        {
                binding.btnNuevoBanner.visibility = android.view.View.GONE
        }
    }

    private  fun mostrarDetalleBanner(banner: Banner)
    {
        val dialogView = layoutInflater.inflate(R.layout.dialog_detalle_banner, null)

        val tvDescripcion = dialogView.findViewById<TextView>(R.id.tvDescripcionBanner)
        val lvItems = dialogView.findViewById<ListView>(R.id.lvItemsBanner)

        // Adapter para mostrar los items
        val adapterItems = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf<BannerItem>()
        )

        lvItems.adapter = adapterItems

        adapterItems.clear()
        if (banner.items.size > 0)
            adapterItems.addAll(banner.items)
        else
        {
            adapterItems.add(BannerItem(null, nombre = "Sin items", null, null, null, null))
        }


        tvDescripcion.text = banner.descripcion

        val dialog = AlertDialog.Builder(this)
            .setTitle("Banner: "+banner.nombre)
            .setView(dialogView)
            .setNegativeButton("Cerrar", null)
            .create()

        dialog.show()
    }

    private fun mostrarDialogoCrearBanner()
    {
        val dialogView = layoutInflater.inflate(R.layout.dialog_crear_banner, null)

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombre)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcion)
        val chkActivo = dialogView.findViewById<CheckBox>(R.id.chkActivo)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Crear Banner")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->

                val nombre = etNombre.text.toString()
                val descripcion = etDescripcion.text.toString()
                val activo = chkActivo.isChecked

                if (nombre.isBlank())
                {
                    Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val banner = Banner(
                    id = null,
                    nombre = nombre,
                    descripcion = descripcion,
                    activo = activo,
                    items = listOf())

                viewModel.guardarBanner(this@BannerActivity, banner,
                    onFinish = {
                        cargarBanners()
                    })
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
}