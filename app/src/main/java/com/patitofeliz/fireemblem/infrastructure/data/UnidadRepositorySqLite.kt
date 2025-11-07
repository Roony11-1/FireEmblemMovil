package com.patitofeliz.fireemblem.infrastructure.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.core.model.Crecimientos
import com.patitofeliz.fireemblem.core.model.SistemaNivel
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.helper.DBHelper

class UnidadRepositorySqLite(context: Context)
{

    private val dbHelper = DBHelper(context)

    fun agregarUnidad(unidad: Unidad)
    {
        val nombre: String = unidad.nombre
        val clase: String = unidad.clase.nombreClase
        val niveles: SistemaNivel = unidad.nivel
        val crecimiento: Crecimientos = unidad.crecimientos

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COL_NOMBRE, nombre)
            put(DBHelper.COL_CLASE, clase)
            put(DBHelper.COL_NIVEL, niveles.nivel)
            put(DBHelper.COL_EXP, niveles.experiencia)
            put(DBHelper.COL_CREPV, crecimiento.pv)
            put(DBHelper.COL_CREFUE, crecimiento.fue)
            put(DBHelper.COL_CREHAB, crecimiento.hab)
            put(DBHelper.COL_CREVEL, crecimiento.vel)
            put(DBHelper.COL_CRESUE, crecimiento.sue)
            put(DBHelper.COL_CREDEF, crecimiento.def)
            put(DBHelper.COL_CRERES, crecimiento.res)
        }
        db.insert(DBHelper.PERSONAJES, null, values)
        db.close()
    }

    private fun cursorToUnidad(cursor: Cursor): Unidad
    {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_ID))
        val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_NOMBRE))
        val clase = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_CLASE))
        val nivel = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_NIVEL))
        val exp = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_EXP))
        val crecimiento = Crecimientos(
            cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_CREPV)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_CREFUE)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_CREHAB)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_CREVEL)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_CRESUE)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_CREDEF)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COL_CRERES))
        )
        return Manager.unidadFactory.crearUnidad(id, nombre, clase, nivel, exp, crecimiento)
    }

    fun obtenerUnidades(): List<Unidad>
    {
        val db = dbHelper.readableDatabase
        val lista = mutableListOf<Unidad>()
        val cursor: Cursor = db.query(DBHelper.PERSONAJES, null, null, null, null, null, null)
        if (cursor.moveToFirst())
        {
            do
            {
                lista.add(cursorToUnidad(cursor))
            }
            while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }

    fun actualizarUnidad(
        id: Int,
        nombre: String,
        clase: String,
        niveles: SistemaNivel,
        crecimiento: Crecimientos)
    {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DBHelper.COL_NOMBRE, nombre)
            put(DBHelper.COL_CLASE, clase)
            put(DBHelper.COL_NIVEL, niveles.nivel)
            put(DBHelper.COL_EXP, niveles.experiencia)
            put(DBHelper.COL_CREPV, crecimiento.pv)
            put(DBHelper.COL_CREFUE, crecimiento.fue)
            put(DBHelper.COL_CREHAB, crecimiento.hab)
            put(DBHelper.COL_CREVEL, crecimiento.vel)
            put(DBHelper.COL_CRESUE, crecimiento.sue)
            put(DBHelper.COL_CREDEF, crecimiento.def)
            put(DBHelper.COL_CRERES, crecimiento.res)
        }
        db.update(DBHelper.PERSONAJES, values, "${DBHelper.COL_ID} = ?", arrayOf(id.toString()))
        db.close()
    }

    fun eliminarUnidad(id: Int)
    {
        val db = dbHelper.writableDatabase
        db.delete(DBHelper.PERSONAJES, "${DBHelper.COL_ID} = ?", arrayOf(id.toString()))
        db.close()
    }
}