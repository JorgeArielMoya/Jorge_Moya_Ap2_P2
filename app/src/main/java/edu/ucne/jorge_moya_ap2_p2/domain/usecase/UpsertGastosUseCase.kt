package edu.ucne.jorge_moya_ap2_p2.domain.usecase

import edu.ucne.jorge_moya_ap2_p2.domain.repository.GastosRepository
import javax.inject.Inject

class UpsertGastosUseCase @Inject constructor(
    private val repository: GastosRepository
) {
    operator fun invoke(id : Int?, fecha : String, suplidor : String, ncf : String, itbis : Double, monto : Double) =
        if (id ==0 || id == null)
            repository.createGasto(fecha, suplidor, ncf, itbis, monto)
        else
            repository.updateGasto(id, fecha, suplidor, ncf, itbis, monto)
}