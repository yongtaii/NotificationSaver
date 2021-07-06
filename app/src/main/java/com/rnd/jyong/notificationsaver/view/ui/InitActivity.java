package com.rnd.jyong.notificationsaver.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;

import java.util.ArrayList;
import java.util.Set;

public class InitActivity extends AppCompatActivity {

    TextView tvAllowed;
    Button btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_init);

        // for test
//        setTestDataset();

        tvAllowed = findViewById(R.id.tvAllowed);
        btnSetting = findViewById(R.id.btnSetting);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
            }
        });

        if(JPreference.getIsFirst()){
            JPreference.setIsFirst(false);
            setGuide();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isNotiPermissionAllowed()){
            startActivity(new Intent(this,RoomListActivity.class));
            this.finish();
        }
    }

    private boolean isNotiPermissionAllowed() {
        Set<String> notiListenerSet = NotificationManagerCompat.getEnabledListenerPackages(this);
        String myPackageName = getPackageName();

        for(String packageName : notiListenerSet) {
            if(packageName == null) {
                continue;
            }
            if(packageName.equals(myPackageName)) {
                return true;
            }
        }

        return false;
    }

    private void setTestDataset(){

        long yesyesterday = System.currentTimeMillis() - (1000*60*60*24*2);
        long yesterday = System.currentTimeMillis() - (1000*60*60*24);

        ArrayList dataset = new ArrayList();
        // muji
        NotiMessage notiMessage1 = new NotiMessage(new NotiMessage("t1",CommonUtil.getStringArray(R.array.example_msg_text)[0],
                "t1",System.currentTimeMillis(),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_muji)),new byte[0]));
        //apeach
        NotiMessage notiMessage2 = new NotiMessage(new NotiMessage("t6",CommonUtil.getStringArray(R.array.example_msg_text)[1],
                "t6",System.currentTimeMillis(),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_apeach)),new byte[0]));
        //tube
        NotiMessage notiMessage3 = new NotiMessage(new NotiMessage("t5",CommonUtil.getStringArray(R.array.example_msg_text)[2],
                "t5",System.currentTimeMillis(),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_tube)),new byte[0]));
        NotiMessage notiMessage4 = new NotiMessage(new NotiMessage("t4",CommonUtil.getStringArray(R.array.example_msg_text)[3],
                "t4",System.currentTimeMillis(),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_tube)),new byte[0]));
        NotiMessage notiMessage5 = new NotiMessage(new NotiMessage("t3",CommonUtil.getStringArray(R.array.example_msg_text)[4],
                "t3",System.currentTimeMillis(),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_tube)),new byte[0]));
        // neo
        NotiMessage notiMessage6 = new NotiMessage(new NotiMessage("t2",CommonUtil.getStringArray(R.array.example_msg_text)[5],
                "t2",System.currentTimeMillis(),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_neo)),new byte[0]));

        NotiMessageRepository repository = new NotiMessageRepository(getApplication(),null);

        repository.insert(notiMessage1);
        repository.insert(notiMessage2);
        repository.insert(notiMessage3);
        repository.insert(notiMessage4);
        repository.insert(notiMessage5);
        repository.insert(notiMessage6);

    }

    private void setGuide(){

        //guide
        NotiMessage notiMessage1 = new NotiMessage(new NotiMessage(getString(R.string.app_name),CommonUtil.getStringArray(R.array.example_msg_howtouse_text)[0],
                getString(R.string.example_msg_howtouse_name),System.currentTimeMillis()-(1000*60*5),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher_round)),new byte[0]));
        NotiMessage notiMessage2 = new NotiMessage(new NotiMessage(getString(R.string.app_name),CommonUtil.getStringArray(R.array.example_msg_howtouse_text)[1],
                getString(R.string.example_msg_howtouse_name),System.currentTimeMillis(),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher_round)),new byte[0]));

        NotiMessageRepository repository = new NotiMessageRepository(getApplication(),null);

        repository.insert(notiMessage1);
        repository.insert(notiMessage2);

    }
}
