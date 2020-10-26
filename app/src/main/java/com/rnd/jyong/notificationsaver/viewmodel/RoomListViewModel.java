package com.rnd.jyong.notificationsaver.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;

import java.util.List;

public class RoomListViewModel extends AndroidViewModel {

    private final NotiMessageRepository repository;
    private LiveData<List<NotiMessage>> allRoomListLiveData;

    public RoomListViewModel(Application application) {
        super(application);
        repository = new NotiMessageRepository(application,null);
        allRoomListLiveData = repository.getAllRoomList();
    }

    public LiveData<List<NotiMessage>> getAllNotiMessages() {
        return allRoomListLiveData;
    }

    public void deleteAllNotiMessages() {
        repository.deleteAll();
    }

    public void deleteMessageWithTime(long time) {
        repository.deleteMessageWithTime(time);
    }

    public void getAppVersion(ValueEventListener listener){

        if(!CommonUtil.checkLastUpdateDiaogTime()) return;

        JPreference.setLastUpdateDialogTime(System.currentTimeMillis());
        Query versionQuery = FirebaseDatabase.getInstance().getReference().child("app_version");
        versionQuery.addListenerForSingleValueEvent(listener);
    }


}