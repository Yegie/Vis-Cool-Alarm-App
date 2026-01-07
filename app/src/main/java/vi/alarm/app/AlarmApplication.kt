package vi.alarm.app

import android.app.Application

class AlarmApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        DataStoreRepo.getInstance(applicationContext)
    }
}