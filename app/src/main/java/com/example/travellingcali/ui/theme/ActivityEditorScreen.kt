package com.example.travellingcali.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.travellingcali.data.repository.ActividadRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityEditorScreen(
    navController: NavHostController,
    actividadRepo: ActividadRepository,
    actividadId: String? = null
) {
    var titulo by remember { mutableStateOf("") }
    var descripcionCorta by remember { mutableStateOf("") }
    var descripcionLarga by remember { mutableStateOf("") }
    var zonaId by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    var isSaving by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val esEdicion = !actividadId.isNullOrBlank()

    LaunchedEffect(actividadId) {
        if (esEdicion) {
            isLoading = true
            actividadRepo.getActividadById(
                id = actividadId!!,
                onSuccess = { actividad ->
                    titulo = actividad.titulo
                    descripcionCorta = actividad.descripcionCorta
                    descripcionLarga = actividad.descripcionLarga
                    zonaId = actividad.zonaId
                    direccion = actividad.direccion
                    fechaInicio = actividad.fechaInicio
                    fechaFin = actividad.fechaFin
                    imagenUrl = actividad.imagenUrl
                    isLoading = false
                },
                onError = { e ->
                    errorMessage = e.message ?: "Error al cargar la actividad"
                    isLoading = false
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (esEdicion) "Editar actividad" else "Nueva actividad")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            if (isLoading) {
                Text("Cargando actividad...", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
            }

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcionCorta,
                onValueChange = { descripcionCorta = it },
                label = { Text("Descripción corta") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcionLarga,
                onValueChange = { descripcionLarga = it },
                label = { Text("Descripción larga") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = zonaId,
                onValueChange = { zonaId = it },
                label = { Text("Zona / Barrio") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fechaInicio,
                onValueChange = { fechaInicio = it },
                label = { Text("Fecha inicio (YYYY-MM-DD)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fechaFin,
                onValueChange = { fechaFin = it },
                label = { Text("Fecha fin (YYYY-MM-DD)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = imagenUrl,
                onValueChange = { imagenUrl = it },
                label = { Text("URL de imagen (opcional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    if (titulo.isBlank() || zonaId.isBlank() || fechaFin.isBlank()) {
                        errorMessage = "Título, zona y fecha fin no pueden estar vacíos"
                        return@Button
                    }

                    isSaving = true
                    errorMessage = null

                    if (esEdicion) {
                        actividadRepo.actualizarActividad(
                            id = actividadId!!,
                            titulo = titulo,
                            descripcionCorta = descripcionCorta,
                            descripcionLarga = descripcionLarga,
                            zonaId = zonaId,
                            direccion = direccion,
                            fechaInicio = fechaInicio,
                            fechaFin = fechaFin,
                            imagenUrl = imagenUrl,
                            onSuccess = {
                                isSaving = false
                                navController.popBackStack()
                            },
                            onError = { e ->
                                isSaving = false
                                errorMessage = e.message ?: "Error al actualizar"
                            }
                        )
                    } else {
                        actividadRepo.crearActividad(
                            titulo = titulo,
                            descripcionCorta = descripcionCorta,
                            descripcionLarga = descripcionLarga,
                            zonaId = zonaId,
                            direccion = direccion,
                            fechaInicio = fechaInicio,
                            fechaFin = fechaFin,
                            imagenUrl = imagenUrl,
                            onSuccess = {
                                isSaving = false
                                navController.popBackStack()
                            },
                            onError = { e ->
                                isSaving = false
                                errorMessage = e.message ?: "Error al guardar"
                            }
                        )
                    }
                },
                enabled = !isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(if (isSaving) "Guardando..." else "Guardar actividad")
            }
        }
    }
}



