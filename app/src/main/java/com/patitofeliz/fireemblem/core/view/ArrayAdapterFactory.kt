package com.patitofeliz.fireemblem.core.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.patitofeliz.fireemblem.core.model.Unidad

object ArrayAdapterFactory
{
    fun <T> create(context: Context, items: List<T>): ArrayAdapter<String>
    {
        if (items.isEmpty())
            return ArrayAdapter(context, android.R.layout.simple_list_item_1, emptyList())

        val displayList = items.map { item ->
            when (item)
            {
                is com.patitofeliz.fireemblem.core.model.Unidad -> item.nombre
                else -> item.toString()
            }
        }
        return ArrayAdapter(context, android.R.layout.simple_list_item_1, displayList)
    }
}
