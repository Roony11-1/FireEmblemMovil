package com.patitofeliz.fireemblem.presentation.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patitofeliz.fireemblem.R
import com.patitofeliz.fireemblem.core.model.api.BannerItem
import com.patitofeliz.fireemblem.databinding.ActivityItemsBinding
import com.patitofeliz.fireemblem.presentation.viewmodel.ItemsViewModel

class ItemsActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityItemsBinding
    private val viewModel: ItemsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.cargarBannerItems()

        var itemAdapter = crearAdapter<BannerItem>()
        viewModel.items.observe(this) {lista ->
            itemAdapter.clear()
            itemAdapter.addAll(lista)
            itemAdapter.notifyDataSetChanged()
        }

        binding.lvActivityItems.adapter = itemAdapter

        binding

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
}