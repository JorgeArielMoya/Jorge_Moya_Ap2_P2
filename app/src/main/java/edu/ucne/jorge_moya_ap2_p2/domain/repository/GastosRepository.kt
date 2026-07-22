package edu.ucne.jorge_moya_ap2_p2.domain.repository

import edu.ucne.jorge_moya_ap2_p2.data.remote.Resource
import edu.ucne.jorge_moya_ap2_p2.domain.model.Gastos
import kotlinx.coroutines.flow.Flow

interface GastosRepository {
    fun getGastos () : Flow<Resource<List<Gastos>>>
    fun getGasto (id : Int) : Flow<Resource<Gastos>>
    fun createGasto (fecha : String, suplidor : String, ncf : String, itbis : Double, monto : Double) : Flow<Resource<Gastos>>
    fun updateGasto (id : Int, fecha: String, suplidor : String, ncf: String, itbis: Double, monto: Double) : Flow<Resource<Gastos>>
}