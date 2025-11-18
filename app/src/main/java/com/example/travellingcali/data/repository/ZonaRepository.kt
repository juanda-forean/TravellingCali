package com.example.travellingcali.data.repository

import com.example.travellingcali.data.model.Zona
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class ZonaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val zonasCollection = db.collection("zonas")

    // Obtener todas las zonas
    fun getZonas(
        onSuccess: (List<Zona>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        zonasCollection
            .get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull { doc ->
                    doc.toZona()
                }
                onSuccess(lista)
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    // --- función auxiliar para convertir un documento en Zona ---
    private fun DocumentSnapshot.toZona(): Zona? {
        val nombre = getString("nombre") ?: return null
        val descripcion = getString("descripcion") ?: ""
        val estado = getBoolean("estado") ?: true
        val orden = getLong("orden")?.toInt() ?: 0

        return Zona(
            id = id,                 // id del documento en Firestore
            nombre = nombre,
            descripcion = descripcion,
            estado = estado,
            orden = orden
        )
    }
}
