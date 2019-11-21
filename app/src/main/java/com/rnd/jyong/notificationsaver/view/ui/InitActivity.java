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
        // setTestDataset();

        tvAllowed = findViewById(R.id.tvAllowed);
        btnSetting = findViewById(R.id.btnSetting);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
            }
        });

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
        NotiMessage notiMessage1 = new NotiMessage(new NotiMessage("무지","뭐해?",
                "무지",yesyesterday+1000*60*60,
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_muji))));
        //apeach
        NotiMessage notiMessage2 = new NotiMessage(new NotiMessage("어피치","우리 헤어져",
                "어피치",yesterday,
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_apeach))));
        //tube
        NotiMessage notiMessage3 = new NotiMessage(new NotiMessage("튜브","희망을 버리지 않고 계속 똑같이 충실했던 게..",
                "튜브",System.currentTimeMillis()-(1000*60*10),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_tube))));
        NotiMessage notiMessage4 = new NotiMessage(new NotiMessage("튜브","거기에 대해서 분명히 얻는 점이 있을거라고 생각해요..",
                "튜브",System.currentTimeMillis()-(1000*60*5),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_tube))));
        NotiMessage notiMessage5 = new NotiMessage(new NotiMessage("튜브","그거를 할 때만은 정말 충실하게 했으면 좋겠어요..",
                "튜브",System.currentTimeMillis(),
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_tube))));
        // neo
        NotiMessage notiMessage6 = new NotiMessage(new NotiMessage("네오","차단",
                "네오",yesyesterday+1000*60*60+1000*60*10,
                "kakao",CommonUtil.getBytes(BitmapFactory.decodeResource(getResources(),
                R.drawable.test_neo))));

        NotiMessageRepository repository = new NotiMessageRepository(getApplication(),null);

        repository.insert(notiMessage1);
        repository.insert(notiMessage2);
        repository.insert(notiMessage3);
        repository.insert(notiMessage4);
        repository.insert(notiMessage5);
        repository.insert(notiMessage6);


    }
}
