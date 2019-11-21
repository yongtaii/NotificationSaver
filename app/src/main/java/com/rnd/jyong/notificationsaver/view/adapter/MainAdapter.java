package com.rnd.jyong.notificationsaver.view.adapter;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.base.BaseApplication;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;

import java.util.Date;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.FavViewHolder> {

    private List<NotiMessage> mNotiMessages;

    public MainAdapter(List<NotiMessage> list){
        mNotiMessages = list;
    }

    @Override
    public FavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row, parent, false);
        return new FavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavViewHolder holder, int position) {
        NotiMessage notiMessage = mNotiMessages.get(position);
        holder.tvMsg.setText(notiMessage.msg);
        holder.tvName.setText(notiMessage.name);
        holder.tvTime.setText(CommonUtil.convertTimeToSimpleTime(notiMessage.time));
        holder.viewIcon.setBackground(new BitmapDrawable(BaseApplication.getInstance().getApplicationContext().getResources()
                , CommonUtil.getImage(notiMessage.icon)));
//        GradientDrawable drawable = (GradientDrawable) holder.tvIcon.getBackground();
//        drawable.setColor(Color.parseColor(message.getMemberData().getColor()));
    }

    @Override
    public int getItemCount() {
        return mNotiMessages.size();
    }


    class FavViewHolder extends RecyclerView.ViewHolder {

        TextView tvMsg;
        TextView tvName;
        TextView tvTime;
        View viewIcon;

        FavViewHolder(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tvMsg);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            viewIcon = itemView.findViewById(R.id.tvIcon);
//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int pos = getAdapterPosition();
//                    NotiMessage notiMessage = mNotiMessages.get(pos);
//                    mFavViewModel.removeFav(favourites.mId);
//                }
//            });
        }
    }


}
