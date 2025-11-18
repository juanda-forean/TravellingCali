package com.example.travellingcali.data.model

data class Actividad(
    val id: String = "",
    val titulo: String = "",
    val descripcionCorta: String = "",
    val descripcionLarga: String = "",
    val zonaId: String = "",
    val categoriaId: String = "",
    val direccion: String = "",
    val organizador: String = "",
    val contacto: String = "",
    val fechaInicio: String = "",
    val fechaFin: String = "",
    val costoEstimado: String = "",
    val imagenUrl: String = "",
    val estado: Boolean = true
)
