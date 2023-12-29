package com.rnd.jyong.notificationsaver.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

//import com.google.android.gms.tasks.OnSuccessListener;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;

import java.util.List;

public class RoomListViewModel extends AndroidViewModel {
    public RoomListViewModel(@NonNull Application application) {
        super(application);
    }

//    private final NotiMessageRepository repository;
//    private LiveData<List<NotiMessage>> allRoomListLiveData;
//    private String BTM_BANNER_URL = "";
//    private MutableLiveData<Uri> btmBannerLiveData;
//
//    public RoomListViewModel(Application application) {
//        super(application);
//        repository = new NotiMessageRepository(application,null);
//        allRoomListLiveData = repository.getAllRoomList();
//        btmBannerLiveData = new MutableLiveData<>();
//    }
//
//    public String getBtmBannerUrl(){ return BTM_BANNER_URL; }
//
//    public LiveData<Uri> getBtmBannerLiveData(){return btmBannerLiveData;}
//
//    public LiveData<List<NotiMessage>> getAllNotiMessages() {
//        return allRoomListLiveData;
//    }
//
//    public void deleteAllNotiMessages() {
//        repository.deleteAll();
//    }
//
//    public void deleteMessageWithTime(long time) {
//        repository.deleteMessageWithTime(time);
//    }
//
//    public void getAppVersion(ValueEventListener listener){
//
//        if(!CommonUtil.checkLastUpdateDiaogTime()) return;
//        try{
//            JPreference.setLastUpdateDialogTime(System.currentTimeMillis());
//            Query versionQuery = FirebaseDatabase.getInstance().getReference().child("app_version");
//            versionQuery.addListenerForSingleValueEvent(listener);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    public void getBottomAdInfoFromFirebaseDB(){
//        Query versionQuery = FirebaseDatabase.getInstance().getReference();
//        versionQuery.addListenerForSingleValueEvent(bottomAdinfoListener);
//    }
//
//    ValueEventListener bottomAdinfoListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//
//            try {
//
//                String btmAdUse = snapshot.child("BTM_AD_USE").getValue().toString();
//                String btmAdName = snapshot.child("BTM_AD_NAME").getValue().toString();
//                BTM_BANNER_URL = snapshot.child("BTM_AD_URL").getValue().toString();
//
////                Log.d("yong123","btmAdUse : " + btmAdUse);
////                Log.d("yong123","btmAdName : " + btmAdName);
////                Log.d("yong123","btmAdUrl : " + BTM_BANNER_URL);
//
//                if(!btmAdUse.equalsIgnoreCase("true")) return;
//
//                StorageReference storageReference = FirebaseStorage.getInstance().getReference(btmAdName);
//                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
////                        Log.d("yong123","onSUccess()");
//                        btmBannerLiveData.postValue(uri);
//                    }
//                });
//
//
//            }catch (Exception err){
//                Log.d("Error", err.toString());
//            }
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//        }
//    };

}