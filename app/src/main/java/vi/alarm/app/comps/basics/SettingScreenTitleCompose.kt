package vi.alarm.app.comps.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vi.alarm.app.ui.theme.ViAlarmAppTheme

@Composable
internal fun SettingScreenTitleView(
    text: String
) {
    HorizontalDivider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.background
    )
    Text(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        text = text,
        style = MaterialTheme.typography.titleMedium
    )
    HorizontalDivider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.background
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        SettingScreenTitleView("Sample Title")
    }
}