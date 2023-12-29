package com.rnd.jyong.notificationsaver.viewmodel;

import android.app.Application;

import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;

public class IgnoreListVIewModel extends AndroidViewModel {

    private List<NotiMessage> ignoreList;

    public IgnoreListVIewModel(Application application) {
        super(application);
        ignoreList = JPreference.getIgnoreList();
    }

    public List<NotiMessage> getMessagesByRoomname() {
        return ignoreList;
    }

}
