package com.example.travellingcali.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.travellingcali.data.model.Actividad

@Composable
fun ActividadCard(
    actividad: Actividad,
    esPasada: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Título grande
            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Barrio / zona en verde
            Text(
                text = actividad.zonaId ?: "Zona sin definir",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Fechas
            Text(
                text = "Inicio: ${actividad.fechaInicio}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Fin: ${actividad.fechaFin}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Descripción corta
            if (!actividad.descripcionLarga.isNullOrBlank()) {
                Text(
                    text = actividad.descripcionLarga,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Etiqueta de estado
            Text(
                text = if (esPasada) "Estado: evento pasado"
                else "Estado: evento próximo",
                style = MaterialTheme.typography.labelSmall,
                color = if (esPasada)
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                else
                    MaterialTheme.colorScheme.primary
            )
        }
    }
}
