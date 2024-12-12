package kg.study.testcontentobservers

import android.content.Context
import android.database.ContentObserver
import android.os.Handler
import android.provider.Settings
import android.util.Log

class ScreenBrightnessObserver(
    private val context: Context,
    handler: Handler,
    private val onBrightnessChanged: (Int) -> Unit
) : ContentObserver(handler) {

    private val contentResolver = context.contentResolver

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        // Получаем текущую яркость экрана
        val brightness = getScreenBrightness()
        onBrightnessChanged(brightness)
    }

    private fun getScreenBrightness(): Int {
        return try {
            Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Settings.SettingNotFoundException) {
            Log.e("BrightnessObserver", "Unable to access brightness setting", e)
            -1 // -1 в случае ошибки
        }
    }

    fun startObserving() {
        contentResolver.registerContentObserver(
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS),
            false,
            this
        )
    }

    fun stopObserving() {
        contentResolver.unregisterContentObserver(this)
    }
}