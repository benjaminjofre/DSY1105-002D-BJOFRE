package com.example.variablesyfunciones_3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.variablesyfunciones_3.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val nombreUsuario: String = "Ana"
    var edadUsuario: Int = 20
    var promedioNotas: Double = 6.5
    val esMayorDeEdad: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val saludo = crearSaludo(nombreUsuario, edadUsuario)
        val mayoredad = calcularMayoriaEdad(edadUsuario)
        val mensajeFinal = "$saludo\n¿Es mayor de edad?: $esMayorDeEdad\nPromedio: $promedioNotas"

        mostrarResultado("$saludo ")
    }
        fun crearSaludo(nombre: String, edad: Int ): String {
            return "Hola, soy $nombre tines $edad años"
        }

        fun calcularMayoriaEdad(edad: Int): Boolean {
            return edad >= 18
        }

        fun mostrarResultado(mensaje: String): Unit{
            binding.textView.text = mensaje
        }
    }
