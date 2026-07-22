package edu.ucne.jorge_moya_ap2_p2.domain.usecase

import edu.ucne.jorge_moya_ap2_p2.domain.repository.GastosRepository
import javax.inject.Inject

class GetGastosUseCase @Inject constructor(
    private val repository: GastosRepository
) {
    operator fun invoke() = repository.getGastos()
}