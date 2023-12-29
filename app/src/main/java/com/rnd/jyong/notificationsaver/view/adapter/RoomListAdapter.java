package com.rnd.jyong.notificationsaver.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.rnd.jyong.notificationsaver.BuildConfig;
import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;
import com.rnd.jyong.notificationsaver.data.repository.NotiMessageRepository;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;
import com.rnd.jyong.notificationsaver.view.ui.MainActivity;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.FavViewHolder> {

    private List<NotiMessage> roomNames;
    private Activity activity;
    private InterstitialAd mInterstitialAd;

    public RoomListAdapter(Activity activity, List<NotiMessage> list){
        this.activity = activity;
        roomNames = list;
        initAdmob();
    }

    private void initAdmob(){

        if(BuildConfig.DEBUG) return;

        MobileAds.initialize(activity.getApplicationContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        mInterstitialAd = new InterstitialAd(activity.getApplicationContext());
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); // test admob
        mInterstitialAd.setAdUnitId(activity.getApplicationContext().getString(R.string.addmob_roomlist_ad_id));

//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//            }
//        });
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_recyclerview_row, parent, false);
        return new FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RoomListAdapter.FavViewHolder holder, int position) {
        NotiMessage notiMessage = roomNames.get(position);
        holder.tvMsg.setText(notiMessage.msg.replace(" ", "\u00A0"));
        holder.tvRoomname.setText(notiMessage.roomname);
        holder.tvTime.setText(CommonUtil.convertTimeForRoomList(notiMessage.time));
//        holder.ivIcon.setImageDrawable(new BitmapDrawable(App.getInstance().getApplicationContext().getResources()
//                , CommonUtil.getImage(notiMessage.icon)));


//        GradientDrawable drawable = (GradientDrawable) holder.tvIcon.getBackground();
//        drawable.setColor(Color.parseColor(message.getMemberData().getColor()));
    }

    @Override
    public int getItemCount() {
        return roomNames.size();
    }


    class FavViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView tvMsg;
        TextView tvRoomname;
        TextView tvTime;
        ImageView ivIcon;
        RelativeLayout roomWrapper;

        FavViewHolder(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tvMsg);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvRoomname = itemView.findViewById(R.id.tvRoomname);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            roomWrapper = itemView.findViewById(R.id.roomWrapper);
            roomWrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    NotiMessage roomNotiMessage = roomNames.get(pos);
                    String roomname = roomNotiMessage.roomname;

                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("roomname",roomname);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    if ( !BuildConfig.DEBUG && CommonUtil.checkLastRoomInAdmobTime()) {

                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
                        mInterstitialAd.setAdListener(new AdListener(){
                            public void onAdLoaded(){
                                JPreference.setShowLastRoomInAdmobTime(System.currentTimeMillis());
                                mInterstitialAd.show();
                            }
                        });

                    }

                }
            });

            roomWrapper.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

//            menu.setHeaderTitle("Select The Action");
            MenuItem deleteMenu = menu.add(0, 1, 1, "삭제하기"); //groupId, itemId, order, title
            MenuItem ignoreMenu = menu.add(0, 2, 2, "차단하기"); //groupId, itemId, order, title
            deleteMenu.setOnMenuItemClickListener(onMenuItemClickListener);
            ignoreMenu.setOnMenuItemClickListener(onMenuItemClickListener);

        }

        private final MenuItem.OnMenuItemClickListener onMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int pos = getAdapterPosition();
                NotiMessage roomNotiMessage = roomNames.get(pos);
                NotiMessageRepository repository = new NotiMessageRepository(activity.getApplication(), roomNotiMessage.roomname);

                switch (item.getItemId()) {
                    case 1:
                        // delete
                        repository.deleteRoomMessage(roomNotiMessage.roomname);
                        break;

                    case 2:
                        // ignore
                        CommonUtil.addToIgnoreList(roomNotiMessage);
                        repository.deleteRoomMessage(roomNotiMessage.roomname);
                        break;
                }
                return false;
            }
        };
    }

}
