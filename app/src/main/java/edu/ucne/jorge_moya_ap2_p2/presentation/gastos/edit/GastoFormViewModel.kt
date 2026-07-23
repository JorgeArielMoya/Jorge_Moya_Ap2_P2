package edu.ucne.jorge_moya_ap2_p2.presentation.gastos.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jorge_moya_ap2_p2.data.remote.Resource
import edu.ucne.jorge_moya_ap2_p2.domain.model.Gastos
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.GetGastoById
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.UpsertGastosUseCase
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.formatFechaParaApi
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.formatFechaParaUi
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.validateFecha
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.validateItbis
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.validateMonto
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.validateNcf
import edu.ucne.jorge_moya_ap2_p2.domain.usecase.validateSuplidor
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GastoFormViewModel @Inject constructor(
    private val getGastoById: GetGastoById,
    private val upsertGastosUseCase: UpsertGastosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GastoFormUiState())
    val state = _state.asStateFlow()

    fun init(id: Int) {
        _state.update { GastoFormUiState() }
        if (id != 0) loadGasto(id)
    }

    fun onEvent(event: GastoFormUiEvent) {
        when (event) {
            is GastoFormUiEvent.UpdateFecha ->
                _state.update { it.copy(fecha = event.fecha, fechaError = null) }

            is GastoFormUiEvent.UpdateSuplidor->
                _state.update { it.copy(suplidor = event.suplidor, suplidorError = null) }

            is GastoFormUiEvent.UpdateNcf->
                _state.update { it.copy(ncf = event.ncf, ncfError = null) }

            is GastoFormUiEvent.UpdateItbis->
                _state.update { it.copy(itbis = event.itbis, itbisError = null) }

            is GastoFormUiEvent.UpdateMonto->
                _state.update { it.copy(monto = event.monto, montoError = null) }

            GastoFormUiEvent.ResetSaved ->
                _state.update { it.copy(saved = false) }

            GastoFormUiEvent.Save -> onSave()
        }
    }

    private fun loadGasto(id: Int) {
        viewModelScope.launch {
            getGastoById(id).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> result.data?.let { gasto ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                gastoId = gasto.gastoId,
                                fecha = formatFechaParaUi(gasto.fecha),
                                suplidor = gasto.suplidor,
                                ncf = gasto.ncf,
                                itbis = gasto.itbis.toString(),
                                monto = gasto.monto.toString()
                            )
                        }
                    }
                    is Resource.Error -> _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }
    }

    private fun onSave() {
        val fechaResult = validateFecha(state.value.fecha)
        val suplidorResult = validateSuplidor(state.value.suplidor)
        val ncfResult = validateNcf(state.value.ncf)
        val itbisResult = validateItbis(state.value.itbis)
        val montoResult = validateMonto(state.value.monto)

        if (!fechaResult.isValid || !suplidorResult.isValid ||
            !ncfResult.isValid || !itbisResult.isValid || !montoResult.isValid) {
            _state.update {
                it.copy(
                    fechaError = fechaResult.error,
                    suplidorError = suplidorResult.error,
                    ncfError = ncfResult.error,
                    itbisError = itbisResult.error,
                    montoError = montoResult.error
                )
            }
            return
        }

        viewModelScope.launch {
            val current = _state.value
            upsertGastosUseCase(current.gastoId, formatFechaParaApi(current.fecha), current.suplidor, current.ncf, current.itbis.toDouble(), current.monto.toDouble())
                .collect { result: Resource<Gastos> ->
                    when (result) {
                        is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                        is Resource.Success -> _state.update {
                            it.copy(isLoading = false, saved = true)
                        }
                        is Resource.Error -> _state.update {
                            it.copy(isLoading = false, error = result.message)
                        }
                    }
                }
        }
    }
}