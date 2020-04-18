/*
 * MIT License
 *
 * Copyright (c) 2020 Giorgos Neokleous
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.giorgosneokleous.example.ttsspan.model

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import java.text.SimpleDateFormat
import java.util.*

data class TTSItem(
    val title: String,
    val caption: String,
    private val ttsSpanType: String?
) {

    var id: Int = 0

    fun toSpannable(): SpannableString? {
        val ttsSpanBuilder = when (ttsSpanType) {
            TtsSpan.TYPE_DATE -> {
                val calendar = Calendar.getInstance()
                calendar.time = simpleDataFormat.parse(title)
                    ?: throw IllegalStateException("Not expected null Date")

                TtsSpan.DateBuilder()
                    .setWeekday(calendar.get(Calendar.DAY_OF_WEEK))
                    .setDay(calendar.get(Calendar.DAY_OF_MONTH))
                    .setMonth(calendar.get(Calendar.MONTH))
                    .setYear(calendar.get(Calendar.YEAR))
            }
            TtsSpan.TYPE_MEASURE -> {
                val number = digitsPattern.find(title)?.value
                val unit = stringPattern.find(title)?.value
                TtsSpan.MeasureBuilder()
                    .setNumber(number)
                    .setUnit(unit)
            }
            TtsSpan.TYPE_TIME -> {
                val hours = title.split(":")[0]
                val minutes = title.split(":")[1]
                TtsSpan.TimeBuilder()
                    .setHours(hours.toInt())
                    .setMinutes(minutes.toInt())
            }
            TtsSpan.TYPE_ELECTRONIC -> {
                val username = title.split(":")[0]
                val password = title.split(":")[1]
                TtsSpan.ElectronicBuilder()
                    .setPassword(password)
                    .setUsername(username)

            }
            else -> null
        }

        val spannable = SpannableString(title)

        ttsSpanBuilder?.let {
            val ttsSpan = ttsSpanBuilder.build()
            spannable.setSpan(ttsSpan, 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return spannable
    }
}

private val simpleDataFormat = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
private val digitsPattern = "\\d+".toRegex()
private val stringPattern = "[a-zA-Z]+".toRegex()

