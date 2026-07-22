package edu.ucne.jorge_moya_ap2_p2.presentation.model.list

sealed interface GastoListUiEvent {
    data object LoadGastos : GastoListUiEvent
}