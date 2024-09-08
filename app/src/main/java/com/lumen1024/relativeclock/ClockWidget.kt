package com.lumen1024.relativeclock

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import com.lumen1024.relativeclock.screen.toClockFormat
import com.lumen1024.relativeclock.tools.instantToStringWithPattern
import java.time.Duration
import java.time.Instant

class ClockWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ClockWidget()
}
class ClockWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            GlanceTextClock(time = Duration.ofHours(5), wakeUpTime = null)
        }
    }
}

@GlanceComposable
@Composable
fun GlanceTextClock(
    modifier: GlanceModifier = GlanceModifier,
    time: Duration,
    wakeUpTime: Instant?
) {
    Column(
        modifier = modifier.fillMaxSize().cornerRadius(5.dp)
//            .border(
//                BorderStroke(4.dp, MaterialTheme.colorScheme.primary),
//                shape = RoundedCornerShape(30.dp)
//            )
            .background(MaterialTheme.colorScheme.surface),
            //.padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time.toClockFormat(),
        )
        if (wakeUpTime != null)
            androidx.compose.material3.Text(
                text = "wake up at " + instantToStringWithPattern(wakeUpTime, pattern = "hh:mm"),
                color = MaterialTheme.colorScheme.outline

            )
    }

}