package vi.alarm.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vi.alarm.app.comps.AppContainerView
import vi.alarm.app.data.AlarmAppViewModel
import vi.alarm.app.data.AlarmEntryViewModel
import vi.alarm.app.data.room.AlarmEntry
import vi.alarm.app.data.room.AppDatabase
import vi.alarm.app.ui.theme.ViAlarmAppTheme
import java.util.Calendar

internal class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    var alarmAppViewModel: AlarmAppViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = application.database()


        CoroutineScope(Dispatchers.IO).launch {

            val currentAlarms: List<AlarmEntry> = db.alarmDao().getAll()

            val currentAlarmsViewModels: MutableList<AlarmEntryViewModel> = createModelsForEntries(currentAlarms)

            val viewModel: AlarmAppViewModel by viewModels {
                AlarmAppViewModel.Factory(
                    application,
                    currentAlarmsViewModels
                )
            }
            viewModel.loadValuesFromPrefs()

            alarmAppViewModel = viewModel
        }

        //todo if the user has used the app for a while and has never clicked the kofi link, maybe
        // popup a once off request for donation

        setContent {
            ViAlarmAppTheme {
                alarmAppViewModel?.let {
                    AppContainerView(
                        newAlarmClicked = ::newAlarmClicked,
                        viewModel = it
                    )
                }
            }
        }
    }

    override fun onPause() {
        //todo group into one transaction
        CoroutineScope(Dispatchers.IO).launch {
            alarmAppViewModel?.currentAlarms?.value?.forEach {
                db.alarmDao().insert(it.alarmEntry.value)
            }
        }
        super.onPause()
    }

    private fun newAlarmClicked() {
        CoroutineScope(Dispatchers.IO).launch {
            val time = Calendar.getInstance()
            val entry = AlarmEntry(time.get(Calendar.HOUR), time.get(Calendar.MINUTE))
            db.alarmDao().insert(entry)
            val viewModel = createModelForEntry(entry)
            //todo make active and focus to this
            alarmAppViewModel?.addAlarm(viewModel)
            Log.d("tmp", "added new alarm")
        }
    }

    private fun createModelsForEntries(currentAlarms: List<AlarmEntry>): MutableList<AlarmEntryViewModel> {
        val out: MutableList<AlarmEntryViewModel> = mutableListOf()

        currentAlarms.forEach {
            out.add(createModelForEntry(it))
        }

        return out
    }

    private fun createModelForEntry(entry: AlarmEntry): AlarmEntryViewModel {
        val alarmEntryViewModel: AlarmEntryViewModel = ViewModelProvider(
            this,
            AlarmEntryViewModel.Factory(entry)
        )[entry.id.toString(), AlarmEntryViewModel::class.java]
        return alarmEntryViewModel
    }
}