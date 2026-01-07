package vi.alarm.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vi.alarm.app.comps.BottomBarView
import vi.alarm.app.ui.theme.ViAlarmAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViAlarmAppTheme {
                Content()
            }
        }
    }
}

@Composable
private fun Content(viewModel: AlarmAppViewModel = viewModel()) {
    val navController = rememberNavController()
    val currentScreen = remember { viewModel.currentScreen }

    LaunchedEffect(currentScreen.value) {
        navController.navigate(currentScreen.value.name)
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
                BottomBarView()
            },
            floatingActionButton = {

            }
        ) { innerPadding ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),

            ) {
                NavHost(navController, Screen.Alarms.toString()) {
                    composable(Screen.Alarms.name) {
                        Text("We are on the alarms screen")
                    }
                    composable(Screen.Settings.name) {
                        Text("We are on the settings screen")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = PIXEL_9, showSystemUi = true)
private fun Preview() {
    ViAlarmAppTheme {
        Content()
    }
}