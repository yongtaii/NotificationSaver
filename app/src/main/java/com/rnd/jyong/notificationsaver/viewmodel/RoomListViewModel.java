package com.rnd.jyong.notificationsaver.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository;

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

}