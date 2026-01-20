package vi.alarm.app.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ALARMS_TABLE_NAME)
internal data class AlarmEntry (
    val hour: Int,
    val minute: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
)