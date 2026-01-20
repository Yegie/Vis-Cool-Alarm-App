package vi.alarm.app.comps

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vi.alarm.app.comps.alarmentry.AlarmEntryView
import vi.alarm.app.data.AlarmAppViewModel
import vi.alarm.app.ui.theme.ViAlarmAppTheme
import java.lang.ref.WeakReference

@SuppressLint("ViewModelConstructorInComposable")
@Composable
internal fun AlarmScreenView(viewModel: AlarmAppViewModel = viewModel()) {
    val activity = WeakReference<Activity>(LocalActivity.current)
    val alarms by viewModel.currentAlarms.collectAsState()

    BackHandler(true) {
        activity.get()?.finish()
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        alarms.forEach { alarmViewModel ->
            AlarmEntryView(viewModel = alarmViewModel)
        }
    }
}

@Preview(showBackground = true, device = PIXEL_9)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        AlarmScreenView()
    }
}