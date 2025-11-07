package com.patitofeliz.fireemblem.core.model

class SistemaNivel(var nivel:Int = 1, var experiencia:Int = 0)
{
    fun agregarExperiencia(exp:Int):Boolean
    {
        this.experiencia += exp
        return this.subirNivel()
    }

    private fun subirNivel(): Boolean
    {
        if (this.nivel >= 20)
            return false

        if (this.experiencia >= 100)
        {
            this.experiencia -= 100
            this.nivel++
            return true
        }
        return false
    }
}