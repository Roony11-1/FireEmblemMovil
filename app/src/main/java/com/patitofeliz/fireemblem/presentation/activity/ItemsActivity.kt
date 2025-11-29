package com.patitofeliz.fireemblem.presentation.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.model.api.BannerItem
import com.patitofeliz.fireemblem.core.view.ArrayAdapterFactory
import com.patitofeliz.fireemblem.databinding.ActivityItemsBinding
import com.patitofeliz.fireemblem.presentation.viewmodel.ItemsViewModel

class ItemsActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityItemsBinding
    private var itemSeleccionado: BannerItem = BannerItem(null, null, null, null, null, null, null)
    private val viewModel: ItemsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var itemAdapter = crearAdapter<BannerItem>()
        viewModel.items.observe(this) {lista ->
            itemAdapter.clear()
            itemAdapter.addAll(lista)
            itemAdapter.notifyDataSetChanged()
        }


        binding.lvActivityItems.adapter = itemAdapter

        binding.lvActivityItems.setOnItemClickListener { parent, _, position, _ ->
            val bannerItemSeleccionado = parent.getItemAtPosition(position) as BannerItem

            if (itemIsNull(bannerItemSeleccionado))
                return@setOnItemClickListener

            mostrarDetalleItem(bannerItemSeleccionado)
        }

        binding.btnAgregar.setOnClickListener {
            mostrarCrearItem(BannerItem(null, "", "", "", "", 0.0, false))
        }

        binding.btnActualizar.setOnClickListener {
            if (!itemIsNull(itemSeleccionado))
                mostrarCrearItem(itemSeleccionado)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun mostrarCrearItem(item: BannerItem)
    {
        var itemModificado: BannerItem = BannerItem(
            item.id,
            item.nombre,
            item.clase,
            item.tipo,
            item.rareza,
            item.probabilidad,
            item.activo)

        val dialogView = layoutInflater.inflate(R.layout.dialog_crear_item, null)

        val etNombre = dialogView.findViewById<EditText>(R.id.etNombreCrearItem)
        val spClases = dialogView.findViewById<Spinner>(R.id.spClaseCrearItem)
        val chkItem = dialogView.findViewById<CheckBox>(R.id.chkActivoItem)
        val spRareza = dialogView.findViewById<Spinner>(R.id.spRareza)
        val etProb = dialogView.findViewById<EditText>(R.id.etProbabilidad)

        etNombre.setText(itemModificado.nombre)
        // Llenamos el spclases con el viewmodel
        viewModel.clases.observe(this) { opciones ->
            val adapter = ArrayAdapterFactory.create(this, opciones)
            spClases.adapter = adapter
        }
        spClases.adapter

        val rarezas: List<String> = listOf("S", "A", "B")

        chkItem.isChecked = itemModificado.activo ?: false

        val adapterRarezas = ArrayAdapterFactory.create(this, rarezas)
        spRareza.adapter = adapterRarezas

        etProb.setText(itemModificado.probabilidad.toString())

        var accion: String = "Guardar"
        var title:String = "Crear"

        if (item.id != null)
        {
            accion = "Actualizar"
            title = "Actualizar"
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("$title Item")
            .setView(dialogView)
            .setPositiveButton(accion, {_, _ ->
                itemModificado.nombre = etNombre.text.toString()
                itemModificado.tipo = "personaje"
                itemModificado.rareza = spRareza.selectedItem.toString()
                itemModificado.clase = spClases.selectedItem.toString()
                itemModificado.probabilidad = etProb.text.toString().toDoubleOrNull() ?: 0.0
                itemModificado.activo = chkItem.isChecked

                if (!itemIsNull(itemModificado))
                    viewModel.updateBannerItem(this, itemModificado.id, itemModificado,
                        onFinish = {
                            viewModel.cargarBannerItems()
                        })
                else
                    viewModel.saveBannerItem(this, itemModificado,
                        onFinish = {
                            viewModel.cargarBannerItems()
                        })
            })
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show()

    }

    private fun mostrarDetalleItem(item: BannerItem)
    {
        val dialogView = layoutInflater.inflate(R.layout.dialog_detalle_item, null)

        val tvNombre = dialogView.findViewById<TextView>(R.id.tvNombreItem)

        tvNombre.setText(item.toString())

        val dialog = AlertDialog.Builder(this)
            .setTitle("Item: "+item.nombre)
            .setView(dialogView)
            .setPositiveButton("Seleccionar", {_, _ ->
                    itemSeleccionado = item
                    Toast.makeText(
                        this,
                        "Has seleccionado el item: ${itemSeleccionado.nombre}",
                        Toast.LENGTH_SHORT
                    ).show()
            })
            .setNegativeButton("Cerrar", null)
            .create()

        dialog.show()
    }

    private fun <T> crearAdapter(items: List<T> = emptyList()): ArrayAdapter<T>
    {
        return ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            items.toMutableList())
    }

    private fun itemIsNull(bannerItem: BannerItem): Boolean
    {
        return bannerItem.id == null
    }
}