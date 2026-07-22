package edu.ucne.jorge_moya_ap2_p2.presentation.model.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GastoFormScreen(
    viewModel: GastoFormViewModel = hiltViewModel(),
    gastoId: Int,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(gastoId) {
        viewModel.init(gastoId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if (state.saved) {
            viewModel.onEvent(GastoFormUiEvent.ResetSaved)
            onNavigateBack()
        }
    }

    FormGastoBodyScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormGastoBodyScreen(
    state: GastoFormUiState,
    onEvent: (GastoFormUiEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (state.gastoId == null) "Nuevo Gasto" else "Editar Gasto")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            state.error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = state.fecha,
                onValueChange = { onEvent(GastoFormUiEvent.UpdateFecha(it)) },
                label = { Text("Fecha") },
                isError = state.fechaError != null,
                supportingText = {
                    state.fechaError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.suplidor,
                onValueChange = { onEvent(GastoFormUiEvent.UpdateSuplidor(it)) },
                label = { Text("Suplidor") },
                isError = state.suplidorError != null,
                supportingText = {
                    state.suplidorError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.ncf,
                onValueChange = { onEvent(GastoFormUiEvent.UpdateNcf(it)) },
                label = { Text("Ncf") },
                isError = state.ncfError != null,
                supportingText = {
                    state.ncfError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.itbis,
                onValueChange = { onEvent(GastoFormUiEvent.UpdateItbis(it)) },
                label = { Text("Itbis") },
                isError = state.itbisError != null,
                supportingText = {
                    state.itbisError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.monto,
                onValueChange = { onEvent(GastoFormUiEvent.UpdateMonto(it)) },
                label = { Text("Monto") },
                isError = state.montoError != null,
                supportingText = {
                    state.montoError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { onEvent(GastoFormUiEvent.Save) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.gastoId == null) "Guardar" else "Modificar")
            }
        }
    }
}