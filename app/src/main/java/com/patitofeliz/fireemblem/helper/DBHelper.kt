package com.patitofeliz.fireemblem.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION)
{

    companion object
    {
        const val DATABASE_NAME = "FireEmblemDB.db"
        private const val DATABASE_VERSION = 1
        // Tabla Personaje
        const val PERSONAJES = "personajes"
        const val COL_ID = "id"
        const val COL_NOMBRE = "nombre"
        const val COL_CLASE = "clase"
        const val COL_NIVEL = "nivel"
        const val COL_EXP = "experiencia"
        const val COL_CREPV = "crepv"
        const val COL_CREFUE = "crefue"
        const val COL_CREHAB = "crehab"
        const val COL_CREVEL = "crevel"
        const val COL_CRESUE = "cresue"
        const val COL_CREDEF = "credef"
        const val COL_CRERES = "creres"
    }

    override fun onCreate(db: SQLiteDatabase)
    {
        val createTable = """
            CREATE TABLE $PERSONAJES (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT NOT NULL,
                $COL_CLASE TEXT NOT NULL,
                $COL_NIVEL INTEGER NOT NULL,
                $COL_EXP INTEGER NOT NULL,
                $COL_CREPV INTEGER NOT NULL,
                $COL_CREFUE INTEGER NOT NULL,
                $COL_CREHAB INTEGER NOT NULL,
                $COL_CREVEL INTEGER NOT NULL,
                $COL_CRESUE INTEGER NOT NULL,
                $COL_CREDEF INTEGER NOT NULL,
                $COL_CRERES INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
    {
        db.execSQL("DROP TABLE IF EXISTS $PERSONAJES")
        onCreate(db)
    }
}