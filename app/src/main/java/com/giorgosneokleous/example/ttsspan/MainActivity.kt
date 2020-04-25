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

package com.giorgosneokleous.example.ttsspan

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giorgosneokleous.example.ttsspan.list.TtsRecyclerViewAdapter
import com.giorgosneokleous.example.ttsspan.listener.OnTtsItemClickListener
import com.giorgosneokleous.example.ttsspan.model.DummyDataFactory
import com.giorgosneokleous.example.ttsspan.model.TtsItem
import java.util.*

class MainActivity : AppCompatActivity(), OnTtsItemClickListener {

    private lateinit var ttsAdapter: TtsRecyclerViewAdapter
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTextToSpeech()

        ttsAdapter = TtsRecyclerViewAdapter(this)

        findViewById<RecyclerView>(R.id.ttsRecyclerView).apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context)
            adapter = ttsAdapter
        }

        ttsAdapter.submitList(DummyDataFactory.getListOfTTSItem())
    }

    override fun onDestroy() {
        super.onDestroy()

        textToSpeech.shutdown()
    }

    override fun onTtsItemClicked(item: TtsItem) {
        Toast.makeText(this, "Item ${item.id} clicked", Toast.LENGTH_LONG).show()
        textToSpeech.speak(
            item.toSpannable(),
            TextToSpeech.QUEUE_ADD,
            null,
            "item_id_${item.id}"
        )
    }

    private fun setupTextToSpeech() {
        textToSpeech = TextToSpeech(this) { status ->
            val message = if (status == TextToSpeech.SUCCESS)
                "Initialization of TextToSpeech was successful"
            else
                "Initialization of TextToSpeech has failed, please restart activity"

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            textToSpeech.voice = Voice(
                "custom_voice",
                Locale.UK,
                Voice.QUALITY_VERY_HIGH,
                Voice.LATENCY_VERY_HIGH,
                false,
                mutableSetOf()
            )
        }
    }
}
