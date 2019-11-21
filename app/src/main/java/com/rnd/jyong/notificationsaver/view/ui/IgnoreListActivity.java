package com.rnd.jyong.notificationsaver.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;
import com.rnd.jyong.notificationsaver.databinding.ActivityIgnoreListBinding;
import com.rnd.jyong.notificationsaver.databinding.ActivityRoomListBinding;
import com.rnd.jyong.notificationsaver.view.adapter.IgnoreListAdapter;
import com.rnd.jyong.notificationsaver.view.adapter.RoomListAdapter;
import com.rnd.jyong.notificationsaver.viewmodel.IgnoreListVIewModel;
import com.rnd.jyong.notificationsaver.viewmodel.RoomListViewModel;

import java.util.ArrayList;
import java.util.List;

public class IgnoreListActivity extends AppCompatActivity {

    private IgnoreListAdapter ignoreListAdapter;
    private ActivityIgnoreListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ignore_list);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("차단");

        binding = DataBindingUtil.setContentView(this,R.layout.activity_ignore_list);
        IgnoreListVIewModel ignoreListVIewModel = new IgnoreListVIewModel(getApplication());
        binding.setViewModel(ignoreListVIewModel);
        binding.setLifecycleOwner(this);

        List<NotiMessage> ignoreList = JPreference.getIgnoreList();

        if(ignoreList == null || ignoreList.size() < 1){
//            ignoreList = new ArrayList<>();
            binding.wrapperNoList.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }else{
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ignoreListAdapter = new IgnoreListAdapter(IgnoreListActivity.this, ignoreList);
            binding.recyclerView.setAdapter(ignoreListAdapter);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
