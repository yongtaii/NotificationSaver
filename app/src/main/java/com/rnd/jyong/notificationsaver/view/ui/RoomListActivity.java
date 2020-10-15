package com.rnd.jyong.notificationsaver.view.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;
import com.rnd.jyong.notificationsaver.databinding.ActivityRoomListBinding;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;
import com.rnd.jyong.notificationsaver.view.adapter.RoomListAdapter;
import com.rnd.jyong.notificationsaver.viewmodel.RoomListViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class RoomListActivity extends AppCompatActivity {

    private RoomListAdapter roomListAdapter;
    private ActivityRoomListBinding binding;
    private boolean isFabRotated = false;
    private RoomListViewModel roomListViewModel;

    private final int REQUEST_CODE_INAPP_UPDATE = 100;
    private AppUpdateManager mAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("채팅");

        binding = DataBindingUtil.setContentView(this,R.layout.activity_room_list);
        roomListViewModel = new RoomListViewModel(getApplication());
        binding.setMainViewModel(roomListViewModel);
        binding.setLifecycleOwner(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        roomListViewModel.getAllNotiMessages().observe(this, notiMessageObserver);

        checkInAppUpdate();

        initFab();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(
                new OnSuccessListener<AppUpdateInfo>() {
                    @Override
                    public void onSuccess(AppUpdateInfo appUpdateInfo) {
                        if (appUpdateInfo.updateAvailability()
                                == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            // 인 앱 업데이트가 이미 실행중이었다면 계속해서 진행하도록
                            try {
                                mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, RoomListActivity.this, REQUEST_CODE_INAPP_UPDATE);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
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

    private void initFab(){

        CommonUtil.init(binding.fabTrash);
        binding.fab.setOnClickListener( view -> {
                isFabRotated = CommonUtil.rotateFab(view,!isFabRotated);
                if(isFabRotated){
                    CommonUtil.showIn(binding.fabTrash);
                }else{
                    CommonUtil.showOut(binding.fabTrash);
                }
            }
        );

        binding.fabTrash.setOnClickListener( view -> showDelteDialog());
    }

    public void showDelteDialog(){

        String[] dateList = new String[]{"1주 전", "3주 전","전체 기간"};
        JPreference.setDelNotiMsgIdx(0);
        AlertDialog deleteDialog;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this,R.style.CustomMaterialDialog)
                .setTitle(getString(R.string.dialog_delete_notimsg_title))
                .setNeutralButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int index = JPreference.getDelNotiMsgIdx();
                        switch (index){
                            case 0:
                                roomListViewModel.deleteMessageWithTime(1000*60*60*24*7);
//                                roomListViewModel.deleteMessageWithTime(System.currentTimeMillis()-1000*60);
                                break;
                            case 1:
                                roomListViewModel.deleteMessageWithTime(System.currentTimeMillis()-1000*60*60*24*7*3);
                                break;
                            case 2:
                                roomListViewModel.deleteAllNotiMessages();
                                break;
                        }
                    }
                })
                .setSingleChoiceItems(dateList, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        JPreference.setDelNotiMsgIdx(index);
                    }
                });
        deleteDialog = builder.create();
        deleteDialog.show();

    }

    private void checkInAppUpdate(){
        mAppUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        // 업데이트 사용 가능 상태인지 체크
        Task<AppUpdateInfo> appUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();

        // 사용가능 체크 리스너를 달아준다
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
//                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
//                                AppUpdateType.IMMEDIATE,
                                AppUpdateType.FLEXIBLE,
                                RoomListActivity.this,
                                // 전역변수로 선언해준 Code
                                REQUEST_CODE_INAPP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                } else {
                }
            }
        });

    }


}
