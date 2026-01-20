package vi.alarm.app.data.room

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Locale

//this should only consist of vals to support recomposition
@Entity(tableName = ALARMS_TABLE_NAME)
internal data class AlarmEntry (
    val hour: Int,
    val minute: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
) {

    //12 being represented as 0 is intentional as I prefer it this way. May add a config for this when
    // I get closer to releasing for others
    @OptIn(ExperimentalMaterial3Api::class)
    internal fun getTimeDisplayString(): String {
        return if (hour >= 12) {
            "${hour - 12}:${formatMinutes(minute)} pm"
        } else {
            "$hour:${formatMinutes(minute)} am"
        }
    }

    private fun formatMinutes(minute: Int): String = String.format(Locale.getDefault(), "%02d", minute)
}