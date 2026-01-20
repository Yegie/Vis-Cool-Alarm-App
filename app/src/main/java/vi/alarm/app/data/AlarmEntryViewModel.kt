package vi.alarm.app.data

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vi.alarm.app.data.room.AlarmEntry

@OptIn(ExperimentalMaterial3Api::class)
internal class AlarmEntryViewModel(
    alarmEntry: AlarmEntry
): ViewModel() {
    //ui
    private val _showTimePickerDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTimePickerDialog: StateFlow<Boolean> = _showTimePickerDialog.asStateFlow()
    private val _compacted: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val compacted: StateFlow<Boolean> = _compacted.asStateFlow()

    //data
    private val _alarmEntry: MutableStateFlow<AlarmEntry> = MutableStateFlow(alarmEntry)
    val alarmEntry: StateFlow<AlarmEntry> = _alarmEntry.asStateFlow()

    //todo get rid of these two from in here
    val title: TextFieldState = TextFieldState(alarmEntry.title)
    val time: TimePickerState = TimePickerState(
        initialHour = alarmEntry.hour,
        initialMinute = alarmEntry.minute,
        is24Hour = false,
    )

    //setters
    fun setShouldShowTimePicker(value: Boolean) {
        viewModelScope.launch {
            _showTimePickerDialog.value = value
        }
    }

    fun setCompacted(value: Boolean) {
        viewModelScope.launch {
            _compacted.value = value
            if (!value)
                emitCompactSignal(onCompactSignal)
        }
    }

    fun setTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            _alarmEntry.value = _alarmEntry.value.copy(
                hour = hour,
                minute = minute
            )
        }
    }

    //signal stuff
    val onCompactSignal: () -> Unit = {
        viewModelScope.launch {
            _compacted.value = true
        }
    }

    fun subscribe() {
        subscribeToCompactSignal(onCompactSignal)
    }

    fun unsubscribe() {
        unsubscribeFromCompactSignal(onCompactSignal)
    }

    class Factory(
        private val alarmEntry: AlarmEntry
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlarmEntryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlarmEntryViewModel(alarmEntry) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    //todo this is not the best way to do this. given the current scope it works, but if the
    // signal system gets expanded should refactor to proper AppEvent system
    companion object {
        private val listeners: MutableList<() -> Unit> = mutableListOf()

        private fun subscribeToCompactSignal(listener: () -> Unit) {
            listeners.add(listener)
        }

        private fun unsubscribeFromCompactSignal(listener: () -> Unit) {
            listeners.remove(listener)
        }

        fun emitCompactSignal(exclude: (() -> Unit)? = null) {
            listeners.forEach {
                if (it == exclude) {
                    return@forEach
                }
                it.invoke()
            }
        }
    }
}