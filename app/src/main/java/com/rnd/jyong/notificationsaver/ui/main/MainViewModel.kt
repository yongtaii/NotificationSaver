package com.rnd.jyong.notificationsaver.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.database.notification.entity.Message
import com.rnd.jyong.notificationsaver.database.notification.repository.MessageRepository
import com.rnd.jyong.notificationsaver.ui.components.HeaderViewData
import com.rnd.jyong.notificationsaver.ui.components.SingleLiveEvent
import com.rnd.jyong.notificationsaver.ui.main.data.MainItemViewData
import com.rnd.jyong.notificationsaver.utils.ImageUtils
import com.rnd.jyong.notificationsaver.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
class MainViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val application: Application
) : AndroidViewModel(application) {

//    private val _goDetail: SingleLiveEvent<String>()
//    var goDetail = _goDetail
    private val _goDetail = SingleLiveEvent<String>()
    var goDetail = _goDetail

    private val _showSetting = SingleLiveEvent<Boolean>()
    var showSetting = _showSetting

    val headerViewData = HeaderViewData(titleText = application.getString(R.string.main_title), onRightClickListener = { _showSetting.value = true}, isVisibleLeftBtn = false)

//    private var _goDetail = MutableStateFlow<String>("")
//    val goDetail = _goDetail.asStateFlow()

    val datas: StateFlow<PagingData<MainItemViewData>> =
        messageRepository.getGroupListByPagingSource().map { pagingData ->
            pagingData.map { it.toMainItemView { groupName -> _goDetail.value = groupName } }
        }.cachedIn(viewModelScope) // 코루틴이 데이터흐름을 캐시하고 공유 가능하게 만든다.
         .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
    // UI 에서 관찰해야하는 데이터이기 때문에 stateIn을 써서 StateFlow 로 만들어준다.



//    private var _mainDatas = MutableStateFlow<List<MainItemViewData>>(emptyList())
//    val mainDatas = _mainDatas.asStateFlow()

    /**
     * Item Click Listener
     * */
    private val itemClickListener = MainItemViewData.OnClickListener { groupName -> _goDetail.value = groupName }
//    private val itemClickListener = MainItemViewData.OnClickListener { groupName ->
//        viewModelScope.launch {_goDetail.emit(groupName) }
//    }

    init {

//        viewModelScope.launch {
//
//            messageRepository.getGroupList().collectLatest {
//                _mainDatas.emit(it.toMainItemViewList(itemClickListener))
//            }
//        }

    }

    fun deleteGroup(groupName : String){
        CoroutineScope(Dispatchers.IO).launch {
            messageRepository.deleteGroup(groupName)
        }
    }


}

fun List<Message>.toMainItemViewList(listener : MainItemViewData.OnClickListener) : List<MainItemViewData> {
    return this.map { MainItemViewData(groupName = it.groupName, message = it.message, time = TimeUtils.convertTimeForMainList(it.postTime),
        icon = ImageUtils.convertBase64ToBitmap(it.iconBase64), listener = listener) }
}
fun Message.toMainItemView(listener : MainItemViewData.OnClickListener) : MainItemViewData {
    return MainItemViewData(groupName = this.groupName, message = this.message, time = TimeUtils.convertTimeForMainList(this.postTime),
        icon = ImageUtils.convertBase64ToBitmap(this.iconBase64), listener = listener)
}