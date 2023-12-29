package com.rnd.jyong.notificationsaver.ui.components.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rnd.jyong.notificationsaver.database.notification.entity.Message
import com.rnd.jyong.notificationsaver.databinding.ItemMainRowBinding
import com.rnd.jyong.notificationsaver.ui.main.data.MainItemViewData

class MainPagingAdapter : PagingDataAdapter<MainItemViewData, MainPagingAdapter.MainViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MainPagingAdapter.MainViewHolder, position: Int) {
        val mainItemViewData = getItem(position)
        mainItemViewData?.let {
            holder.bind(it)
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(mainItemViewData) }
            }
            holder.itemView.setOnLongClickListener {
                onItemLongClickListener?.let { it(mainItemViewData) }
                return@setOnLongClickListener true
            }
        }
//        val tile = getItem(position)
//        if (tile != null) {
//            holder.bind(tile)
//        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainPagingAdapter.MainViewHolder {
        return MainPagingAdapter.MainViewHolder(
            ItemMainRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    private var onItemClickListener: ((MainItemViewData) -> Unit)? = null
    private var onItemLongClickListener: ((MainItemViewData) -> Unit)? = null
//
    fun setOnItemClickListener(listener: (MainItemViewData) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (MainItemViewData) -> Unit) {
        onItemLongClickListener = listener
    }

    object ARTICLE_DIFF_CALLBACK : DiffUtil.ItemCallback<MainItemViewData>() {
        override fun areItemsTheSame(oldItem: MainItemViewData, newItem: MainItemViewData): Boolean {
            return oldItem.time == newItem.time && oldItem.groupName == newItem.groupName && oldItem.message == newItem.message
        }

        override fun areContentsTheSame(oldItem: MainItemViewData, newItem: MainItemViewData): Boolean {
            return oldItem == newItem
        }
    }

    class MainViewHolder(
        private val binding: ItemMainRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(mainItemViewData: MainItemViewData) {
            binding.apply {
                // icon
                Glide.with(binding.ivIcon.context)
                    .load(mainItemViewData.icon)
                    .into(binding.ivIcon)

                binding.tvRoomname.text = mainItemViewData.groupName
                binding.tvMsg.text = mainItemViewData.message
                binding.tvTime.text = mainItemViewData.time

            }
        }
    }

}