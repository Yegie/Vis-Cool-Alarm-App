package vi.alarm.app.comps.basics

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import vi.alarm.app.data.AlarmEntryViewModel

//todo refactor, should not need view model
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TimePickerDialog(
    onConfirm: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
    viewModel: AlarmEntryViewModel
) {
    BasicDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(viewModel.time.hour, viewModel.time.minute) }
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
        shape = RectangleShape,
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