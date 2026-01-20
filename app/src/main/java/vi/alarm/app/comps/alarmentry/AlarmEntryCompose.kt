package vi.alarm.app.comps.alarmentry

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vi.alarm.app.comps.basics.TimePickerDialog
import vi.alarm.app.data.AlarmEntryViewModel
import vi.alarm.app.data.room.AlarmEntry
import vi.alarm.app.getDisplayString
import vi.alarm.app.ui.theme.ViAlarmAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlarmEntryView (
    viewModel: AlarmEntryViewModel
) {
    val shouldShowTimePicker = viewModel.showTimePickerDialog.collectAsState()
    val compacted = viewModel.compacted.collectAsState()
    var displayTime by remember { mutableStateOf(viewModel.time.getDisplayString()) }

    DisposableEffect(Unit) {
        viewModel.subscribe()

        onDispose {
            viewModel.unsubscribe()
        }
    }

    if (shouldShowTimePicker.value) {
        TimePickerDialog(
            onConfirm = { hour, minute ->
                viewModel.setTime(hour, minute)
                viewModel.setShouldShowTimePicker(false)
            },
            onDismiss = {
                viewModel.setShouldShowTimePicker(false)
            },
            viewModel = viewModel
        )
    }

    if (compacted.value) {
        CompactedAlarmEntryView(viewModel)
    } else {
        UncompactedAlarmEntryView(viewModel)
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val viewModel1 = AlarmEntryViewModel(AlarmEntry(
                id = 123,
                title = "Selected Active Alarm in the AM",
                hour = 8,
                minute = 13
            ))
            viewModel1.setCompacted(false)
            val viewModel2 = AlarmEntryViewModel(AlarmEntry(
                id = 456,
                title = "Unselected Active Alarm in the PM",
                hour = 21,
                minute = 45
            ))
            AlarmEntryView(viewModel = viewModel1)
            AlarmEntryView(viewModel = viewModel2)
        }
    }
}