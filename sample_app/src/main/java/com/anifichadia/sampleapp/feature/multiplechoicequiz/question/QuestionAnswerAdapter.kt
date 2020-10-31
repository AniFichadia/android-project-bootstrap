package com.anifichadia.sampleapp.feature.multiplechoicequiz.question

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anifichadia.sampleapp.databinding.ListItemMultipleChoiceQuizAnswerBinding
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionAnswerAdapter.AnswerViewHolder
import kotlin.properties.Delegates

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class QuestionAnswerAdapter : RecyclerView.Adapter<AnswerViewHolder>() {

    // Just use a simple observable that resets the notifies the data changes for the whole adapter for now
    var items: List<String> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChanged() }
    var selectedIndex: Int by Delegates.observable(-1) { _, _, _ -> notifyDataSetChanged() }
    var answersLocked: Boolean by Delegates.observable(false) { _, _, _ -> notifyDataSetChanged() }

    var itemClickListener: ((Int) -> Unit)? = null


    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemMultipleChoiceQuizAnswerBinding.inflate(inflater, parent, false)

        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(items[position], position == selectedIndex, answersLocked)
    }


    inner class AnswerViewHolder(
        private val binding: ListItemMultipleChoiceQuizAnswerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(potentialAnswer: String, selected: Boolean, locked: Boolean) {
            binding.listItemMultipleChoiceQuizAnswerBtnAnswer.setOnClickListener {
                if (!locked) {
                    itemClickListener?.invoke(adapterPosition)
                }
            }

            with(binding.listItemMultipleChoiceQuizAnswerBtnAnswer) {
                text = potentialAnswer

                isClickable = true
                if (selected) {
                    isSelected = true
                    isEnabled = true
                    isClickable = false
                } else {
                    isSelected = false
                    isEnabled = !locked
                }
            }
        }
    }
}
