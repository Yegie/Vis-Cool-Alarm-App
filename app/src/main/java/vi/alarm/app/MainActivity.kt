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

        //todo if the user has used the app for a while and has never clicked the kofi link, maybe
        // popup a once off request for donation

        setContent {
            ViAlarmAppTheme {
                AppContainerView()
            }
        }
    }
}