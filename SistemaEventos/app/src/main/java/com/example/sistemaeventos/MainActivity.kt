package com.example.sistemaeventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sistemaeventos.ui.theme.SistemaEventosTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

open class Entrada(
    val id: Int,
    val precio: Double
) {
//
    open fun mostrarDetalle() {
        println("ID: $id | Precio: $$precio")
    }
}
class EntradaGeneral(
    id: Int,
    precio: Double,
    val zona: String
) : Entrada(id, precio) {

    override fun mostrarDetalle() {
        println("[ENTRADA GENERAL] ID: $id | Zona: $zona | Precio: $$precio")
    }
}
class EntradaVIP(
    id: Int,
    precio: Double,
    val beneficiosExtra: String
) : Entrada(id, precio) {

    override fun mostrarDetalle() {
        println("[ENTRADA VIP] ID: $id | Beneficios: $beneficiosExtra | Precio: $$precio")
    }
}


sealed class EstadoValidacion {
    object Validando : EstadoValidacion()
    data class Valida(val entrada: Entrada) : EstadoValidacion()
    data class NoValida(val mensajeError: String) : EstadoValidacion()
}


suspend fun validarEntrada(id: Int, listaEntradas: List<Entrada>): EstadoValidacion {
    println("\n[SISTEMA] Iniciando validación para la entrada ID: $id...")

    delay(2000)

    // Buscar si existe la entrada por su ID
    val entradaEncontrada = listaEntradas.find { it.id == id }

    return if (entradaEncontrada != null) {
        EstadoValidacion.Valida(entradaEncontrada)
    } else {
        EstadoValidacion.NoValida("No se encontró ninguna entrada registrada con el ID $id.")
    }
}

// ==========================================
// PARTE 2 Y 3: Función Main y Ejecución
// ==========================================

fun main() = runBlocking {
    println("=== SISTEMA DE GESTIÓN DE ENTRADAS ===")

    // Parte 2: Creación de la Colección de Datos (List)
    val entradasVendidas: List<Entrada> = listOf(
        EntradaGeneral(id = 101, precio = 25000.0, zona = "Platea Alta"),
        EntradaVIP(id = 102, precio = 60000.0, beneficiosExtra = "Acceso a Lounge + Bebida Gratis"),
        EntradaGeneral(id = 103, precio = 30000.0, zona = "Cancha General"),
        EntradaVIP(id = 104, precio = 75000.0, beneficiosExtra = "Meet & Greet + Estacionamiento Exclusivo"),
        EntradaGeneral(id = 105, precio = 25000.0, zona = "Platea Baja")
    )

    // Mostrar detalle de todas las entradas (Polimorfismo en acción)
    println("\n--- Detalle de Entradas Vendidas ---")
    entradasVendidas.forEach { entrada ->
        entrada.mostrarDetalle()
    }

    // Análisis de la Colección
    println("\n--- Análisis de Datos ---")

    // 1. Calcular el ingreso total acumulado
    val ingresoTotal = entradasVendidas.sumOf { it.precio }
    println("Ingreso total generado: $$ingresoTotal")

    // 2. Filtrar y contar la cantidad de entradas VIP
    val cantidadVip = entradasVendidas.count { it is EntradaVIP }
    println("Cantidad de entradas VIP vendidas: $cantidadVip")

    // Parte 3: Validación Asíncrona con Corrutinas
    println("\n--- Pruebas de Validación Asíncrona ---")

    // Prueba 1: Buscar una entrada existente (ID 102)
    val resultado1 = validarEntrada(102, entradasVendidas)
    procesarResultadoValidacion(resultado1)

    // Prueba 2: Buscar una entrada inexistente (ID 999)
    val resultado2 = validarEntrada(999, entradasVendidas)
    procesarResultadoValidacion(resultado2)
}

// Función auxiliar con expresión 'when' para manejar la Sealed Class
fun procesarResultadoValidacion(estado: EstadoValidacion) {
    when (estado) {
        is EstadoValidacion.Validando -> {
            println("Estado: Validando la entrada en el servidor...")
        }
        is EstadoValidacion.Valida -> {
            println("✅ RESULTADO: Entrada VÁLIDA.")
            print("   Detalle: ")
            estado.entrada.mostrarDetalle()
        }
        is EstadoValidacion.NoValida -> {
            println("❌ RESULTADO: Entrada NO VÁLIDA. Error: ${estado.mensajeError}")
        }
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SistemaEventosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SistemaEventosTheme {
        Greeting("Android")
    }
}