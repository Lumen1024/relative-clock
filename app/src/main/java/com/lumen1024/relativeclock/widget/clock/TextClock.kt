package com.lumen1024.relativeclock.widget.clock

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceComposable
import com.lumen1024.relativeclock.screen.toClockFormat
import com.lumen1024.relativeclock.tools.instantToStringWithPattern
import java.time.Duration
import java.time.Instant

@Composable
fun TextClock(
    modifier: Modifier = Modifier,
    time: Duration,
    wakeUpTime: Instant?
) {
    Column(
        modifier = modifier
            .border(
                BorderStroke(4.dp, MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(30.dp)
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(

            text = time.toClockFormat(),
            fontSize = 48.sp,
            fontWeight = FontWeight.W600
        )
        if (wakeUpTime != null)
            Text(
                text = "wake up at " + instantToStringWithPattern(wakeUpTime, pattern = "HH:mm"),
                color = MaterialTheme.colorScheme.outline

            )
    }

}