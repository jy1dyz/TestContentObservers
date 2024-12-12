package kg.study.testcontentobservers

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kg.study.testcontentobservers.alarm.AlarmItem
import kg.study.testcontentobservers.alarm.AppAlarmScheduler
import java.time.LocalDateTime

@Composable
fun AlarmScreen() {

    val context = LocalContext.current.applicationContext
    val scheduler by remember {
        mutableStateOf(AppAlarmScheduler(context))
    }
    var alarmItem by remember { mutableStateOf<AlarmItem?>(null) }

    var seconds by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = seconds,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            onValueChange = { seconds = it },
            placeholder = {
                Text(text = "alarm seconds")
            })
        Spacer(modifier = Modifier.size(12.dp))
        OutlinedTextField(
            value = message,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            onValueChange = { message = it },
            placeholder = {
                Text(text = "message")
            })
        Spacer(modifier = Modifier.size(12.dp))
        Button(onClick = {
            alarmItem = AlarmItem(
                time = LocalDateTime.now()
                    .plusSeconds(seconds.toLong()),
                message = message
            )
            alarmItem?.let { scheduler.schedule(it) }
            seconds = ""
            message = ""
        }) {
            Text(text = "alarm schedule")
        }
        Spacer(modifier = Modifier.size(12.dp))
        Button(onClick = {
            alarmItem?.let { scheduler.cancel(it) }
        }) {
            Text(text = "cancel")
        }
    }

}