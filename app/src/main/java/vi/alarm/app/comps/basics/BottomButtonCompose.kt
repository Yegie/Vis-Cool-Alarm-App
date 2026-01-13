package vi.alarm.app.comps.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vi.alarm.app.ui.theme.ViAlarmAppTheme

@Composable
internal fun BottomButtonView(
    text: String,
    toggled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    TextButton (
        modifier = Modifier
            .then(modifier),
        enabled = enabled,
        onClick = onClick,
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        shape = RectangleShape,
    ) {
        Text (
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                ),
            style = MaterialTheme.typography.bodyLarge,
            text = text
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun Preview() {
    ViAlarmAppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BottomButtonView("Sample Toggled On",true, {})
            BottomButtonView("Sample Toggled Off",false, {})
            BottomButtonView("Sample Disabled",false, {}, enabled = false)
            BottomButtonView("Sample with a really long text that should wrap onto a second line",true, {})
        }
    }
}