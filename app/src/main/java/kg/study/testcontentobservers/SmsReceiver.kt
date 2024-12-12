package kg.study.testcontentobservers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.action) {
            val pdus = intent?.extras?.get("pdus") as? Array<*>
            pdus?.forEach {
                val smsMessage = SmsMessage.createFromPdu(it as ByteArray)
                val sender = smsMessage.originatingAddress
                val messageBody = smsMessage.messageBody
                Log.w("TAG", "SmsReceiver: sender $sender, Message: $messageBody")
            }
        }
    }
}