package vi.alarm.app.comps

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices.PIXEL_9
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import vi.alarm.app.AlarmAppViewModel
import vi.alarm.app.ui.theme.ViAlarmAppTheme
import java.lang.ref.WeakReference

@Composable
internal fun AlarmScreenView(viewModel: AlarmAppViewModel = viewModel()) {
    val activity = WeakReference<Activity>(LocalActivity.current)

    BackHandler(true) {
        activity.get()?.finish()
    }

    Text("We are on the alarms screen")
}

@Preview(showBackground = true, device = PIXEL_9)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        AlarmScreenView()
    }
}