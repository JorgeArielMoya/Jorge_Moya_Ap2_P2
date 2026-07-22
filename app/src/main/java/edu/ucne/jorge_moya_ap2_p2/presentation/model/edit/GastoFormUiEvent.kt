package edu.ucne.jorge_moya_ap2_p2.presentation.model.edit

sealed interface GastoFormUiEvent {
    data class UpdateFecha(val fecha: String) : GastoFormUiEvent
    data class UpdateSuplidor(val suplidor: String) : GastoFormUiEvent
    data class UpdateNcf(val ncf: String) : GastoFormUiEvent
    data class UpdateItbis(val itbis: String) : GastoFormUiEvent
    data class UpdateMonto(val monto: String) : GastoFormUiEvent
    data object Save : GastoFormUiEvent
    data object ResetSaved : GastoFormUiEvent
}