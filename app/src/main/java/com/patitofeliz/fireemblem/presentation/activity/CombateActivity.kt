package com.patitofeliz.fireemblem.presentation.activity

import Animacion
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.patitofeliz.fireemblem.Manager
import com.patitofeliz.fireemblem.config.RetroFitClient
import com.patitofeliz.fireemblem.core.model.Crecimientos
import com.patitofeliz.fireemblem.core.model.Unidad
import com.patitofeliz.fireemblem.core.model.api.ResponseApi
import com.patitofeliz.fireemblem.core.model.api.UnidadApi
import com.patitofeliz.fireemblem.databinding.ActivityCombateBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CombateActivity : AppCompatActivity()
{

    private lateinit var ivJugador: ImageView
    private lateinit var ivEnemigo: ImageView
    private lateinit var tvLog: TextView
    private lateinit var tvJugador: TextView
    private lateinit var tvEnemigo: TextView

    private lateinit var enemigoApi: Unidad
    private lateinit var modo: String

    private lateinit var binding: ActivityCombateBinding
    private val combateEngine = Manager.combateEngine

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = ActivityCombateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ivJugador = binding.ivJugador
        ivEnemigo = binding.ivEnemigo
        tvLog = binding.tvLog
        tvJugador = binding.tvJugador
        tvEnemigo = binding.tvEnemigo

        val nombreUnidad = intent.getStringExtra("nombreUnidad")!!
        modo = intent.getStringExtra("modo") ?: "offLine"

        if (modo == "onLine")
        {
            Manager.unidadController.getEnemyFromApi(Manager.loginService.idLogin!!,
                onSuccess = { enemigo ->
                    enemigoApi = enemigo
                    iniciarBatalla(nombreUnidad)
                },
                onError = { error ->
                    error.printStackTrace()
                    Toast.makeText(this@CombateActivity, "Error al buscar enemigo: ${error.message}", Toast.LENGTH_SHORT).show()
                })
        }
        else
            iniciarBatalla(nombreUnidad)
    }

    private suspend fun playAnimacion(anim: Animacion, imageView: ImageView, unidad: Unidad) =
        suspendCancellableCoroutine<Unit> { cont ->

            anim.onUpdateDisplay = { frameResId ->
                imageView.setImageResource(frameResId)
            }

            anim.onComplete = {
                // Lanza una corrutina en el hilo principal para poder usar delay
                CoroutineScope(Dispatchers.Main).launch {
                    delay(250)
                    imageView.setImageResource(unidad.clase.sprite.spriteIdle)
                    if (cont.isActive)
                        cont.resume(Unit) {}
                }
            }

            anim.comenzar()
        }

    private fun actualizarInfo(unidad: Unidad, defensor: Unidad, esJugador: Boolean)
    {
        val precision = combateEngine.calcularPrecision(unidad)
        val evasion = combateEngine.calcularEvasion(defensor)
        val golpeEstimado = (precision - evasion).coerceIn(0, 100)
        val dmg = combateEngine.calcularDmg(unidad, defensor)
        val critico = combateEngine.calcProbCritico(unidad, defensor)
        val canDouble = combateEngine.puedeAtacarDoble(unidad, defensor)
        val cantidadAtaques = if (canDouble) "2X" else "1X"

        val text = buildString {
            append("${unidad.nombre}\n")
            append("PV: ${unidad.estadisticasActuales.pv}\n")
            append("Golpe: $golpeEstimado%\n")
            append("Daño: $dmg\n")
            append("Critico: $critico%\n")
            append("Cantidad de ataques: ${cantidadAtaques}")
        }

        if (esJugador)
            binding.tvJugador.text = text
        else
            binding.tvEnemigo.text = text
    }

    private fun iniciarBatalla(nombre: String)
    {
        val jugador: Unidad = Manager.unidadController.obtenerUnidadNombre(nombre)!!
        var enemigo: Unidad
        if (modo.equals("onLine"))
            enemigo = enemigoApi
        else
        {
            val clases: List<String> = Manager.claseFactory.clasesRegistradas()
            val claseAleatoria = clases.random()
            val nivelJugador: Int = jugador.nivel.nivel
            val aleatorizadorNivel: Int = (-2..3).random()
            enemigo = Manager.unidadFactory.crearUnidad(-1, null,"Enemigo", claseAleatoria, (nivelJugador+aleatorizadorNivel).coerceAtLeast(1))
        }

        // Reiniciar PV
        jugador.estadisticasActuales.pv = jugador.estadisticasBase.pv
        enemigo.estadisticasActuales.pv = enemigo.estadisticasBase.pv

        ivJugador.setImageResource(jugador.clase.sprite.spriteIdle)
        ivEnemigo.setImageResource(enemigo.clase.sprite.spriteIdle)

        CoroutineScope(Dispatchers.Main).launch {
            var turnoJugador = true

            // Función local para manejar el ataque
            suspend fun ejecutarAtaque(atacante: Unidad, defensor: Unidad)
            {
                delay(500L)
                val atacanteView = if (atacante == jugador) ivJugador else ivEnemigo
                val defensorView = if (defensor == jugador) ivJugador else ivEnemigo

                val precision = combateEngine.calcularPrecision(atacante)
                val evasion = combateEngine.calcularEvasion(defensor)
                Log.d("BATALLA", "${atacante.nombre} ataca a ${defensor.nombre}")
                Log.d("BATALLA", "Precision: $precision, Evasion: $evasion")

                // Flag para saber si será crítico antes de animar
                val critChance = combateEngine.calcProbCritico(atacante, defensor)
                val golpe = combateEngine.calcularGolpe(atacante, defensor)
                val esCritico = (0..100).random() <= critChance.coerceAtMost(100)
                val aciertaGolpe = (0..100).random() <= golpe.coerceAtMost(100) || esCritico

                // Seleccionar la animación según si es crítico o no
                val animAtaqueSeleccionada = if (esCritico)
                    atacante.clase.animCritAtaque
                else
                    atacante.clase.animAtaque

                animAtaqueSeleccionada.onFrame = { frameIndex ->
                    // Determina el frame del impacto según el tipo de ataque
                    val frameImpacto = if (esCritico) atacante.clase.frameCritAtaque else atacante.clase.frameAtaque

                    if (frameIndex == frameImpacto)
                    {
                        if (aciertaGolpe)
                        {
                            var dmg = combateEngine.calcularDmg(atacante, defensor)

                            if (esCritico)
                            {
                                dmg *= 3
                                Log.d("BATALLA", "Golpe crítico! Daño: $dmg, PV defensor: ${defensor.estadisticasActuales.pv}")
                                tvLog.append("${atacante.nombre} asesta un golpe crítico a ${defensor.nombre}! Daño: $dmg\n")
                            }
                            else
                            {
                                Log.d("BATALLA", "Golpe! Daño: $dmg, PV defensor: ${defensor.estadisticasActuales.pv}")
                                tvLog.append("${atacante.nombre} asesta un golpe a ${defensor.nombre}! Daño: $dmg\n")
                            }

                            combateEngine.aplicarDmg(defensor, dmg)
                        }
                        else
                        {
                            Log.d("BATALLA", "Golpe fallado!")
                            tvLog.append("${atacante.nombre} falla su ataque a ${defensor.nombre}!\n")

                            defensor.clase.animEsquive?.let { esquiveAnim ->
                                launch {
                                    playAnimacion(esquiveAnim, defensorView, defensor)
                                    defensorView.setImageResource(defensor.clase.sprite.spriteIdle)
                                }
                            }
                        }
                    }
                }


                // Ejecutar animación seleccionada (ataque normal o crítico)
                playAnimacion(animAtaqueSeleccionada, atacanteView, atacante)
            }

            // Bucle de batalla
            while (combateEngine.estaVivo(jugador) && combateEngine.estaVivo(enemigo))
            {
                tvLog.text = ""
                actualizarInfo(jugador, enemigo, true)
                actualizarInfo(enemigo, jugador, false)
                val atacante = if (turnoJugador) jugador else enemigo
                val defensor = if (turnoJugador) enemigo else jugador

                Log.d("BATALLA", "=== Nuevo Turno ===")
                Log.d("BATALLA", "Atacante: ${atacante.nombre} (PV: ${atacante.estadisticasActuales.pv})")
                Log.d("BATALLA", "Defensor: ${defensor.nombre} (PV: ${defensor.estadisticasActuales.pv})")

                // Ataque principal
                ejecutarAtaque(atacante, defensor)
                actualizarInfo(jugador, enemigo, true)
                actualizarInfo(enemigo, jugador, false)

                // Ataque doble si corresponde
                if (combateEngine.puedeAtacarDoble(atacante, defensor) && combateEngine.estaVivo(defensor))
                {
                    Log.d("BATALLA", "${atacante.nombre} puede atacar doble!")
                    ejecutarAtaque(atacante, defensor)
                    actualizarInfo(jugador, enemigo, true)
                    actualizarInfo(enemigo, jugador, false)
                }

                turnoJugador = !turnoJugador
                Log.d("BATALLA", "=== Fin Turno ===")
            }

            val ganador = if (combateEngine.estaVivo(jugador)) jugador else enemigo
            val perdedor = if (ganador == jugador) enemigo else jugador
            Log.d("BATALLA", "¡La batalla ha terminado! Ganador: ${ganador.nombre}")
            tvLog.text = ""
            tvLog.append("¡La batalla ha terminado! Ganador: ${ganador.nombre}\n")

            ganador.agregarExperiencia(75)
            perdedor.agregarExperiencia(10)

            val unidadActualizar = if (ganador == jugador) ganador else perdedor

            if (Manager.loginService.isLogged)
                Manager.unidadController.updateWithApi(this@CombateActivity, unidadActualizar)
            else
                Manager.unidadController.actualizarUnidad(unidadActualizar)

            delay(1500)
            val intent = Intent(this@CombateActivity, PrepararCombateActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
