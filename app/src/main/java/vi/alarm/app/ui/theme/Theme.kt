package vi.alarm.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun ViAlarmAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    //todo
//    val darkColorScheme = darkColorScheme(
//        primary = colorResource(R.color.vi_colors_medium),
//        secondary = colorResource(R.color.vi_colors_light),
//        tertiary = colorResource(R.color.vi_colors_dark),
//        background = colorResource(R.color.vi_colors_dark),
//        onBackground = colorResource(R.color.vi_colors_medium)
//    )

    val lightColorScheme = lightColorScheme(
        background = ViLight,
        onBackground = ViMedium,
        primaryContainer = ViDark,
        onPrimaryContainer = ViBright,
        tertiaryContainer = ViDarkDisabled,
        onTertiaryContainer = ViLight
    )
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
//        darkTheme -> darkColorScheme todo
        else -> lightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}