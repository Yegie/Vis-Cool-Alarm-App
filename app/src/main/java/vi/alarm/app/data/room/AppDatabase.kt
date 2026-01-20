package vi.alarm.app.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AlarmEntry::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}