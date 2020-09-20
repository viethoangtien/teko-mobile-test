package com.teko.hoangviet.androidtest.custom.datepicker

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.LinearLayout
import com.teko.hoangviet.androidtest.extension.color
import com.teko.hoangviet.androidtest.R
import java.util.*

class DatePickerDialogWidget(var context: Context, var onDateListener: (Int, Int, Int) -> Unit) :
    DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onDateListener.invoke(year, month + 1, dayOfMonth)
    }

    val calendar = Calendar.getInstance(TimeZone.getDefault())
    fun showDatePickerDialog() {
        var dialog = DatePickerDialog(
            context,
            R.style.DatePickerTheme,
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.setOnShowListener {
            val btnPositive = dialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
            val btnNegative = dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            val param = btnPositive.layoutParams as LinearLayout.LayoutParams
            param.marginStart = context.resources.getDimensionPixelSize(R.dimen.margin_10_dp)
            btnPositive.layoutParams = param
            btnPositive.apply {
                setTextColor(context.color(R.color.colorPrimary))
            }
            btnNegative.apply {
                setTextColor(context.color(R.color.colorAccent))
            }
        }
        dialog.show()
    }
}