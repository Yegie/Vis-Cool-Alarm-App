package vi.alarm.app.comps

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import vi.alarm.app.AlarmEntryViewModel
import vi.alarm.app.getDisplayString
import vi.alarm.app.ui.theme.ViAlarmAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AlarmEntryView (
    modifier: Modifier = Modifier,
    viewModel: AlarmEntryViewModel
) {
    val shouldShowTimePicker = viewModel.showTimePickerDialog.collectAsState()
    var displayTime by remember { mutableStateOf(viewModel.time.getDisplayString()) }

    if (shouldShowTimePicker.value) {
        TimePickerDialog(
            onConfirm = {
                displayTime = viewModel.time.getDisplayString()
                viewModel.setShouldShowTimePicker(false)
            },
            onDismiss = {
                viewModel.setShouldShowTimePicker(false)
            },
            viewModel = viewModel
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .then(modifier),
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
                text = displayTime,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
    viewModel: AlarmEntryViewModel
) {
    BasicDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(viewModel.time) }
    ) {
        TimePicker(
            state = viewModel.time,
        )
    }
}

@Composable
private fun BasicDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "Dismiss",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(
                    text = "Ok",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        text = { content() }
    )
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
            val viewModel = AlarmEntryViewModel()
            viewModel.title.edit { append("Sample Text") }
            AlarmEntryView(viewModel = viewModel())
            AlarmEntryView(viewModel = viewModel)
        }
    }
}