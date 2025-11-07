package com.patitofeliz.fireemblem.infrastructure.factory

import com.patitofeliz.fireemblem.core.interfaces.ICrecimientoFactory
import com.patitofeliz.fireemblem.core.model.Crecimientos


class CrecimientoFactory : ICrecimientoFactory
{
    override fun generarCrecimientos(clase: String?): Crecimientos
    {
        return when(clase)
        {
            "Mirmidón" -> Crecimientos(
                pv = (60..80).random(),
                fue = (15..40).random(),
                hab = (40..60).random(),
                vel = (60..75).random(),
                sue = (30..45).random(),
                def = (5..20).random(),
                res = (10..25).random()
            )
            "Lord (Roy)" -> Crecimientos(
                pv = (75..85).random(),
                fue = (30..45).random(),
                hab = (40..55).random(),
                vel = (30..40).random(),
                sue = (55..65).random(),
                def = (20..30).random(),
                res = (25..35).random()
            )
            else -> Crecimientos(
                pv = 0,
                fue = 0,
                hab = 0,
                vel = 0,
                sue = 0,
                def = 0,
                res = 0
            )
        }
    }
}