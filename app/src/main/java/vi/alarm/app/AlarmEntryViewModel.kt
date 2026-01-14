package vi.alarm.app

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
class AlarmEntryViewModel: ViewModel() {
    private val currentTime = Calendar.getInstance()

    private val _showTimePickerDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTimePickerDialog: StateFlow<Boolean> = _showTimePickerDialog.asStateFlow()

    val title: TextFieldState = TextFieldState()
    val time: TimePickerState = TimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    fun setShouldShowTimePicker(value: Boolean) {
        viewModelScope.launch {
            _showTimePickerDialog.value = value
        }
    }
}