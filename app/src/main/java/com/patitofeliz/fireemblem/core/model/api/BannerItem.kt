package com.patitofeliz.fireemblem.core.model.api

data class BannerItem(val id: Int?,
    val nombre: String?,
    val clase: String?,
    val tipo: String?,
    val rareza: String?,
    val probabilidad: Double?)
{
    override fun toString(): String
    {
        if (nombre == "Sin items")
            return nombre ?: ""

        val n = nombre ?: ""
        val c = clase ?: ""
        val r = rareza ?: ""
        val p = probabilidad.toString()

        if (n.isEmpty() && c.isEmpty() && r.isEmpty()) return ""

        val parts = mutableListOf<String>()
        if (n.isNotEmpty()) parts.add("Personaje: $n")
        if (c.isNotEmpty()) parts.add("Clase: $c")
        if (r.isNotEmpty()) parts.add("Rareza: $r")
        if (p.isNotEmpty()) parts.add("Probabilidad: $p%")

        return parts.joinToString("\n")
    }
}
