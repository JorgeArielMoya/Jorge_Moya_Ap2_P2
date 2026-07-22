package edu.ucne.jorge_moya_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.jorge_moya_ap2_p2.presentation.navigation.AppNavigationDisplay
import edu.ucne.jorge_moya_ap2_p2.ui.theme.Jorge_Moya_Ap2_P2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jorge_Moya_Ap2_P2Theme {
                AppNavigationDisplay()
            }
        }
    }
}