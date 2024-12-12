package kg.study.testcontentobservers

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import kg.study.testcontentobservers.ui.theme.TestContentObserversTheme

class MainActivity : ComponentActivity() {

    private val observer by lazy {
        AirplaneModeObserver(applicationContext) { isAirplaneModeOn ->
            runOnUiThread {
                if (isAirplaneModeOn) {
                    Toast.makeText(this, "AirMode ON", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "AirMode OFF", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val screenBrightnessObserver: ScreenBrightnessObserver by lazy {
        ScreenBrightnessObserver(this, Handler(Looper.getMainLooper())) { brightness ->
            Log.w("TAG", "Screen Brightness Changed: $brightness")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestPermissions()
        startAirModeObserving()
        screenBrightnessObserver.startObserving()
        setContent {
            TestContentObserversTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AlarmScreen()
                }
            }
        }
    }

    private fun startAirModeObserving() {
        observer.startObserving(Settings.Global.getUriFor(Settings.Global.AIRPLANE_MODE_ON), false)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Остановить наблюдение
        observer.stopObserving()
        screenBrightnessObserver.stopObserving()
    }

    private val permissions = listOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
    )

    private fun requestPermissions() {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(perm), permissions.indexOf(perm) + 1)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestContentObserversTheme {
        Greeting("Android")
    }
}