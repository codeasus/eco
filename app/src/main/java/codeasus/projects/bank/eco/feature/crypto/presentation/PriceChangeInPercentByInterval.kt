package codeasus.projects.bank.eco.feature.crypto.presentation

import codeasus.projects.bank.eco.domain.remote.model.crypto.ChartTimeframe

enum class PriceChangeInPercentByInterval(val text: String) {
    HOUR("1h"),
    DAY("24h"),
    WEEK("7d");

    fun toChartTimeframe(): ChartTimeframe {
        return when (this) {
            HOUR -> ChartTimeframe.HOUR
            DAY -> ChartTimeframe.DAY
            WEEK -> ChartTimeframe.WEEK
        }
    }
}