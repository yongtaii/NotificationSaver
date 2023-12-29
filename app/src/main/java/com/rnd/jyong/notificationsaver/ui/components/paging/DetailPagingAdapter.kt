package com.rnd.jyong.notificationsaver.ui.components.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rnd.jyong.notificationsaver.databinding.ItemDetailRowBinding
import com.rnd.jyong.notificationsaver.ui.detail.data.DetailItemViewData

class DetailPagingAdapter : PagingDataAdapter<DetailItemViewData, DetailPagingAdapter.DetailViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: DetailPagingAdapter.DetailViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailPagingAdapter.DetailViewHolder {
        return DetailPagingAdapter.DetailViewHolder(
            ItemDetailRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

//    private var onItemClickListener: ((MainItemViewData) -> Unit)? = null
//    //
//    fun setOnItemClickListener(listener: (MainItemViewData) -> Unit) {
//        onItemClickListener = listener
//    }

    object ARTICLE_DIFF_CALLBACK : DiffUtil.ItemCallback<DetailItemViewData>() {
        override fun areItemsTheSame(oldItem: DetailItemViewData, newItem: DetailItemViewData): Boolean {
            return oldItem.time == newItem.time && oldItem.name == newItem.name && oldItem.message == newItem.message
        }

        override fun areContentsTheSame(oldItem: DetailItemViewData, newItem: DetailItemViewData): Boolean {
            return oldItem == newItem
        }
    }

    class DetailViewHolder(
        private val binding: ItemDetailRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(detailItemViewData: DetailItemViewData) {
            binding.apply {
                // icon
                Glide.with(binding.ivIcon.context)
                    .load(detailItemViewData.icon)
                    .into(binding.ivIcon)

                // name
                binding.tvName.text = detailItemViewData.name

                binding.ivImage.visibility = if (detailItemViewData.isImageType) View.VISIBLE else View.GONE
                binding.tvTimeWithImage.visibility = if (detailItemViewData.isImageType) View.VISIBLE else View.GONE
                binding.tvMsg.visibility = if (detailItemViewData.isImageType) View.GONE else View.VISIBLE
                binding.tvTime.visibility = if (detailItemViewData.isImageType) View.GONE else View.VISIBLE

                if(detailItemViewData.isImageType){
                    //image type

                    Glide.with(binding.ivImage.context)
                        .load(detailItemViewData.image)
                        .into(binding.ivImage)

                    binding.tvTimeWithImage.text = detailItemViewData.time

                }else{
                    //text type
                    binding.tvMsg.text = detailItemViewData.message
                    binding.tvTime.text = detailItemViewData.time
                }

            }
        }
    }

}