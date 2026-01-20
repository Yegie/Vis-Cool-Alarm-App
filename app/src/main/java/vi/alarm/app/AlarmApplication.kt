package vi.alarm.app

import android.app.Application
import androidx.room.Room
import vi.alarm.app.data.room.AppDatabase

class AlarmApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        DataStoreRepo.getInstance(applicationContext)
    }

    companion object {
        internal lateinit var db: AppDatabase
    }
}

internal fun Application.database(): AppDatabase = AlarmApplication.db