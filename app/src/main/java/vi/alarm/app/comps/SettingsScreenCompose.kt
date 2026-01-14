package vi.alarm.app.comps

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
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
import vi.alarm.app.comps.basics.SettingScreenTitleView
import vi.alarm.app.comps.basics.ToggleButtonView
import vi.alarm.app.ui.theme.KofiRed
import vi.alarm.app.ui.theme.ViAlarmAppTheme

//todo pull up on chains when selected :P

@Composable
internal fun SettingsScreenView(viewModel: AlarmAppViewModel = viewModel()) {
    val uriHandler = LocalUriHandler.current
    val deleteUnusedAlarms by viewModel.deleteUnusedAlarms.collectAsState()

    BackHandler(true) {
        viewModel.setScreen(Screen.Alarms)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = SETTINGS_H_PAD,
                vertical = SETTINGS_V_PAD
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),

    ) {
        SettingScreenTitleView(
            stringResource(R.string.settings_title)
        )
        ToggleButtonView(
            modifier = Modifier,
            text = stringResource(R.string.automatically_delete_unused_alarms),
            toggled = deleteUnusedAlarms,
            onClick = {
                viewModel.setDeleteUnusedAlarms(!deleteUnusedAlarms)
            }
        )
        SettingScreenTitleView(
            stringResource(R.string.credits_title)
        )
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .background(KofiRed),
            shape = RectangleShape,
            contentPadding = PaddingValues(
                horizontal = SETTINGS_H_PAD,
                vertical = SETTINGS_V_PAD
            ),
            onClick = {
                DataStoreRepo.getInstance()?.setHasClickedKofiButton(true)
                uriHandler.openUri("https://ko-fi.com/yegie")
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(2f),
                    text = "Support me on:",
                    color = MaterialTheme.colorScheme.primaryContainer,
                    style = MaterialTheme.typography.titleLarge
                )
                Image(
                    modifier = Modifier
                        .weight(1f),
                    painter = painterResource(R.drawable.kofi_logo),
                    contentDescription = stringResource(R.string.support_me_on_kofi_button_content_desc)
                )
            }
        }
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .padding(
                    horizontal = SETTINGS_H_PAD,
                    vertical = SETTINGS_V_PAD
                ),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(R.string.design_development_credit),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .padding(
                    horizontal = SETTINGS_H_PAD,
                    vertical = SETTINGS_V_PAD
                ),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            text = stringResource(R.string.font_credit),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

internal val SETTINGS_H_PAD = 16.dp
internal val SETTINGS_V_PAD = 8.dp

@Preview(showBackground = true, device = PIXEL_9)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        SettingsScreenView()
    }
}