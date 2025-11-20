package com.example.travellingcali.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.travellingcali.data.model.Actividad

@Composable
fun ActividadCard(
    actividad: Actividad,
    esPasada: Boolean = false
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        tonalElevation = 4.dp,
        color = if (esPasada) Color(0xFF222222) else Color(0xFF333333)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = actividad.titulo,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = actividad.descripcionCorta,
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Zona: ${actividad.zonaId}",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF00FF7F)
            )
        }
    }
}
