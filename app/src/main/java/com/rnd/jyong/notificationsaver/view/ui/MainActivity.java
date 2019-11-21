package com.rnd.jyong.notificationsaver.view.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.databinding.ActivityMainBinding;
import com.rnd.jyong.notificationsaver.view.adapter.MainAdapter;
import com.rnd.jyong.notificationsaver.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainAdapter mainAdapter;
    private ActivityMainBinding binding;
    private String roomname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setAnimation();
        roomname = getIntent().getStringExtra("roomname");

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(roomname);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        MainViewModel mainViewModel = new MainViewModel(getApplication(), roomname);
        binding.setMainViewModel(mainViewModel);
        binding.setLifecycleOwner(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        binding.recyclerView.setLayoutManager(layoutManager);
        mainViewModel.getMessagesByRoomname().observe(this, notiMessageObserver);

    }

    final Observer<List<NotiMessage>> notiMessageObserver = new Observer<List<NotiMessage>>() {
        @Override
        public void onChanged(@Nullable final List<NotiMessage> updatedList) {

            mainAdapter = new MainAdapter(updatedList);
            binding.recyclerView.setAdapter(mainAdapter);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

}
