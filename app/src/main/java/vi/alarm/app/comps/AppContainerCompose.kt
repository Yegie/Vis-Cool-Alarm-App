package vi.alarm.app.comps

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import vi.alarm.app.AlarmAppViewModel
import vi.alarm.app.R
import vi.alarm.app.Screen
import vi.alarm.app.ui.theme.ViAlarmAppTheme

//todo gurl this aint nearly cool enough, we gotta up the cool by like 5x at least

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

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (bottomBar, navHost) = createRefs()
        val bitmap = ImageBitmap.imageResource(id = R.drawable.background)

        Canvas (
            modifier = Modifier.fillMaxSize()
        ) {
            drawImage(
                image = bitmap,
                dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                filterQuality = FilterQuality.None
            )
        }
        BottomBarView(
            modifier = Modifier
                .constrainAs(bottomBar) {
                    bottom.linkTo(parent.bottom)
                },
            viewModel = viewModel
        )
        NavHost(
            modifier = Modifier
                .constrainAs(navHost) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomBar.top)
                },
            navController = navController,
            startDestination = Screen.Settings.toString()
        ) {
            composable(Screen.Alarms.name) {
                AlarmScreenView(viewModel = viewModel)
            }
            composable(Screen.Settings.name) {
                SettingsScreenView(viewModel = viewModel)
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
