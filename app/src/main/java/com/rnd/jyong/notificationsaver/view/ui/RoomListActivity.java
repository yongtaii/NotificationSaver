package com.rnd.jyong.notificationsaver.view.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;
import com.rnd.jyong.notificationsaver.databinding.ActivityRoomListBinding;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;
import com.rnd.jyong.notificationsaver.view.adapter.RoomListAdapter;
import com.rnd.jyong.notificationsaver.viewmodel.RoomListViewModel;

import java.util.List;

public class RoomListActivity extends AppCompatActivity {

    private RoomListAdapter roomListAdapter;
    private ActivityRoomListBinding binding;
    private boolean isFabRotated = false;
    private RoomListViewModel roomListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(CommonUtil.getStringArray(R.array.room_name_list)[1]);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_room_list);
        roomListViewModel = new RoomListViewModel(getApplication());
        binding.setMainViewModel(roomListViewModel);
        binding.setLifecycleOwner(this);
        binding.btmBanner.setOnClickListener(view -> {

//            String url = roomListViewModel.getBtmBannerUrl();


//            if(url.length() < 1) return;
//
//            if (!url.startsWith("http://") && !url.startsWith("https://"))
//                url = "https://" + url;

//            Log.d("yong123","launch url : " + url);

//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(browserIntent);

        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        roomListViewModel.getAllNotiMessages().observe(this, notiMessageObserver);
//        roomListViewModel.getBtmBannerLiveData().observe(this, btmBannerObserver);
//        roomListViewModel.getAppVersion(appVersionListener);
//        roomListViewModel.getBottomAdInfoFromFirebaseDB();

        initFab();

    }

    @Override
    public void onBackPressed() {

        if(System.currentTimeMillis() - JPreference.getLastReviewDialogTime() > 1000*60*60*24*5){
            JPreference.setLastReviewDialogTime(System.currentTimeMillis());
            ReviewBottomSheetDialog bsdf = new ReviewBottomSheetDialog();
            bsdf.show(getSupportFragmentManager(),bsdf.getTag());
        }else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    final Observer<List<NotiMessage>> notiMessageObserver = new Observer<List<NotiMessage>>() {
        @Override
        public void onChanged(@Nullable final List<NotiMessage> updatedList) {

            roomListAdapter = new RoomListAdapter(RoomListActivity.this, updatedList);
            binding.recyclerView.setAdapter(roomListAdapter);
        }
    };

    final Observer<Uri> btmBannerObserver = new Observer<Uri>() {
        @Override
        public void onChanged(@Nullable final Uri uri) {

            Glide.with(RoomListActivity.this)
                    .load(uri)
                    .into(binding.btmBanner);
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

        String[] dateList = new String[]{CommonUtil.getStringArray(R.array.dialog_delete_period_msg)[0],
                CommonUtil.getStringArray(R.array.dialog_delete_period_msg)[1],CommonUtil.getStringArray(R.array.dialog_delete_period_msg)[2]};
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
//                                roomListViewModel.deleteMessageWithTime(1000*60*60*24*7);
//                                roomListViewModel.deleteMessageWithTime(System.currentTimeMillis()-1000*60);
                                break;
                            case 1:
//                                roomListViewModel.deleteMessageWithTime(System.currentTimeMillis()-1000*60*60*24*7*3);
                                break;
                            case 2:
//                                roomListViewModel.deleteAllNotiMessages();
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

    public void showUpdateDialog(){

        AlertDialog updateDialog;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this,R.style.CustomMaterialDialog)
                .setTitle(getString(R.string.dialog_update_title))
                .setMessage(getString(R.string.dialog_update_message))
                .setCancelable(false)
                .setNeutralButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.btn_update), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        launchMarket(getPackageName());
                    }
                });
        updateDialog = builder.create();
        updateDialog.show();

    }

    public void launchMarket(String appPackageName){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

//    ValueEventListener appVersionListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            long serverAppVersion = (long)snapshot.getValue();
//            long clientAppVersion = BuildConfig.VERSION_CODE;
//
//            if(serverAppVersion > clientAppVersion){
//                showUpdateDialog();
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//        }
//    };

}
