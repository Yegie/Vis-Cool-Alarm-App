package vi.alarm.app.comps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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

    val bitmap = ImageBitmap.imageResource(id = R.drawable.tile_wall)
    val brush = remember(bitmap) { ShaderBrush(ImageShader(bitmap, TileMode.Repeated, TileMode.Repeated)) }

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(brush = brush)
    ) {
        val (bottomBar, navHost) = createRefs()

        BottomBarView(
            modifier = Modifier
                .constrainAs(bottomBar) {
                    bottom.linkTo(parent.bottom)
                },
            viewModel = viewModel
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(navHost) {
                    top.linkTo(parent.top)
                    bottom.linkTo(bottomBar.top)
                    height = Dimension.fillToConstraints
                }
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
                )
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Alarms.toString()
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
}

@Preview(showBackground = true, device = PIXEL_9, showSystemUi = true)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        AppContainerView()
    }
}
