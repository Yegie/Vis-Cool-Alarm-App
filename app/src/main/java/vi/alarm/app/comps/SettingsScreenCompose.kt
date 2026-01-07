package vi.alarm.app.comps

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vi.alarm.app.AlarmAppViewModel
import vi.alarm.app.DataStoreRepo
import vi.alarm.app.R
import vi.alarm.app.Screen
import vi.alarm.app.comps.basics.ToggleButtonView
import vi.alarm.app.ui.theme.ViAlarmAppTheme

@Composable
internal fun SettingsScreenView(viewModel: AlarmAppViewModel = viewModel()) {
    val uriHandler = LocalUriHandler.current
    val hasClickedKofi by viewModel.hasClickedKofi.collectAsState()
    val deleteUnusedAlarms by viewModel.deleteUnusedAlarms.collectAsState()

    BackHandler(true) {
        viewModel.setScreen(Screen.Alarms)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ToggleButtonView(
            modifier = Modifier,
            text = stringResource(R.string.automatically_delete_unused_alarms),
            toggled = deleteUnusedAlarms,
            onClick = {
                viewModel.setDeleteUnusedAlarms(!deleteUnusedAlarms)
            }
        )
        if (!hasClickedKofi) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    DataStoreRepo.getInstance()?.setHasClickedKofiButton(true)
                    uriHandler.openUri("https://ko-fi.com/yegie")
                }
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.support_me_on_kofi_red),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = stringResource(R.string.support_me_on_kofi_button_content_desc)
                )
            }
        }
    }
}

@Preview(showBackground = true, device = PIXEL_9)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        SettingsScreenView()
    }
}