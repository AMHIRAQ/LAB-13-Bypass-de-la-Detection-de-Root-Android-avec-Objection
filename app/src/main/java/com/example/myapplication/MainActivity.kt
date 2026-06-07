package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.scottyab.rootbeer.RootBeer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootCheckScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun RootCheckScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val rootBeer = remember { RootBeer(context) }
    val isRooted = rootBeer.isRooted

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Root Detection Status",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (isRooted) "ROOT DETECTED" else "NOT ROOTED",
            fontSize = 20.sp,
            color = if (isRooted) Color.Red else Color.Green,
            fontWeight = FontWeight.Black
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(text = "Details:", fontWeight = FontWeight.SemiBold)
        Text(text = "isRooted: $isRooted")
        Text(text = "Root Management Apps: ${rootBeer.detectRootManagementApps()}")
        Text(text = "Potentially Dangerous Apps: ${rootBeer.detectPotentiallyDangerousApps()}")
        Text(text = "Test Keys: ${rootBeer.detectTestKeys()}")
        Text(text = "BusyBox Binary: ${rootBeer.checkForBusyBoxBinary()}")
        Text(text = "Su Binary: ${rootBeer.checkForSuBinary()}")
        Text(text = "RW Paths: ${rootBeer.checkForRWPaths()}")
        Text(text = "Dangerous Props: ${rootBeer.checkForDangerousProps()}")
        Text(text = "Root Native: ${rootBeer.checkForRootNative()}")
        Text(text = "Magisk Specific: ${rootBeer.checkForMagiskBinary()}")
    }
}

@Preview(showBackground = true)
@Composable
fun RootCheckPreview() {
    MyApplicationTheme {
        RootCheckScreen()
    }
}
