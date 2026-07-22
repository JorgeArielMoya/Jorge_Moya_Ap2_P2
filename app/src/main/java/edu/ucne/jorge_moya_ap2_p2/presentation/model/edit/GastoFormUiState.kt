package edu.ucne.jorge_moya_ap2_p2.presentation.model.edit

data class GastoFormUiState(
    val isLoading: Boolean = false,
    val gastoId: Int? = null,
    val fecha: String = "",
    val suplidor: String = "",
    val ncf: String = "",
    val itbis: String = "",
    val monto: String = "",
    val fechaError: String? = null,
    val suplidorError: String? = null,
    val ncfError: String? = null,
    val itbisError: String? = null,
    val montoError: String? = null,
    val error: String? = null,
    val saved: Boolean = false
)