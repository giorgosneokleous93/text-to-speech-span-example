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

import android.text.style.TtsSpan

object DummyDataFactory {

    fun getListOfTTSItem(): List<TtsItem> = listOf(
        TtsItem("18/04/2020", "Date without TTSSpan", null),
        TtsItem("18/04/2020", "Date with TtsSpan.DateBuilder", TtsSpan.TYPE_DATE),

        TtsItem("5 meter", "Measure without TTSSpan", null),
        TtsItem("5 meter", "Measure with TTSSpan", TtsSpan.TYPE_MEASURE),

        TtsItem("14:00", "Time without TTSSpan", null),
        TtsItem("14:00", "Time with TTSSpan", TtsSpan.TYPE_TIME),

        TtsItem("admin:123456789", "Password without TTSSpan", null),
        TtsItem("admin:123456789", "Password with TTSSpan", TtsSpan.TYPE_ELECTRONIC)
    ).also { list ->
        list.forEachIndexed { index, ttsItem -> ttsItem.id = index }
    }
}
