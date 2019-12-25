package com.rnd.jyong.notificationsaver.view.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.databinding.ActivityRoomListBinding;
import com.rnd.jyong.notificationsaver.view.adapter.RoomListAdapter;
import com.rnd.jyong.notificationsaver.viewmodel.RoomListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class RoomListActivity extends AppCompatActivity {

    private RoomListAdapter roomListAdapter;
    private ActivityRoomListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("채팅");

        binding = DataBindingUtil.setContentView(this,R.layout.activity_room_list);
        RoomListViewModel roomListViewModel = new RoomListViewModel(getApplication());
        binding.setMainViewModel(roomListViewModel);
        binding.setLifecycleOwner(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        roomListViewModel.getAllNotiMessages().observe(this, notiMessageObserver);

    }

    final Observer<List<NotiMessage>> notiMessageObserver = new Observer<List<NotiMessage>>() {
        @Override
        public void onChanged(@Nullable final List<NotiMessage> updatedList) {

            roomListAdapter = new RoomListAdapter(RoomListActivity.this,updatedList);
            binding.recyclerView.setAdapter(roomListAdapter);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_roomlist, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.ignore:
                startActivity(new Intent(RoomListActivity.this, IgnoreListActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
}
