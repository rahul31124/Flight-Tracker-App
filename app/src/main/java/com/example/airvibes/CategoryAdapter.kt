package com.example.airvibes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CategoryAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Category, RecyclerView.ViewHolder>(CategoryDiffCallback()) {

    companion object {
        private const val TYPE_GRID = 1
        private const val TYPE_LINEAR = 2
    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).cname == "Nearby") TYPE_LINEAR else TYPE_GRID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_LINEAR) {
            val view = inflater.inflate(R.layout.recyclerlayout2, parent, false)
            LinearViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.recyclerlayout, parent, false)
            GridViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = getItem(position)
        when (holder) {
            is LinearViewHolder -> holder.bind(category)
            is GridViewHolder -> holder.bind(category)
        }
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCategory: ImageView = itemView.findViewById(R.id.categoryIcon)
        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)

        fun bind(category: Category) {
            categoryName.text = category.cname
            Glide.with(itemView.context)
                .load(category.imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .centerCrop()
                .into(imgCategory)

            itemView.setOnClickListener {
                listener.onItemClick(category)
            }
        }
    }

    inner class LinearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCategory: ImageView = itemView.findViewById(R.id.categoryIcon)
        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)

        fun bind(category: Category) {
            categoryName.text = category.cname
            Glide.with(itemView.context)
                .load(category.imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .centerCrop()
                .into(imgCategory)

            itemView.setOnClickListener {
                listener.onItemClick(category)
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}
