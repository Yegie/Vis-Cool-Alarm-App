package vi.alarm.app

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.isPm
import java.util.Locale

//12 being represented as 0 is intentional as I prefer it this way. May add a config for this when
// I get closer to releasing for others
@OptIn(ExperimentalMaterial3Api::class)
internal fun TimePickerState.getDisplayString(): String {
    return if (!is24hour) {
        if (isPm) {
            "${hour - 12}:${formatMinutes(minute)} pm"
        } else {
            "$hour:${formatMinutes(minute)} am"
        }
    } else {
        "$hour:${formatMinutes(minute)}"
    }
}

private fun formatMinutes(minute: Int): String = String.format(Locale.getDefault(), "%02d", minute)