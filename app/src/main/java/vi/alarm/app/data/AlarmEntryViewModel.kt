package vi.alarm.app.data

import android.app.Application
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
    private val alarmEntry: AlarmEntry
): ViewModel() {
    //ui
    private val _showTimePickerDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showTimePickerDialog: StateFlow<Boolean> = _showTimePickerDialog.asStateFlow()
    private val _compacted: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val compacted: StateFlow<Boolean> = _compacted.asStateFlow()

    //data
    val title: TextFieldState = TextFieldState(alarmEntry.title)
    val time: TimePickerState = TimePickerState(
        initialHour = alarmEntry.hour,
        initialMinute = alarmEntry.minute,
        is24Hour = false,
    )

    fun setShouldShowTimePicker(value: Boolean) {
        viewModelScope.launch {
            _showTimePickerDialog.value = value
        }
    }

    fun setCompacted(value: Boolean) {
        viewModelScope.launch {
            _compacted.value = value
        }
    }

    val onCompactSignal: () -> Unit = {
        viewModelScope.launch {
            _compacted.value = true
            emitCompactSignal(onCompactSignal)
        }
    }

    fun subscribe() {
        subscribeToCompactSignal(onCompactSignal)
    }

    fun unsubscribe() {
        unsubscribeFromCompactSignal(onCompactSignal)
    }

    class Factory(
        application: Application,
        private val alarmEntry: AlarmEntry
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlarmEntryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlarmEntryViewModel(alarmEntry) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

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