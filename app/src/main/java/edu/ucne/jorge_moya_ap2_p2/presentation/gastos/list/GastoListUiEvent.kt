package edu.ucne.jorge_moya_ap2_p2.presentation.gastos.list

sealed interface GastoListUiEvent {
    data object LoadGastos : GastoListUiEvent
}