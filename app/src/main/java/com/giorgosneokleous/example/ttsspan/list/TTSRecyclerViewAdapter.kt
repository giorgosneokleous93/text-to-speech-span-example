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
import com.giorgosneokleous.example.ttsspan.model.TTSItem
import java.util.*

class TTSRecyclerViewAdapter(
    private val onItemClickListener: (position: Int, item: TTSItem) -> Unit
) : ListAdapter<TTSItem, TTSRecyclerViewAdapter.TTSViewHolder>(DifftilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TTSViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_tts, parent, false)

        return TTSViewHolder(view, ::onItemClicked)
    }

    override fun onBindViewHolder(holder: TTSViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun onItemClicked(position: Int) {
        val item = getItem(position)

        onItemClickListener(position, item)
    }

    class TTSViewHolder(itemView: View, onViewHolderClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.ttsItemTitle)
        private val subtitle = itemView.findViewById<TextView>(R.id.ttsItemSubtitle)
        private val id = itemView.findViewById<TextView>(R.id.ttsItemId)

        init {
            itemView.setOnClickListener { onViewHolderClicked(adapterPosition) }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: TTSItem) {
            title.text = item.title
            subtitle.text = item.caption
            id.text = "ID: ${item.id}"
        }
    }

    class DifftilCallback : DiffUtil.ItemCallback<TTSItem>() {
        override fun areItemsTheSame(oldItem: TTSItem, newItem: TTSItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TTSItem, newItem: TTSItem): Boolean {
            return Objects.deepEquals(oldItem, newItem)
        }

    }
}

