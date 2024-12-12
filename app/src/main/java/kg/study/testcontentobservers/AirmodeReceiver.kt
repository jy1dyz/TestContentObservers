package kg.study.testcontentobservers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * нерабочий способ проверки airmode
 * см класс AirplaneModeObserver
 * */
class AirmodeReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED == intent?.action) {
            val isAirPlaneModeOn = intent.getBooleanExtra("state", false)
            if (isAirPlaneModeOn) {
                Toast.makeText(context, "ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "OFF", Toast.LENGTH_SHORT).show()
            }
        }
    }
}