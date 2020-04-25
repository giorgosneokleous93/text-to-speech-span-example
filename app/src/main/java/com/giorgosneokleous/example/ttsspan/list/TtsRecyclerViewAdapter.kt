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

package com.giorgosneokleous.example.ttsspan.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.giorgosneokleous.example.ttsspan.R
import com.giorgosneokleous.example.ttsspan.listener.OnTtsItemClickListener
import com.giorgosneokleous.example.ttsspan.model.TtsItem
import java.util.*

class TtsRecyclerViewAdapter(
    private val onTtsItemClickListener: OnTtsItemClickListener
) : ListAdapter<TtsItem, TtsRecyclerViewAdapter.TTSViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TTSViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tts, parent, false)

        return TTSViewHolder(view) { position ->
            onTtsItemClickListener.onTtsItemClicked(getItem(position))
        }
    }

    override fun onBindViewHolder(holder: TTSViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TTSViewHolder(
        itemView: View,
        callback: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.ttsItemTitle)
        private val subtitle = itemView.findViewById<TextView>(R.id.ttsItemSubtitle)
        private val id = itemView.findViewById<TextView>(R.id.ttsItemId)

        init {
            itemView.setOnClickListener {
                callback(adapterPosition)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: TtsItem) {
            title.clearComposingText()
            title.text = item.toSpannable()
            subtitle.text = item.caption
            id.text = "ID: ${item.id}"
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<TtsItem>() {
        override fun areItemsTheSame(oldItem: TtsItem, newItem: TtsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TtsItem, newItem: TtsItem): Boolean {
            return Objects.deepEquals(oldItem, newItem)
        }

    }
}

