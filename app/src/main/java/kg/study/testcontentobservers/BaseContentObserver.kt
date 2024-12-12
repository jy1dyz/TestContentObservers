package kg.study.testcontentobservers

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler

abstract class BaseContentObserver(
    private val context: Context,
    handler: Handler?
) : ContentObserver(handler) {

    abstract val contentResolver: ContentResolver

    open fun startObserving(uri: Uri, notify: Boolean) {
        contentResolver.registerContentObserver(uri, notify, this)
    }

    open fun stopObserving() {
        contentResolver.unregisterContentObserver(this)
    }
}