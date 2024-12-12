package kg.study.testcontentobservers

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import android.util.Log

class AirplaneModeObserver(context: Context, private val callback: (Boolean) -> Unit) :
    BaseContentObserver(context, null) {
    override val contentResolver: ContentResolver = context.contentResolver

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Log.w("TAG", "onChange $selfChange")
        callback(isAirplaneModeOn())
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }
}