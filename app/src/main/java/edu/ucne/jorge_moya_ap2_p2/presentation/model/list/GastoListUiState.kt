package edu.ucne.jorge_moya_ap2_p2.presentation.model.list

import edu.ucne.jorge_moya_ap2_p2.domain.model.Gastos

data class GastoListUiState(
    val isLoading: Boolean = false,
    val gastos: List<Gastos> = emptyList(),
    val error: String? = null
)