package vi.alarm.app.comps.basics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.outlined.ToggleOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vi.alarm.app.ui.theme.ViAlarmAppTheme

//todo 9patch rect with pixel art? maybe like a wooden sign?

@Composable
internal fun ToggleButtonView(
    text: String,
    toggled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button (
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(30),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(//todo replace with pixel art checkbox
                imageVector = if (toggled) Icons.Filled.ToggleOn else Icons.Outlined.ToggleOff,
                contentDescription = null
            )
        }
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
            ToggleButtonView("Sample Toggled On",true, {})
            ToggleButtonView("Sample Toggled Off",false, {})
            ToggleButtonView("Sample Disabled",false, {}, enabled = false)
            ToggleButtonView("Sample with a really long text that should wrap onto a second line",true, {})
        }
    }
}