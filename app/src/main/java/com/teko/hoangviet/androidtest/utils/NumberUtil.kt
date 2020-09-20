package com.teko.hoangviet.androidtest.utils

import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

object NumberUtil {
    val formatter = (DecimalFormat.getInstance() as DecimalFormat).apply {
        decimalFormatSymbols = DecimalFormatSymbols().apply {
            decimalSeparator = '.'
        }
    }

    fun formatValue(value: String): String {
        try {
            return formatter.format(removeSpecialCharacters(value).toInt())
        } catch (e: NumberFormatException) {
            return ""
        }
    }

    private fun removeSpecialCharacters(value: String): Double {
        val resultString = StringBuilder("")
        for (i in value) {
            if (i.isDigit()) {
                resultString.append(i)
            }
        }
        return resultString.toString().toDouble()
    }
}