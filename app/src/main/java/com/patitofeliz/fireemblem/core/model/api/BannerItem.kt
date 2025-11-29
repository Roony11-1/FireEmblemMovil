package com.patitofeliz.fireemblem.core.model.api

import com.patitofeliz.fireemblem.Manager

data class BannerItem(val id: Int?,
    val nombre: String?,
    val clase: String?,
    val tipo: String?,
    val rareza: String?,
    val probabilidad: Double?,
    val activo: Boolean?)
{
    override fun toString(): String
    {
        if (nombre == "Sin items")
            return nombre ?: ""

        val n = nombre ?: ""
        val c = clase ?: ""
        val t = tipo ?: ""
        val r = rareza ?: ""
        val p = probabilidad.toString()
        val a = if (activo ?: false) "Activo" else "No activo"

        if (n.isEmpty() && c.isEmpty() && r.isEmpty()) return ""

        val parts = mutableListOf<String>()
        if (n.isNotEmpty()) parts.add("Entidad: $n")
        if (c.isNotEmpty()) parts.add("Clase: $c")
        if (c.isNotEmpty()) parts.add("Tipo: $t")
        if (r.isNotEmpty()) parts.add("Rareza: $r")
        if (p.isNotEmpty()) parts.add("Probabilidad: $p%")
        if (Manager.loginService.tipo == "admin")
            if (a.isNotEmpty()) parts.add("Estado: $a")

        return parts.joinToString("\n")
    }
}
