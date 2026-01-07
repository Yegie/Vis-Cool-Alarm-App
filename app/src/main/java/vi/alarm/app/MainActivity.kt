package vi.alarm.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import vi.alarm.app.comps.AppContainerView
import vi.alarm.app.ui.theme.ViAlarmAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ViAlarmAppTheme {
                AppContainerView()
            }
        }
    }
}