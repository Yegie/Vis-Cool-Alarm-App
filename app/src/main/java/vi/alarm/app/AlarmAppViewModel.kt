package vi.alarm.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

enum class Screen {
    Alarms,
    Settings,
}

class AlarmAppViewModel: ViewModel() {

    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.Alarms)
    val currentScreen: State<Screen> = _currentScreen

    fun setScreen(screen: Screen) {
        _currentScreen.value = screen
    }
}