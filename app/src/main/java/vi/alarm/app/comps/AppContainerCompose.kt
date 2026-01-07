package vi.alarm.app.comps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vi.alarm.app.AlarmAppViewModel
import vi.alarm.app.Screen
import vi.alarm.app.ui.theme.ViAlarmAppTheme

@Composable
internal fun AppContainerView(viewModel: AlarmAppViewModel = viewModel()) {
    val navController = rememberNavController()
    val currentScreen by viewModel.currentScreen.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadValuesFromPrefs()
    }

    LaunchedEffect(currentScreen) {
        navController.navigate(currentScreen.name)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .background(MaterialTheme.colorScheme.background),
            bottomBar = {
                BottomBarView(viewModel = viewModel)
            },
            floatingActionButton = {

            }
        ) { innerPadding ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                ) {
                NavHost(navController, Screen.Alarms.toString()) {
                    composable(Screen.Alarms.name) {
                        AlarmScreenView(viewModel = viewModel)
                    }
                    composable(Screen.Settings.name) {
                        SettingsScreenView(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = PIXEL_9, showSystemUi = true)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        AppContainerView()
    }
}
