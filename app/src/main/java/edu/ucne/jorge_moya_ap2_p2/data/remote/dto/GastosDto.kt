package edu.ucne.jorge_moya_ap2_p2.data.remote.dto

import edu.ucne.jorge_moya_ap2_p2.domain.model.Gastos

data class GastosResponse(
    val gastoId : Int,
    val fecha : String,
    val suplidor : String,
    val ncf : String,
    val itbis : Double,
    val monto : Double
){
    fun toDomain () = Gastos(gastoId, fecha, suplidor, ncf, itbis, monto)
}

data class GastosRequest (
    val fecha: String,
    val suplidor: String,
    val ncf: String,
    val itbis: Double,
    val monto: Double
)