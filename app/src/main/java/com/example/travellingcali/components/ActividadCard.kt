package com.example.travellingcali.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.travellingcali.data.model.Actividad

@Composable
fun ActividadCard(
    actividad: Actividad,
    esPasada: Boolean,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (esPasada) Color(0xFF1A1A1A) else Color(0xFF222222)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            // Título
            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descripción corta
            Text(
                text = actividad.descripcionCorta,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFCCCCCC)
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Zona
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Zona:",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF00FF7F)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = actividad.zonaId,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Fecha
            Text(
                text = "Finaliza: ${actividad.fechaFin}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFFAAAAAA)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = if (esPasada) "Evento pasado" else "Evento próximo",
                style = MaterialTheme.typography.labelSmall,
                color = if (esPasada) Color(0xFFFF5555) else Color(0xFF00FF7F)
            )
        }
    }
}
