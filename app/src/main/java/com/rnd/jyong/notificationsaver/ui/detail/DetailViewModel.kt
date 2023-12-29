package com.rnd.jyong.notificationsaver.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.database.notification.entity.Message
import com.rnd.jyong.notificationsaver.database.notification.repository.MessageRepository
import com.rnd.jyong.notificationsaver.ui.components.HeaderViewData
import com.rnd.jyong.notificationsaver.ui.components.SingleLiveEvent
import com.rnd.jyong.notificationsaver.ui.detail.data.DetailItemViewData
import com.rnd.jyong.notificationsaver.ui.main.data.MainItemViewData
import com.rnd.jyong.notificationsaver.ui.main.toMainItemViewList
import com.rnd.jyong.notificationsaver.utils.ImageUtils
import com.rnd.jyong.notificationsaver.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val application: Application,
) : AndroidViewModel(application) {

    private var _datas = MutableStateFlow<List<DetailItemViewData>>(emptyList())
    val datas = _datas.asStateFlow()

    private val _onPrev = SingleLiveEvent<Boolean>()
    var onPrev = _onPrev

    val headerViewData = HeaderViewData(titleText = application.getString(R.string.main_title), onLeftClickListener = { _onPrev.value = true}, isVisibleRightBtn = false)

    fun initData(groupName : String?){
        viewModelScope.launch {
            if (!groupName.isNullOrEmpty()){
                messageRepository.getGroupMessages(groupName).collectLatest {
                    _datas.emit(it.toDetailItemViewList())
                }
            }
        }
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