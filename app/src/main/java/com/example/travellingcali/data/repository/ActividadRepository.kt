package com.example.travellingcali.data.repository

import com.example.travellingcali.data.model.Actividad
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class ActividadRepository {

    private val db = FirebaseFirestore.getInstance()
    private val actividadesCollection = db.collection("actividades")

    /**
     * onSuccess recibe DOS listas:
     *  - primeras: actividades próximas (activas y no vencidas)
     *  - segundas: actividades pasadas
     */
    fun getActividades(
        onSuccess: (List<Actividad>, List<Actividad>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        actividadesCollection.get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull { doc ->
                    doc.toActividad()
                }

                val (proximas, pasadas) = lista.partition { actividad ->
                    esActividadProxima(actividad)
                }

                onSuccess(proximas, pasadas)
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    // Convierte un documento de Firestore en un objeto Actividad
    private fun DocumentSnapshot.toActividad(): Actividad? {
        val id = this.id

        val titulo = getString("titulo") ?: return null
        val descripcionCorta = getString("descripcionCorta") ?: ""
        val descripcionLarga = getString("descripcionLarga") ?: ""
        val zonaId = getString("zonaId") ?: ""
        val categoriaId = getString("categoriaId") ?: ""
        val direccion = getString("direccion") ?: ""
        val organizador = getString("organizador") ?: ""
        val contacto = getString("contacto") ?: ""
        val fechaInicio = getString("fechaInicio") ?: ""
        val fechaFin = getString("fechaFin") ?: ""
        val costoEstimado = getString("costoEstimado") ?: ""
        val imagenUrl = getString("imagenUrl") ?: ""
        val estado = getBoolean("estado") ?: true

        return Actividad(
            id = id,
            titulo = titulo,
            descripcionCorta = descripcionCorta,
            descripcionLarga = descripcionLarga,
            zonaId = zonaId,
            categoriaId = categoriaId,
            direccion = direccion,
            organizador = organizador,
            contacto = contacto,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin,
            costoEstimado = costoEstimado,
            imagenUrl = imagenUrl,
            estado = estado
        )
    }

    /**
     * Regla:
     *  - Debe tener estado = true
     *  - fechaFin debe ser HOY o una fecha futura
     *  - formato esperado de fechaFin: "YYYY-MM-DD" (ej: "2025-12-20")
     */
    private fun esActividadProxima(actividad: Actividad): Boolean {
        if (!actividad.estado) return false

        val partes = actividad.fechaFin.split("-")
        if (partes.size != 3) return false

        val año = partes[0].toIntOrNull() ?: return false
        val mes = partes[1].toIntOrNull() ?: return false  // 1-12
        val dia = partes[2].toIntOrNull() ?: return false  // 1-31

        val hoy = Calendar.getInstance()

        val fechaFinCal = Calendar.getInstance().apply {
            set(Calendar.YEAR, año)
            set(Calendar.MONTH, mes - 1) // Calendar: enero = 0
            set(Calendar.DAY_OF_MONTH, dia)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // true si fechaFin es HOY o FUTURA
        return !fechaFinCal.before(hoy)
    }
}
