package kg.study.testcontentobservers.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi

/**
 * alarmUri:
 * content://settings/system/alarm_alert RingtoneManager.TYPE_ALARM
 * content://settings/system/ringtone RingtoneManager.TYPE_RINGTONE
 * content://settings/system/notification_sound RingtoneManager.TYPE_NOTIFICATION
 * */
class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private var ringtone: Ringtone? = null
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE")
        
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        when {
            ringtone?.isPlaying == true && !isRingtoneCancel(message) -> return
            ringtone?.isPlaying == true && isRingtoneCancel(message) -> stopRingtone(vibrator)
            else -> playRingtone(context, alarmUri, vibrator)
        }
    }

    private fun playRingtone(context: Context, alarmUri: Uri, vibrator: Vibrator) {
        ringtone = RingtoneManager.getRingtone(context, alarmUri)
        vibrator.vibrate(VibrationEffect.createOneShot(3000, -1))

        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)
        audioManager.setStreamVolume(
            AudioManager.STREAM_RING,
            maxVolume, // Уровень громкости (0 до maxVolume)
            0 // Флаги (например, AudioManager.FLAG_SHOW_UI)
        )
        ringtone?.play()
    }

    private fun stopRingtone(vibrator: Vibrator) {
        ringtone?.stop()
        vibrator.cancel()
        ringtone = null
    }

    private fun isRingtoneCancel(message: String?): Boolean =
        message?.contains("cancel") == true
}