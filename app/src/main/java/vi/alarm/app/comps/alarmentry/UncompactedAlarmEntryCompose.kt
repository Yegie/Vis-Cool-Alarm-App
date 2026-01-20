package vi.alarm.app.comps.alarmentry

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vi.alarm.app.data.AlarmEntryViewModel
import vi.alarm.app.data.room.AlarmEntry
import vi.alarm.app.ui.theme.ViAlarmAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UncompactedAlarmEntryView(
    viewModel: AlarmEntryViewModel
) {
    val alarmEntry by viewModel.alarmEntry.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        TextButton(
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ),
            onClick = {
                viewModel.setShouldShowTimePicker(true)
            }
        ) {
            Text(
                text = alarmEntry.getTimeDisplayString(),
                style = MaterialTheme.typography.displayLarge
            )
        }
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            shape = RectangleShape,
            modifier = Modifier
                .fillMaxWidth(),
            state = viewModel.title,
            lineLimits = TextFieldLineLimits.SingleLine,
            label = {
                Text("Title")
            },
        )
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
            AlarmEntryView(viewModel = viewModel1)
        }
    }
}