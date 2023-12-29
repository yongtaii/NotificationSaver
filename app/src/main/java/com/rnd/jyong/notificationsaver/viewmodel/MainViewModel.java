package com.rnd.jyong.notificationsaver.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private String roomname;
    private final NotiMessageRepository repository;
    private LiveData<List<NotiMessage>> msgLiveDataByRoomname;

    public MainViewModel(Application application, String roomname) {
        super(application);
        this.roomname = roomname;
        repository = new NotiMessageRepository(application, roomname);
        msgLiveDataByRoomname = repository.getNotiMessagesByRoomname(roomname);
    }

    public LiveData<List<NotiMessage>> getMessagesByRoomname() {
        return msgLiveDataByRoomname;
    }

}
