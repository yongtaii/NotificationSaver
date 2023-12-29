//package com.rnd.jyong.notificationsaver.ui.components
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.annotation.LayoutRes
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import androidx.paging.PagingDataAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.rnd.jyong.notificationsaver.database.notification.entity.Message
//import java.util.Collections
//
//class GeneralPagingAdapter< T : Any, B : ViewDataBinding>(
//    @LayoutRes private val layoutResId: Int,
//    private val bindingVariableId: Int? = null,
//    private val bindingPositionVariableId: Int? = null,
//    private val diff : DiffUtil.ItemCallback<T>
//) : PagingDataAdapter<T, GeneralPagingAdapter.ViewHolder<B>>(diff) {
//
//    /**
//     * Adapter 셋 데이터 객체
//     */
////    private val datas = mutableListOf<T>()
////    fun getData() : List<T>{
////        return datas;
////    }
//
//    /**
//     * 데이터 설정
//     */
////    fun setItems(inputDatas: List<T>?) {
////        if (inputDatas.isNullOrEmpty()) {
////            return
////        }
////
////        datas.run {
////            clear()
////            addAll(inputDatas)
////            notifyDataSetChanged()
////        }
////    }
//
//    /**
//     * 데이터 추가
//     */
////    fun addItems(inputDatas: List<T>?) {
////        if (inputDatas.isNullOrEmpty()) {
////            return
////        }
////
////        datas.run {
////            addAll(inputDatas)
////            notifyDataSetChanged()
////        }
////    }
//
//
//    /**
//     * 데이터 갱신
//     */
////    fun updateItem(updateData: T?, position: Int) {
////        updateData?: return
////
////        if (datas.isNullOrEmpty() || position >= itemCount) {
////            return
////        }
////
////        datas.run {
////            set(position, updateData)
////            notifyItemChanged(position, updateData)
////        }
////    }
//
//    /**
//     * 데이터 스왑 및 애니메이션 처리
//     */
////    fun switchItem(fromPosition: Int, toPosition: Int) {
////        datas.run {
////            Collections.swap(this, fromPosition, toPosition)
////            notifyItemMoved(fromPosition, toPosition)
////        }
////    }
//
//    /**
//     * 해당 데이터의 포지션을 반환
//     */
////    fun getPosition(T: Any) : Int {
////        return datas.indexOf(T)
////    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder<B> {
//        return ViewHolder(
//            layoutResId = layoutResId,
//            parent = parent,
//            bindingVariableId = bindingVariableId,
//            bindingPositionVariableId = bindingPositionVariableId
//        )
//    }
//
////    override fun getItemCount() = datas.size
////
////    override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
////        holder.onBindViewHolder(datas[position])
////    }
//
//    /**
//     * RecyclerView 홀더 객체
//     * 데이터 바인딩 처리
//     */
//    class ViewHolder<B : ViewDataBinding>(
//        @LayoutRes layoutResId: Int,
//        parent: ViewGroup,
//        private val bindingVariableId: Int?,
//        private val bindingPositionVariableId: Int?
//    ) : RecyclerView.ViewHolder(
//        LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
//    ) {
//
//        private val binding: B = DataBindingUtil.bind(itemView)
//            ?: throw IllegalStateException("create View Holder fail : binding == null")
//
//        fun onBindViewHolder(data: Any?) {
//            try {
//                bindingVariableId?.let {
//                    binding.setVariable(it, data)
//                    binding.executePendingBindings()
//                }
//                // 위치 설정
//                bindingPositionVariableId?.let {
//                    binding.setVariable(it, bindingAdapterPosition)
//                    binding.executePendingBindings()
//                }
//            } catch (e: Exception) {
//                Log.e("GeneralBindAdapter","onBindViewHolder Exception Occurred");
////                e.printStackTrace()
//            }
//        }
//    }
//
//
//}
//
////object MESSAGE_DIFF_CALLBACK : DiffUtil.ItemCallback<Message>() {
////    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
////        return oldItem.postTime == newItem.postTime && oldItem.name == newItem.name && oldItem.message == newItem.message
////    }
////
////    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
////        return oldItem == newItem
////    }
////}