package vi.alarm.app.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
internal interface AlarmDao {
    @Query("SELECT * FROM $ALARMS_TABLE_NAME")
    fun getAll(): List<AlarmEntry>

    @Query("SELECT * FROM $ALARMS_TABLE_NAME WHERE id = :id")
    fun get(id: Int): List<AlarmEntry>

    @Insert(onConflict = REPLACE)
    fun insert(alarm: AlarmEntry)

    @Delete
    fun delete(alarm: AlarmEntry)

    @Query("DELETE FROM $ALARMS_TABLE_NAME WHERE id = :id")
    fun delete(id: Int)
}

internal const val ALARMS_TABLE_NAME = "alarms_table"