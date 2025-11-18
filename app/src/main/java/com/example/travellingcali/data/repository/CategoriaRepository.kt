package com.example.travellingcali.data.repository

import com.example.travellingcali.data.model.Categoria
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class CategoriaRepository {

    private val db = FirebaseFirestore.getInstance()
    private val categoriasCollection = db.collection("categorias")

    // ---------------------------------------------------------
    //  OBTENER TODAS LAS CATEGORÍAS
    // ---------------------------------------------------------
    fun getCategorias(
        onSuccess: (List<Categoria>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        categoriasCollection.get()
            .addOnSuccessListener { result ->
                val lista = result.documents.mapNotNull { doc ->
                    doc.toCategoria()
                }
                onSuccess(lista)
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }

    // ---------------------------------------------------------
    //  CONVERTIR DOCUMENTO → OBJETO 'Categoria'
    // ---------------------------------------------------------
    private fun DocumentSnapshot.toCategoria(): Categoria? {
        val id = this.id
        val nombre = getString("nombre") ?: ""
        val descripcion = getString("descripcion") ?: ""
        val icono = getString("icono") ?: ""

        return Categoria(
            id = id,
            nombre = nombre,
            descripcion = descripcion,
            icono = icono
        )
    }
}

