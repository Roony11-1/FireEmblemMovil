package com.patitofeliz.fireemblem.core.model

class Crecimientos(
    var pv: Int=0,
    var fue: Int=0,
    var hab: Int=0,
    var vel: Int=0,
    var sue: Int=0,
    var def: Int=0,
    var res: Int=0)
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