package codeasus.projects.bank.eco.feature.crypto.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codeasus.projects.bank.eco.domain.remote.model.crypto.ChartTimeframe
import codeasus.projects.bank.eco.domain.remote.model.crypto.PricePoint
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.Zoom
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.lineSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.data.ExtraStore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val timestampKey = ExtraStore.Key<List<Long>>()

@Composable
fun CoinLineChart(
    modifier: Modifier = Modifier,
    data: List<PricePoint>,
    timeframe: ChartTimeframe,
    showAxis: Boolean = true
) {
    if (data.isEmpty()) return

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(data, timeframe) {
        modelProducer.runTransaction {
            lineSeries { series(y = data.map { it.price }) }
            extras { store -> store[timestampKey] = data.map { it.timestamp } }
        }
    }

    CoinLineChartContent(
        modifier = modifier,
        modelProducer = modelProducer,
        data = data,
        timeframe = timeframe,
        showAxis = showAxis
    )
}

@Composable
private fun CoinLineChartContent(
    modifier: Modifier = Modifier,
    modelProducer: CartesianChartModelProducer,
    data: List<PricePoint>,
    timeframe: ChartTimeframe,
    showAxis: Boolean
) {
    val dateFormat = remember(timeframe) {
        when (timeframe) {
            ChartTimeframe.HOUR -> SimpleDateFormat("HH:mm", Locale.getDefault())
            ChartTimeframe.DAY -> SimpleDateFormat("HH:mm", Locale.getDefault())
            ChartTimeframe.WEEK -> SimpleDateFormat("EEE d", Locale.getDefault())
        }
    }

    val bottomAxisFormatter = CartesianValueFormatter { context, x, _ ->
        val timestamps = context.model.extraStore.getOrNull(timestampKey)
            ?: return@CartesianValueFormatter ""
        val index = x.toInt().coerceIn(timestamps.indices)
        dateFormat.format(Date(timestamps[index]))
    }

    val startAxisFormatter = CartesianValueFormatter { _, y, _ ->
        when {
            y >= 1_000 -> "$${"%.1f".format(y / 1_000)}K"
            else -> "$${"%,.2f".format(y)}"
        }
    }

    val lineColor = Color(0xFFB1AAE7)

    val tickSpacing = when (timeframe) {
        ChartTimeframe.HOUR -> maxOf(1, data.size / 6)
        ChartTimeframe.DAY -> maxOf(1, data.size / 6)
        ChartTimeframe.WEEK -> maxOf(1, data.size / 7)
    }

    val startAxis = if (showAxis) VerticalAxis.rememberStart(
        valueFormatter = startAxisFormatter,
        itemPlacer = VerticalAxis.ItemPlacer.count({ 2 }),
        horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
        guideline = null
    ) else null

    CartesianChartHost(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(Fill(lineColor)),
                        areaFill = LineCartesianLayer.AreaFill.single(
                            Fill(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        lineColor.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                )
                            )
                        ),
                        interpolator = LineCartesianLayer.Interpolator.catmullRom(),
                    ),
                )
            ),
            startAxis = startAxis
//            bottomAxis = HorizontalAxis.rememberBottom(
//                valueFormatter = bottomAxisFormatter,
//                itemPlacer = HorizontalAxis.ItemPlacer.aligned(
//                    spacing = { tickSpacing },
//                    addExtremeLabelPadding = false,
//                ),
//                guideline = null,
//                tick = null
//            ),
        ),
        modelProducer = modelProducer,
        zoomState = rememberVicoZoomState(
            zoomEnabled = false,
            initialZoom = Zoom.Content
        )
    )
}


private val previewData = run {
    val now = System.currentTimeMillis()
    List(24) { i ->
        PricePoint(
            timestamp = now - (23 - i) * 3_600_000L,
            price = 45_000.0 + (Math.random() - 0.5) * 2_000.0
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinLineChartPreviewHour() {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries { series(y = previewData.map { it.price }) }
            extras { it[timestampKey] = previewData.map { it.timestamp } }
        }
    }
    CoinLineChartContent(
        modelProducer = modelProducer,
        data = previewData,
        timeframe = ChartTimeframe.HOUR,
        showAxis = true
    )
}

//@Preview(showBackground = true)
//@Composable
//private fun CoinLineChartPreviewDay() {
//    val modelProducer = remember { CartesianChartModelProducer() }
//    LaunchedEffect(Unit) {
//        modelProducer.runTransaction {
//            lineSeries { series(y = previewData.map { it.price }) }
//            extras { it[timestampKey] = previewData.map { it.timestamp } }
//        }
//    }
//    CoinLineChartContent(
//        modelProducer = modelProducer,
//        data = previewData,
//        timeframe = ChartTimeframe.DAY
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun CoinLineChartPreviewWeek() {
//    val modelProducer = remember { CartesianChartModelProducer() }
//    LaunchedEffect(Unit) {
//        modelProducer.runTransaction {
//            lineSeries { series(y = previewData.map { it.price }) }
//            extras { it[timestampKey] = previewData.map { it.timestamp } }
//        }
//    }
//    CoinLineChartContent(
//        modelProducer = modelProducer,
//        data = previewData,
//        timeframe = ChartTimeframe.WEEK
//    )
//}