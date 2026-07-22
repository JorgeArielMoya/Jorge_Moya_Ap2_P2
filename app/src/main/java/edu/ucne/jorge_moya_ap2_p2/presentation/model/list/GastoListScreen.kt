package edu.ucne.jorge_moya_ap2_p2.presentation.model.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.jorge_moya_ap2_p2.domain.model.Gastos

@Composable
fun GastosListScreen(
    viewModel: GastoListViewModel = hiltViewModel(),
    onAddGasto: () -> Unit,
    onEditGasto: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(GastoListUiEvent.LoadGastos)
    }

    ListGastoBodyScreen(
        state = state,
        onAddGasto = onAddGasto,
        onEditGasto = onEditGasto
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListGastoBodyScreen(
    state: GastoListUiState,
    onAddGasto: () -> Unit,
    onEditGasto: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Gastos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddGasto) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            state.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(state.gastos) { gasto ->
                    GastoItem(
                        gasto = gasto,
                        onClick = { onEditGasto(gasto.gastoId) }
                    )
                }
            }

            Text(
                text = "Total de Gastos: ${state.gastos.size}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun GastoItem(gasto: Gastos, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Id: ${gasto.gastoId}", style = MaterialTheme.typography.bodyLarge)
            Text("Fecha: ${gasto.fecha}", style = MaterialTheme.typography.bodyLarge)
            Text("Suplidor: ${gasto.suplidor}", style = MaterialTheme.typography.bodyMedium)
            Text("NCF: ${gasto.ncf}", style = MaterialTheme.typography.bodyMedium)
            Text("Itbis: ${gasto.itbis}", style = MaterialTheme.typography.bodyMedium)
            Text("Monto: ${gasto.monto}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}