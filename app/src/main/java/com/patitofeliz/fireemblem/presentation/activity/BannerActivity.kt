package com.patitofeliz.fireemblem.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
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
    private val isAdmin = Manager.loginService.tipo == "admin"
    private var bannerSeleccionado: Banner = Banner(null, null, null, nombre = "SinSeleccionar", null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vistaPorRol()

        // Lista de Banners
        val adapter = crearAdapter<Banner>()

        binding.lvBanners.adapter = adapter

        viewModel.banners.observe(this) { lista ->
            if (lista.isEmpty() && !isAdmin)
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

        binding.lvBanners.setOnItemClickListener { parent, _, position, _ ->
            val bannerSeleccionado = parent.getItemAtPosition(position) as Banner

            if (bannerSeleccionado.id == null)
                return@setOnItemClickListener

            mostrarDetalleBanner(bannerSeleccionado)
        }

        binding.btnPull.setOnClickListener {
            // Ir al activity pull con los el banner seleccionado
            if (bannerSeleccionado.items?.size == 0)
            {
                Toast.makeText(this, "El Banner no tiene elementos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, PullActivity::class.java)
            intent.putExtra("idBannerSeleccionado", bannerSeleccionado.id)

            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun <T> crearAdapter(items: List<T> = emptyList()): ArrayAdapter<T>
    {
        return ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items.toMutableList())
    }

    private fun cargarBanners()
    {
        viewModel.cargarBannerItemsActivos()
        if (isAdmin)
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
        if (isAdmin)
        {
            binding.btnEditarBanner.visibility = android.view.View.VISIBLE
            binding.btnEditarBanner.setOnClickListener {
                if (bannerSeleccionado.id == null)
                {
                    Toast.makeText(this, "Debes seleccionar un banner primero", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                mostrarDialogoCrearBanner(bannerSeleccionado)
            }
            binding.btnNuevoBanner.visibility = android.view.View.VISIBLE
            binding.btnNuevoBanner.setOnClickListener {
                mostrarDialogoCrearBanner(Banner(null, false, "", "", listOf()))
            }
            binding.btnBorrarBanner.visibility = android.view.View.VISIBLE
            binding.btnBorrarBanner.setOnClickListener {
                if (bannerSeleccionado.id == null)
                {
                    Toast.makeText(this, "Debes seleccionar un banner primero", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewModel.deleteBanner(this, bannerSeleccionado.id,
                    onFinish = {
                        cargarBanners()
                        bannerSeleccionado = Banner(null, null, null, nombre = "SinSeleccionar", null)
                    })
            }
        }
        else
        {
            binding.btnNuevoBanner.visibility = android.view.View.GONE
            binding.btnEditarBanner.visibility = android.view.View.GONE
            binding.btnBorrarBanner.visibility = android.view.View.GONE
        }
    }

    private  fun mostrarDetalleBanner(banner: Banner)
    {
        val dialogView = layoutInflater.inflate(R.layout.dialog_detalle_banner, null)

        val tvDescripcion = dialogView.findViewById<TextView>(R.id.tvDescripcionBanner)
        val lvItems = dialogView.findViewById<ListView>(R.id.lvItemsBanner)

        // Adapter para mostrar los items
        val adapterItems = crearAdapter<BannerItem>()

        lvItems.adapter = adapterItems

        adapterItems.clear()
        if (banner.items?.size!! > 0)
            adapterItems.addAll(banner.items!!)
        else
        {
            adapterItems.add(BannerItem(null, nombre = "Sin items", null, null, null, null, null))
        }


        tvDescripcion.text = banner.descripcion

        val dialog = AlertDialog.Builder(this)
            .setTitle("Banner: "+banner.nombre)
            .setView(dialogView)
            .setPositiveButton("Seleccionar", {_, _ ->
                if (banner.items!!.size > 0 || isAdmin)
                {
                    bannerSeleccionado = banner
                    if (bannerSeleccionado.id != null)
                        Toast.makeText(
                            this,
                            "Has seleccionado el banner: ${banner.nombre}",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                else
                    Toast.makeText(this, "No puedes seleccionar un banner sin items disponibles", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton("Cerrar", null)
            .create()

        dialog.show()
    }

    private fun mostrarDialogoCrearBanner(banner: Banner)
    {
        val bannerActualizado = viewModel.banners.value?.firstOrNull { it.id == banner.id } ?: banner

        val bannerModificado = Banner(
            id = bannerActualizado.id,
            nombre = bannerActualizado.nombre,
            descripcion = bannerActualizado.descripcion,
            activo = bannerActualizado.activo,
            items = bannerActualizado.items
        )

        val dialogView = layoutInflater.inflate(R.layout.dialog_crear_banner, null)

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombre)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcion)
        val chkActivo = dialogView.findViewById<CheckBox>(R.id.chkActivo)
        val lvItems = dialogView.findViewById<ListView>(R.id.lvAddBannerItems)
        val spItems = dialogView.findViewById<Spinner>(R.id.spAddBannerItems)
        val btnAddItem = dialogView.findViewById<Button>(R.id.btnAddBannerItem)

        val adapterItems = crearAdapter<BannerItem>()
        val adapterSpItems = crearAdapter<BannerItem>()

        lvItems.adapter = adapterItems
        spItems.adapter = adapterSpItems
        viewModel.items.observe(this) { itemList ->
            itemList?.let {
                adapterSpItems.clear()
                adapterSpItems.addAll(it)
            }
        }

        adapterItems.clear()
        if (bannerModificado.items?.size!! > 0)
            adapterItems.addAll(bannerModificado.items!!)
        else
        {
            adapterItems.add(BannerItem(null, nombre = "Sin items", null, null, null, null, null))
        }

        lvItems.setOnItemClickListener { _, _, position, _ ->
            val itemSeleccionado = adapterItems.getItem(position)
            if (itemSeleccionado?.id != null)
            {
                AlertDialog.Builder(this)
                    .setTitle("Eliminar item")
                    .setMessage("¿Deseas quitar '${itemSeleccionado.nombre}' de la lista?")
                    .setPositiveButton("Sí") { _, _ ->
                        adapterItems.remove(itemSeleccionado)
                        adapterItems.notifyDataSetChanged()

                        // placeholder
                        if (adapterItems.count == 0)
                        {
                            val placeholder = BannerItem(null, nombre = "Sin items", null, null, null, null, null)
                            adapterItems.add(placeholder)
                        }
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }

        etNombre.setText(bannerModificado.nombre)
        etDescripcion.setText(bannerModificado.descripcion)
        chkActivo.isChecked = bannerModificado.activo ?: false

        btnAddItem.setOnClickListener {
            val itemSeleccionado = spItems.selectedItem as? BannerItem
            if (itemSeleccionado != null)
            {
                var yaExiste = false
                for (i in 0 until adapterItems.count)
                {
                    val item = adapterItems.getItem(i)
                    if (item?.id == itemSeleccionado.id)
                    {
                        yaExiste = true
                        break
                    }
                }

                if (!yaExiste)
                {
                    adapterItems.add(itemSeleccionado)
                    adapterItems.notifyDataSetChanged()
                } else
                    Toast.makeText(this, "El item ya está en la lista", Toast.LENGTH_SHORT).show()
            }
        }

        var accion: String = "Guardar"
        var title:String = "Crear"

        if (banner.id != null)
        {
            accion = "Actualizar"
            title = "Actualizar"
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("$title Banner")
            .setView(dialogView)
            .setPositiveButton(accion) { _, _ ->
                val nombre = etNombre.text.toString()
                val descripcion = etDescripcion.text.toString()
                val activo = chkActivo.isChecked

                bannerModificado.nombre = nombre
                bannerModificado.descripcion = descripcion
                bannerModificado.activo = activo

                if (nombre.isBlank() || descripcion.isBlank())
                {
                    Toast.makeText(this, "El nombre, descripcion es obligatorio", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val itemsSeleccionados = mutableListOf<BannerItem>()
                for (i in 0 until adapterItems.count)
                {
                    val item = adapterItems.getItem(i)
                    if (item?.id != null)
                        itemsSeleccionados.add(item)
                }
                bannerModificado.items = itemsSeleccionados

                if (bannerModificado.id == null)
                    viewModel.guardarBanner(this@BannerActivity, bannerModificado,
                        onFinish = {
                            cargarBanners()
                        })
                else
                    viewModel.updateBanner(this@BannerActivity, bannerModificado.id!!, bannerModificado,
                        onFinish = {
                            cargarBanners()
                        })
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()
    }
}
