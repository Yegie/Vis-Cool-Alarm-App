package vi.alarm.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import vi.alarm.app.comps.AppContainerView
import vi.alarm.app.data.AlarmAppViewModel
import vi.alarm.app.data.AlarmEntryViewModel
import vi.alarm.app.data.room.AlarmEntry
import vi.alarm.app.ui.theme.ViAlarmAppTheme
import java.util.Calendar

internal class MainActivity : ComponentActivity() {

    private val db = application.database()
    lateinit var alarmAppViewModel: AlarmAppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val currentAlarms: List<AlarmEntry> = db.alarmDao().getAll()

        val currentAlarmsViewModels: MutableList<AlarmEntryViewModel> = createModelsForEntries(currentAlarms)

        val alarmAppViewModel: AlarmAppViewModel by viewModels {
            AlarmAppViewModel.Factory(
                application,
                currentAlarmsViewModels
            )
        }
        this.alarmAppViewModel = alarmAppViewModel

        alarmAppViewModel.loadValuesFromPrefs()

        //todo if the user has used the app for a while and has never clicked the kofi link, maybe
        // popup a once off request for donation

        setContent {
            ViAlarmAppTheme {
                AppContainerView(
                    newAlarmClicked = ::newAlarmClicked,
                    viewModel = alarmAppViewModel
                )
            }
        }
    }

    //todo write alarms to database on pause?

    private fun newAlarmClicked() {
        val time = Calendar.getInstance()
        val entry = AlarmEntry(time.get(Calendar.HOUR), time.get(Calendar.MINUTE))
        db.alarmDao().insert(entry)
        val viewModel = createModelForEntry(entry)
        //todo make active and focus to this
        alarmAppViewModel.addAlarm(viewModel)
    }

    private fun createModelsForEntries(currentAlarms: List<AlarmEntry>): MutableList<AlarmEntryViewModel> {
        val out: MutableList<AlarmEntryViewModel> = mutableListOf()

        currentAlarms.forEach {
            out.add(createModelForEntry(it))
        }

        return out
    }

    private fun createModelForEntry(entry: AlarmEntry): AlarmEntryViewModel {
        val alarmEntryViewModel: AlarmEntryViewModel by viewModels {
            AlarmEntryViewModel.Factory(
                application,
                entry
            )
        }
        return alarmEntryViewModel
    }
}