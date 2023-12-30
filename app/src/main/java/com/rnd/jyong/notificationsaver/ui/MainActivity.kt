package com.rnd.jyong.notificationsaver.ui

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.rnd.jyong.notificationsaver.R
import com.rnd.jyong.notificationsaver.core.admob.AdmobManager
import com.rnd.jyong.notificationsaver.data.preference.JPreference
import com.rnd.jyong.notificationsaver.database.notification.entity.Message
import com.rnd.jyong.notificationsaver.database.notification.repository.MessageRepository
import com.rnd.jyong.notificationsaver.databinding.ActivityMainBinding
import com.rnd.jyong.notificationsaver.datastore.DataStoreKey
import com.rnd.jyong.notificationsaver.datastore.DataStoreManager
import com.rnd.jyong.notificationsaver.utils.FileUtils
import com.rnd.jyong.notificationsaver.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var messageRepository: MessageRepository
    @Inject lateinit var admobManager: AdmobManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initialise()
    }

    private fun initialise(){

        insertDefaultMessages()

        admobManager.init(this)

    }

    private fun deleteOldMessages(){
        CoroutineScope(Dispatchers.IO).launch {
            // 일주일 전 메시지는 삭제한다
            val time = System.currentTimeMillis() - (1000L*60L*60L*24L*7L)
            messageRepository.deleteMessageByTime(time)
        }
    }

    private fun insertDefaultMessages(){

        CoroutineScope(Dispatchers.IO).launch {

            val isFirst = DataStoreManager.get(context = applicationContext, DataStoreKey.IS_FIRST, true)
            if(isFirst){
                DataStoreManager.save(context = applicationContext, DataStoreKey.IS_FIRST, false)
                messageRepository.insert(Message(name = application.getString(R.string.my_name),
                    message = resources.getStringArray(R.array.example_msg_howtouse_text)[0],
                    groupName = application.getString(R.string.example_msg_howtouse_name),
                    iconBase64 = FileUtils.convertBitmapToBase64(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round)),
                    postTime = (System.currentTimeMillis() - (1000 * 60 * 5)),
                    imageBase64 = ""))

                messageRepository.insert(Message(name = application.getString(R.string.my_name),
                    message = resources.getStringArray(R.array.example_msg_howtouse_text)[1],
                    groupName = application.getString(R.string.example_msg_howtouse_name),
                    iconBase64 = FileUtils.convertBitmapToBase64(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round)),
                    postTime = (System.currentTimeMillis()),
                    imageBase64 = ""))
            }


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        deleteOldMessages()
    }


}