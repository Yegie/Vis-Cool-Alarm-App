package vi.alarm.app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

private const val USER_PREFERENCES_NAME = "vi_alarm_app_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

internal class DataStoreRepo private constructor(context: Context) {
    private val context = WeakReference<Context>(context)

    fun setHasClickedKofiButton(value: Boolean = true) {
        CoroutineScope(Dispatchers.IO).launch {
            putBoolean(KOFI_CLICKED_KEY, value)
        }
    }

    suspend fun getHasClickedKofiButton(): Boolean {
        return getBoolean(KOFI_CLICKED_KEY)
    }
    fun setDeleteUnusedAlarms(value: Boolean = true) {
        CoroutineScope(Dispatchers.IO).launch {
            putBoolean(DELETE_UNUSED_ALARMS, value)
        }
    }

    suspend fun getDeleteUnusedAlarms(): Boolean {
        return getBoolean(DELETE_UNUSED_ALARMS)
    }

    /////////////////
    // helper methods
    /////////////////

    private suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        context.get()?.dataStore?.edit { preferences ->
            preferences[preferencesKey] = value
        } ?: throw IllegalStateException("Context was null")
    }

    private suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        val preferencesKey = booleanPreferencesKey(key)
        val context = context.get() ?: return defaultValue
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey] ?: defaultValue
    }

    private suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.get()?.dataStore?.edit { preferences ->
            preferences[preferencesKey] = value
        } ?: throw IllegalStateException("Context was null")
    }

    private suspend fun getInt(key: String, defaultValue: Int? = null): Int? {
        val preferencesKey = intPreferencesKey(key)
        val context = context.get() ?: return defaultValue
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey] ?: defaultValue
    }

    companion object {
        private const val KOFI_CLICKED_KEY = "metaUserHasClickedKofiLink"
        private const val DELETE_UNUSED_ALARMS = "settingDeleteUnusedAlarms"

        private var dataStoreRepo: DataStoreRepo? = null

        /**
         * If context is passed in: creates a new repo from it.
         *
         * If no context passed in: returns latest created repo unless the context for that repo
         * is now null in which case returns null.
         */
        fun getInstance(context: Context? = null): DataStoreRepo? {
            if (context != null)
                dataStoreRepo = DataStoreRepo(context)
            else if (dataStoreRepo?.context?.get() == null)
                dataStoreRepo = null
            return dataStoreRepo
        }
    }
}