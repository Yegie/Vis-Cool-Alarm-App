package vi.alarm.app.comps.alarmentry

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vi.alarm.app.data.AlarmEntryViewModel
import vi.alarm.app.data.room.AlarmEntry
import vi.alarm.app.ui.theme.ViAlarmAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CompactedAlarmEntryView(
    viewModel: AlarmEntryViewModel
) {
    val alarmEntry by viewModel.alarmEntry.collectAsState()

    //todo refactor to using the backing object for this data
    var title by remember { mutableStateOf(viewModel.title.text) }

    TextButton(
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        shape = RectangleShape,
        onClick = {
            viewModel.setCompacted(false)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = alarmEntry.getTimeDisplayString(),
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = title.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
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
            val viewModel2 = AlarmEntryViewModel(AlarmEntry(
                id = 456,
                title = "Unselected Active Alarm in the PM",
                hour = 21,
                minute = 45
            ))
            AlarmEntryView(viewModel = viewModel2)
        }
    }
}