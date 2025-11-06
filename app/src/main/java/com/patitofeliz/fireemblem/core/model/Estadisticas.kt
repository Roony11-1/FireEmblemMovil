package com.patitofeliz.fireemblem.core.model

class Estadisticas(
    var pv: Int,
    var fue: Int,
    var hab: Int,
    var vel: Int,
    var sue: Int,
    var def: Int,
    var res: Int,
    var con: Int)
{
    var stats: MutableMap<String, Int> = mutableMapOf(
    "pv" to pv,
    "fue" to fue,
    "hab" to hab,
    "vel" to vel,
    "sue" to sue,
    "def" to def,
    "res" to res)

    fun actualizarStats()
    {
        this.pv = stats["pv"] ?: this.pv
        this.fue = stats["fue"] ?: this.fue
        this.hab = stats["hab"] ?: this.hab
        this.vel = stats["vel"] ?: this.vel
        this.sue = stats["sue"] ?: this.sue
        this.def = stats["def"] ?: this.def
        this.res = stats["res"] ?: this.res
    }

    fun clon() = Estadisticas(pv, fue, hab, vel, sue, def, res, con)
}