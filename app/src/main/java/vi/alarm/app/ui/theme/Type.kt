package vi.alarm.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import vi.alarm.app.R

internal val dpComic = FontFamily(
    Font(R.font.dpcomic, FontWeight.Normal),
)
private val defaultTypography = Typography()
internal val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = dpComic, fontSize = 34.sp, lineHeight = 40.sp, letterSpacing = 0.sp),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = dpComic, fontSize = 30.sp, lineHeight = 36.sp, letterSpacing = 0.sp),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = dpComic, fontSize = 26.sp, lineHeight = 32.sp, letterSpacing = 0.sp),

    titleLarge = defaultTypography.titleLarge.copy(fontFamily = dpComic, fontSize = 24.sp),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = dpComic, fontSize = 23.sp),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = dpComic, fontSize = 22.sp),

    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = dpComic, fontSize = 24.sp),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = dpComic, fontSize = 23.sp),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = dpComic, fontSize = 22.sp),

    labelLarge = defaultTypography.labelLarge.copy(fontFamily = dpComic, fontSize = 14.sp),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = dpComic, fontSize = 13.sp),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = dpComic, fontSize = 12.sp),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = dpComic, fontSize = 20.sp),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = dpComic, fontSize = 18.sp),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = dpComic, fontSize = 16.sp),
)