package com.patitofeliz.fireemblem.core.model

class Crecimientos(
    var pv: Int=500,
    var fue: Int=500,
    var hab: Int=500,
    var vel: Int=500,
    var sue: Int=500,
    var def: Int=500,
    var res: Int=500)
{
    var crecimientos: Map<String, Int> = mapOf(
        "pv" to pv,
        "fue" to fue,
        "hab" to hab,
        "vel" to vel,
        "sue" to sue,
        "def" to def,
        "res" to res
    )
}