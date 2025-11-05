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
    fun clon() = Estadisticas(pv, fue, hab, vel, sue, def, res, con)
}