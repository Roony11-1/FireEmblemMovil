package com.patitofeliz.fireemblem.core.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.interfaces.IUnidadController
import com.patitofeliz.fireemblem.core.model.Crecimientos
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.model.api.ResponseApi
import com.patitofeliz.fireemblem.core.model.api.UnidadApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnidadController : IUnidadController
{
    private val unidades = mutableListOf<Unidad>()
    private var nextId = 0

    override fun agregarUnidad(context: Context, nombre: String, tipoClase: String?)
    {
        if (nombre.isEmpty())
        {
            Toast.makeText(context, "No puedes registrar una Unidad sin nombre", Toast.LENGTH_SHORT).show()
            return
        }

        
        val unidadExistente: Unidad? = this.obtenerUnidadNombre(nombre)

        if (unidadExistente != null)
        {
            Toast.makeText(context, "Ya existe una unidad con el nombre ${nombre}", Toast.LENGTH_SHORT).show()
            return
        }

        val unidad = Manager.unidadFactory.crearUnidad(null, null,nombre, tipoClase)

        unidades.add(unidad)

        if (!Manager.loginService.isLogged)
            Manager.unidadRepositorySqLite.agregarUnidad(unidad)
        else
            saveOnApi(context, unidad)

        Toast.makeText(context, "Alistaste a ${nombre} - Clase: ${tipoClase}", Toast.LENGTH_SHORT).show()
    }

    private fun modelToApiModel(unidad: Unidad): UnidadApi
    {
        return UnidadApi(
            unidad.id ?: 0,
            Manager.loginService.idLogin!!,
            unidad.nombre,
            unidad.clase.nombreClase,
            unidad.nivel.nivel,
            unidad.nivel.experiencia,
            unidad.crecimientos.pv,
            unidad.crecimientos.fue,
            unidad.crecimientos.hab,
            unidad.crecimientos.vel,
            unidad.crecimientos.sue,
            unidad.crecimientos.def,
            unidad.crecimientos.res
        )
    }

    private fun apiModeltoModel(unidadApi: UnidadApi):Unidad
    {
        return Manager.unidadFactory.crearUnidad(unidadApi.id,
            unidadApi.idPropietario,
            unidadApi.nombre,
            unidadApi.clase,
            unidadApi.nivel,
            unidadApi.experiencia,
            Crecimientos(unidadApi.crePv,
                unidadApi.creFue,
                unidadApi.creHab,
                unidadApi.creVel,
                unidadApi.creSue,
                unidadApi.creDef,
                unidadApi.creRes))
    }

    private fun saveOnApi(context: Context, unidad: Unidad)
    {
        val unidadApi = modelToApiModel(unidad)

        RetroFitClient.unidadService.saveUnit(unidadApi)
            .enqueue(object : Callback<ResponseApi<UnidadApi>> {
                override fun onResponse(
                    call: Call<ResponseApi<UnidadApi>>,
                    response: Response<ResponseApi<UnidadApi>>
                ) {
                    /*if (response.isSuccessful)
                        Toast.makeText(context, "Unidad Guardada: ${unidadApi.nombre}", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "No se guardo la unidad: ${response.body()?.message}", Toast.LENGTH_SHORT).show()*/
                }

                override fun onFailure(call: Call<ResponseApi<UnidadApi>>, t: Throwable) {
                    //Toast.makeText(context, "Error al intentar guardar la unidad", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun updateWithApi(context: Context, unidad: Unidad)
    {
        val unidadApi = modelToApiModel(unidad)

        RetroFitClient.unidadService.updateUnit(unidadApi)
            .enqueue(object : Callback<ResponseApi<UnidadApi>> {
                override fun onResponse(
                    call: Call<ResponseApi<UnidadApi>>,
                    response: retrofit2.Response<ResponseApi<UnidadApi>>
                ) {
                    if (response.isSuccessful)
                        Toast.makeText(context, "Unidad actualizada", Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(context, "Error al actualizar unidad", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<ResponseApi<UnidadApi>>, t: Throwable) {
                    Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun agregarUnidadApi(unidadApi: UnidadApi)
    {
        val unidadFromApi = apiModeltoModel(unidadApi)

        unidades.add(unidadFromApi)
    }



    override fun agregarUnidadDB(unidad: Unidad)
    {
        unidades.add(unidad)
    }

    override fun eliminarUnidadid(id: Int): Boolean
    {
        return unidades.removeIf { it.id == id }
    }

    override fun limpiar()
    {
        unidades.clear()
        nextId = 0
    }

    override fun obtenerUnidadId(id: Int): Unidad?
    {
        return unidades.find { it.id == id }
    }

    override fun obtenerUnidadNombre(nombre: String): Unidad?
    {
        return unidades.find { it.nombre == nombre }
    }

    override fun obtenerUnidades(): List<Unidad> = unidades.toList()

    override fun actualizarUnidad(unidad: Unidad): Boolean
    {
        val index = unidades.indexOfFirst { it.id == unidad.id }
        return if (index != -1)
        {
            unidades[index] = unidad

            Manager.unidadRepositorySqLite.actualizarUnidad(unidad)
            true
        }
        else
            false
    }
}