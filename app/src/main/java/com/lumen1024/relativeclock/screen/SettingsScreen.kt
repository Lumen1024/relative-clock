package com.lumen1024.relativeclock.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import com.lumen1024.relativeclock.data.RelativeClockRepository
import com.lumen1024.relativeclock.tools.durationToString
import com.lumen1024.relativeclock.tools.durationToTimeString
import com.lumen1024.relativeclock.tools.instantToStringWithPattern
import com.lumen1024.relativeclock.widget.TimePickerDialog
import java.time.Duration
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    var timePickerDialogOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var offset : Duration? by remember { mutableStateOf(RelativeClockRepository.getTimeOffset(context)) }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Time offset")
            Button(onClick = { timePickerDialogOpen = true }) {
                Text(
                    text = offset?.let { durationToTimeString(it) } ?: "set"
                )
            }
        }


        if (timePickerDialogOpen) {
            TimePickerDialog(
                onCancel = { timePickerDialogOpen = false },
                onConfirm = {
                    val stringTime = instantToStringWithPattern(it.time.toInstant(), "HH:mm")
                    val hours = stringTime.substring(0,2).toLong()
                    val minutes = stringTime.substring(3,5).toLong()

                    val pickedTime = Duration.ofHours(hours) + Duration.ofMinutes(minutes)
                    RelativeClockRepository.setTimeOffset(pickedTime, context)
                    offset = pickedTime
                },
            )
        }
    }
}