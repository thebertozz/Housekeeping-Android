package it.thebertozz.android.housekeeping.objectdetection.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.thebertozz.android.housekeeping.databinding.ItemClassificationResultBinding
import it.thebertozz.android.housekeeping.objectdetection.CategoryLabelClickListener
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.vision.classifier.Classifications
import kotlin.math.min

/**
Adapter per la Reclycler View che mostra i risultati della classificazione e gestisce il tap
 sull'elemento tramite listener
 */

class ClassificationResultsAdapter(categoryLabelClickListener: CategoryLabelClickListener?) :
    RecyclerView.Adapter<ClassificationResultsAdapter.ViewHolder>() {
    private var onClickListener: CategoryLabelClickListener? = null

    init {
        onClickListener = categoryLabelClickListener
    }

    companion object {
        private const val NO_VALUE = "--"
    }

    private var categories: MutableList<Category?> = mutableListOf()
    private var adapterSize: Int = 0

    fun updateResults(listClassifications: List<Classifications>?) {
        categories = MutableList(adapterSize) { null }
        listClassifications?.let { it ->
            if (it.isNotEmpty()) {
                val sortedCategories = it[0].categories.sortedBy { it?.index }
                val min = min(sortedCategories.size, categories.size)
                for (i in 0 until min) {
                    categories[i] = sortedCategories[i]
                }
            }
        }
    }

    fun updateAdapterSize(size: Int) {
        adapterSize = size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClassificationResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        categories[position].let { category ->
            holder.bind(category?.label, category?.score)
        }
    }

    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(private val binding: ItemClassificationResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentLabel: String? = null

        init {
            itemView.setOnClickListener {
                currentLabel?.let {
                    onClickListener?.onLabelSelected(it)
                }
            }
        }

        fun bind(label: String?, score: Float?) {
            currentLabel = label
            with(binding) {
                tvLabel.text = label ?: NO_VALUE
                tvScore.text = if (score != null) String.format("%.2f", score) else NO_VALUE
            }
        }
    }
}
