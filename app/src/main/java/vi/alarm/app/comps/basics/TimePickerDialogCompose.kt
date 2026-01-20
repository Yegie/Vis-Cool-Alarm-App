package vi.alarm.app.comps.basics

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import vi.alarm.app.data.AlarmEntryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerDialog(
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