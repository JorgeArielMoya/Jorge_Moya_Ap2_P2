package edu.ucne.jorge_moya_ap2_p2.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import edu.ucne.jorge_moya_ap2_p2.presentation.model.edit.GastoFormScreen
import edu.ucne.jorge_moya_ap2_p2.presentation.model.list.GastosListScreen

@Composable
fun AppNavigationDisplay() {
    val backStack = rememberNavBackStack(Screen.GastoList)

    NavDisplay(
        backStack = backStack,
        modifier = Modifier.fillMaxSize(),
        entryProvider = entryProvider {
            entry<Screen.GastoList> {
                GastosListScreen (
                    onAddGasto= { backStack.add(Screen.GastoForm(id = 0)) },
                    onEditGasto = { id -> backStack.add(Screen.GastoForm(id = id)) }
                )
            }
            entry<Screen.GastoForm> { key ->
                GastoFormScreen(
                    gastoId = key.id,
                    onNavigateBack = {
                        if (backStack.isNotEmpty()) {
                            backStack.removeAt(backStack.size - 1)
                        }
                    }
                )
            }
        }
    )
}