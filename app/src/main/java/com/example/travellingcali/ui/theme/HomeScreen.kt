package com.example.travellingcali

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.travellingcali.components.ActividadCard
import com.example.travellingcali.data.model.Actividad
import com.example.travellingcali.data.repository.ActividadRepository


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(actividadRepo: ActividadRepository, navController: NavHostController) {

    // Estado base
    var proximas by remember { mutableStateOf<List<Actividad>>(emptyList()) }
    var pasadas by remember { mutableStateOf<List<Actividad>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var zonaSeleccionada by remember { mutableStateOf("Todas") }


    // Filtro de barrio
    val barrios = listOf(
        "Todos",
        "San Antonio",
        "Granada",
        "San Fernando",
        "Ciudad Jardín",
        "El Peñón",
        "Alameda"
    )

    var barrioSeleccionado by remember { mutableStateOf("Todos") }

    LaunchedEffect(Unit) {
        actividadRepo.getActividades(
            onSuccess = { listaProximas, listaPasadas ->
                proximas = listaProximas
                pasadas = listaPasadas
                isLoading = false
            },
            onError = { e ->
                errorMessage = e.message ?: "Error desconocido"
                isLoading = false
            }
        )
    }

    // Aplico filtro por barrio (usa zonaNombre, cámbialo si tu propiedad se llama diferente)
    fun coincideBarrio(actividad: Actividad, filtro: String): Boolean {
        // Si el filtro es "Todas", siempre coincide
        if (filtro == "Todas") return true

        val zonaActividad = actividad.zonaId.trim().lowercase()
        val filtroNormalizado = filtro.trim().lowercase()

        // Coincidencia exacta o que la zona contenga el nombre del barrio
        return zonaActividad == filtroNormalizado || zonaActividad.contains(filtroNormalizado)
    }



    val proximasFiltradas = proximas.filter { actividad ->
        coincideBarrio(actividad, zonaSeleccionada)
    }

    val pasadasFiltradas = pasadas.filter { actividad ->
        coincideBarrio(actividad, zonaSeleccionada)
    }






    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Travelling Cali") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("editor")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Nueva actividad"
                        )
                    }
                }
            )

        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                errorMessage != null -> {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // ===== Fila de filtros por barrios =====
                        item {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                barrios.forEach { barrio ->
                                    FilterChip(
                                        selected = barrioSeleccionado == barrio,
                                        onClick = { barrioSeleccionado = barrio },
                                        label = {
                                            Text(text = barrio)
                                        }
                                    )
                                }
                            }
                        }

                        // ===== Próximos eventos =====
                        if (proximasFiltradas.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Próximos eventos",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
                                )
                            }

                            items(proximasFiltradas) { actividad ->
                                ActividadCard(actividad = actividad, esPasada = false)
                            }

                            item { Spacer(modifier = Modifier.padding(vertical = 4.dp)) }
                        }

                        // ===== Eventos pasados =====
                        if (pasadasFiltradas.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Eventos pasados",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 8.dp
                                    )
                                )
                            }

                            items(pasadasFiltradas) { actividad ->
                                ActividadCard(actividad = actividad, esPasada = true)
                            }
                        }
                    }
                }
            }
        }
    }
}

