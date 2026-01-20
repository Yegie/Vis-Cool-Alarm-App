package vi.alarm.app.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vi.alarm.app.DataStoreRepo

enum class Screen {
    Alarms,
    Settings,
}

internal class AlarmAppViewModel(
    alarms: MutableList<AlarmEntryViewModel>
): ViewModel() {
    private val _currentAlarms: MutableStateFlow<MutableList<AlarmEntryViewModel>> = MutableStateFlow(alarms)
    val currentAlarms: StateFlow<List<AlarmEntryViewModel>> = _currentAlarms.asStateFlow()
    private val _currentScreen: MutableStateFlow<Screen> = MutableStateFlow(Screen.Alarms)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()
    private val _hasClickedKofi: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasClickedKofi: StateFlow<Boolean> = _hasClickedKofi.asStateFlow()
    private val _deleteUnusedAlarms: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteUnusedAlarms: StateFlow<Boolean> = _deleteUnusedAlarms.asStateFlow()

    fun addAlarm(alarmEntryViewModel: AlarmEntryViewModel) {
        viewModelScope.launch {
            _currentAlarms.value.add(alarmEntryViewModel)
        }
    }

    fun setScreen(screen: Screen) {
        viewModelScope.launch {
            _currentScreen.value = screen
        }
    }

    fun setDeleteUnusedAlarms(value: Boolean) {
        DataStoreRepo.getInstance()?.setDeleteUnusedAlarms(value)
        viewModelScope.launch {
            _deleteUnusedAlarms.value = value
        }
    }

    fun loadValuesFromPrefs() {
        val repo = DataStoreRepo.getInstance() ?: return
        viewModelScope.launch {
            _hasClickedKofi.value = repo.getHasClickedKofiButton()
            _deleteUnusedAlarms.value = repo.getDeleteUnusedAlarms()
        }
    }

    class Factory(
        application: Application,
        private val alarms: MutableList<AlarmEntryViewModel>
    ) : ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlarmAppViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlarmAppViewModel(alarms) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}