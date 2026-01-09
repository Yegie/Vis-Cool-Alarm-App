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
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = dpComic, fontSize = 24.sp),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = dpComic, fontSize = 23.sp),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = dpComic, fontSize = 22.sp),

    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = dpComic, fontSize = 20.sp),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = dpComic, fontSize = 18.sp),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = dpComic, fontSize = 16.sp),
)