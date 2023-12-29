package com.rnd.jyong.notificationsaver.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.database.notification.entity.Message
import com.rnd.jyong.notificationsaver.database.notification.repository.MessageRepository
import com.rnd.jyong.notificationsaver.ui.components.HeaderViewData
import com.rnd.jyong.notificationsaver.ui.components.SingleLiveEvent
import com.rnd.jyong.notificationsaver.ui.detail.data.DetailItemViewData
import com.rnd.jyong.notificationsaver.ui.main.data.MainItemViewData
import com.rnd.jyong.notificationsaver.ui.main.toMainItemView
import com.rnd.jyong.notificationsaver.ui.main.toMainItemViewList
import com.rnd.jyong.notificationsaver.utils.ImageUtils
import com.rnd.jyong.notificationsaver.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val application: Application,
) : AndroidViewModel(application) {

//    private var _datas = MutableStateFlow<List<DetailItemViewData>>(emptyList())
//    val datas = _datas.asStateFlow()


//    var datas: StateFlow<PagingData<DetailItemViewData>> =
//        messageRepository.getGroupMessagesByPagingSource(groupName = groupName).map { pagingData ->
//            pagingData.map { it.toDetailItemVie() }
//        }.cachedIn(viewModelScope) // 코루틴이 데이터흐름을 캐시하고 공유 가능하게 만든다.
//            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())

    lateinit var datas : StateFlow<PagingData<DetailItemViewData>>

    private val _onPrev = SingleLiveEvent<Boolean>()
    var onPrev = _onPrev

    val headerViewData = HeaderViewData(onLeftClickListener = { _onPrev.value = true}, isVisibleRightBtn = false)

    fun initData(groupName : String){
//        viewModelScope.launch {
//            if (!groupName.isNullOrEmpty()){
//                messageRepository.getGroupMessages(groupName).collectLatest {
//                    _datas.emit(it.toDetailItemViewList())
//                }
//            }
//        }

        datas = messageRepository.getGroupMessagesByPagingSource(groupName = groupName).map { pagingData ->
            pagingData.map { it.toDetailItemVie() }
        }.cachedIn(viewModelScope) // 코루틴이 데이터흐름을 캐시하고 공유 가능하게 만든다.
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
    }

}

fun List<Message>.toDetailItemViewList() : List<DetailItemViewData> {
    return this.map {
        val imageData = if(it.imageBase64.isNotEmpty()) {
            ImageUtils.convertBase64ToBitmap(it.imageBase64)
        } else null
        DetailItemViewData(name = it.name, message = it.message, time = TimeUtils.convertTimeForDetailList(it.postTime),
        icon = ImageUtils.convertBase64ToBitmap(it.iconBase64), image = imageData )}
}

fun Message.toDetailItemVie() : DetailItemViewData {
        val imageData = if(this.imageBase64.isNotEmpty()) {
            ImageUtils.convertBase64ToBitmap(this.imageBase64)
        } else null
        return DetailItemViewData(name = this.name, message = this.message, time = TimeUtils.convertTimeForDetailList(this.postTime),
            icon = ImageUtils.convertBase64ToBitmap(this.iconBase64), image = imageData )
}