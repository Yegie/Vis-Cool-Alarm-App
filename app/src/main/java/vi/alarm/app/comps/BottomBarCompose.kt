package vi.alarm.app.comps

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vi.alarm.app.AlarmAppViewModel
import vi.alarm.app.Screen
import vi.alarm.app.ui.theme.ViAlarmAppTheme

@Composable
internal fun BottomBarView(
    modifier: Modifier = Modifier,
    viewModel: AlarmAppViewModel = viewModel()
) {
    val currentScreen = remember { viewModel.currentScreen }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {
                viewModel.setScreen(Screen.Alarms)
                Log.d("tmp", viewModel.currentScreen.toString())
            }
        ) {
            Icon(
                imageVector = if (currentScreen.value == Screen.Alarms) Icons.Filled.Alarm else Icons.Outlined.Alarm,
                contentDescription = "Alarm Icon",
                tint = if (currentScreen.value == Screen.Alarms) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.background
            )
        }
        IconButton(
            onClick = {
                viewModel.setScreen(Screen.Settings)
                Log.d("tmp", viewModel.currentScreen.toString())
            }
        ) {
            Icon(
                imageVector = if (currentScreen.value == Screen.Settings) Icons.Filled.Settings else Icons.Outlined.Settings,
                contentDescription = "Settings Icon",
                tint = if (currentScreen.value == Screen.Settings) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.background
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        BottomBarView()
    }
}