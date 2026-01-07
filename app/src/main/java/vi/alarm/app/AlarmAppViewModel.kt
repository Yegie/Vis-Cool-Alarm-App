package vi.alarm.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class Screen {
    Alarms,
    Settings,
}

internal class AlarmAppViewModel: ViewModel() {

    private val _currentScreen: MutableStateFlow<Screen> = MutableStateFlow(Screen.Alarms)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()
    private val _hasClickedKofi: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasClickedKofi: StateFlow<Boolean> = _hasClickedKofi.asStateFlow()
    private val _deleteUnusedAlarms: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val deleteUnusedAlarms: StateFlow<Boolean> = _deleteUnusedAlarms.asStateFlow()

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
}