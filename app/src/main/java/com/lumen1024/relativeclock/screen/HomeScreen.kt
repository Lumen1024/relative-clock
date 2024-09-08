package com.lumen1024.relativeclock.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.lumen1024.relativeclock.data.RelativeClockRepository
import com.lumen1024.relativeclock.widget.TimePickerDialog
import com.lumen1024.relativeclock.widget.clock.TextClock
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.Instant

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    var timePickerDialogOpen by remember { mutableStateOf(false) }

    val repository = RelativeClockRepository
    val context = LocalContext.current

    var time by remember() {
        mutableStateOf(repository.getRelativeTime(context))
    }

    var wakeUpTime by remember {
        mutableStateOf(repository.getWakeTime(context))
    }

    val updateTime = {
        time = repository.getRelativeTime(context)
        wakeUpTime = repository.getWakeTime(context)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(10 * 1000)
            updateTime()
        }
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        TextClock(time = time, wakeUpTime = wakeUpTime)
        HorizontalDivider(modifier.padding(vertical = 30.dp))
        OutlinedButton(
            onClick = {
                repository.setWakeTime(Instant.now(), context)
                updateTime()
            }
        ) {
            Text(text = "I wake up")
        }

        OutlinedButton(
            onClick = {
                timePickerDialogOpen = true
            }
        ) {
            Text(text = "I wake up but forget")
        }
    }

    if (timePickerDialogOpen) {
        TimePickerDialog(
            onCancel = { timePickerDialogOpen = false },
            onConfirm = { repository.setWakeTime(it.time.toInstant(), context); updateTime() },
        )
    }
}

@SuppressLint("DefaultLocale")
fun Duration.toClockFormat(): String {
    val hours = this.toHours()
    val minutes = this.toMinutes() % 60

    return String.format("%02d:%02d", hours, minutes)
}