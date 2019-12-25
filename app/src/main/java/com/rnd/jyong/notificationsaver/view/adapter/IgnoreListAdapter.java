package com.rnd.jyong.notificationsaver.view.adapter;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.base.BaseApplication;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class IgnoreListAdapter extends RecyclerView.Adapter<IgnoreListAdapter.FavViewHolder> {

    private List<NotiMessage> ignoreList;
    private Activity activity;

    public IgnoreListAdapter(Activity activity, List<NotiMessage> list){
        this.activity = activity;
        ignoreList = list;
    }

    @Override
    public IgnoreListAdapter.FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ignore_row, parent, false);
        return new IgnoreListAdapter.FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IgnoreListAdapter.FavViewHolder holder, int position) {
        NotiMessage notiMessage = ignoreList.get(position);
        holder.tvRoomname.setText(notiMessage.roomname);
        holder.ivIcon.setImageDrawable(new BitmapDrawable(BaseApplication.getInstance().getApplicationContext().getResources()
                , CommonUtil.getImage(notiMessage.icon)));
    }

    @Override
    public int getItemCount() {
        return ignoreList.size();
    }


    class FavViewHolder extends RecyclerView.ViewHolder {

        TextView tvRoomname;
        ImageView ivIcon;
        Button btnRelease;

        FavViewHolder(View itemView) {
            super(itemView);
            tvRoomname = itemView.findViewById(R.id.tvRoomname);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            btnRelease = itemView.findViewById(R.id.btnRelease);
            btnRelease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int pos = getAdapterPosition();

                    NotiMessage roomNotiMessage = ignoreList.get(pos);
                    CommonUtil.removeIgnoreListItem(roomNotiMessage);

                    ignoreList.remove(pos);
                    notifyItemRemoved(pos);
//                    notifyItemRangeChanged(pos, ignoreList.size());


                }
            });
        }

    }

}
