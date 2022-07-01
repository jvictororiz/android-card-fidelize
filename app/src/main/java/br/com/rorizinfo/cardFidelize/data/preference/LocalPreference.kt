package br.com.rorizinfo.cardFidelize.data.preference

import android.content.Context
import br.com.rorizinfo.cardFidelize.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalPreference(private val context: Context) {
    
    fun save(typePreference: BasePreference, value: String) {
        val shared = context.getSharedPreferences(KEY_SHARE_PREFERENCES, Context.MODE_PRIVATE)
        shared.edit().putString(typePreference.key, value).apply()
    }
    
    fun save(typePreference: BasePreference, value: Long) {
        val shared = context.getSharedPreferences(KEY_SHARE_PREFERENCES, Context.MODE_PRIVATE)
        shared.edit().putLong(typePreference.key, value).apply()
    }
    
    fun save(typePreference: BasePreference, value: Boolean) {
        val shared = context.getSharedPreferences(KEY_SHARE_PREFERENCES, Context.MODE_PRIVATE)
        shared.edit().putBoolean(typePreference.key, value).apply()
    }
    
    fun getBoolean(typePreference: BasePreference, defaultValue: Boolean = false): Boolean {
        return context.getSharedPreferences(KEY_SHARE_PREFERENCES, Context.MODE_PRIVATE).run {
            getBoolean(typePreference.key, defaultValue)
        }
    }
    
    suspend fun get(typePreference: BasePreference, defaultValue: String = ""): String {
        return   withContext(Dispatchers.IO) { get(typePreference.key, defaultValue) }
    }
    
    suspend fun get(key: String, defaultValue: String = ""): String {
        return  withContext(Dispatchers.IO) {
            context.getSharedPreferences(KEY_SHARE_PREFERENCES, Context.MODE_PRIVATE).run {
                getString(key, defaultValue) ?: String()
            }
        }
    }
    
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return context.getSharedPreferences(KEY_SHARE_PREFERENCES, Context.MODE_PRIVATE).run {
            getLong(key, defaultValue)
        }
    }
    
    fun getLong(typePreference: BasePreference, defaultValue: Long = 0L): Long {
        return context.getSharedPreferences(KEY_SHARE_PREFERENCES, Context.MODE_PRIVATE).run {
            getLong(typePreference.key, defaultValue)
        }
    }
    
    fun getDeviceId() = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE).run {
        getString(DEVICE_ID, "") ?: ""
    }
    
    fun saveDeviceId(deviceId: String) {
        val shared = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        shared.edit().putString(DEVICE_ID, deviceId).apply()
    }
    
    fun remove(typePreference: BasePreference) {
        val shared = context.getSharedPreferences(KEY_SHARE_PREFERENCES, Context.MODE_PRIVATE)
        shared.edit().remove(typePreference.key).apply()
    }
    
    companion object {
        private const val KEY_SHARE_PREFERENCES = "SharedPreferencesKey"
        private const val PREFS_FILENAME = BuildConfig.APPLICATION_ID
        private const val DEVICE_ID = "device-id"
        
    }
}