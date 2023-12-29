package com.rnd.jyong.notificationsaver.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.base.App;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;
import com.rnd.jyong.notificationsaver.view.ui.ImageViewActivity;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.FavViewHolder> {

    private List<NotiMessage> mNotiMessages;
    private Context context;

    public MainAdapter(Context context,List<NotiMessage> list){
        this.context = context;
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
        holder.tvName.setText(notiMessage.name);
//        holder.viewIcon.setBackground(new BitmapDrawable(App.getInstance().getApplicationContext().getResources()
//                , CommonUtil.getImage(notiMessage.icon)));


        int visibleImage = notiMessage.image.length > 0 ? View.VISIBLE : View.GONE;
        int visibleTest = visibleImage == View.VISIBLE ? View.GONE : View.VISIBLE;
        holder.tvMsg.setVisibility(visibleTest);
        holder.tvTime.setVisibility(visibleTest);
        holder.ivImage.setVisibility(visibleImage);
        holder.tvTimeWithImage.setVisibility(visibleImage);

        if(visibleImage == View.VISIBLE){
            // 이미지가 있을 경우
//            holder.ivImage.setImageBitmap();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterInside(), new RoundedCorners(16));

//            Glide.with(App.getInstance().getApplicationContext())
//                    .asBitmap()
//                    .load(notiMessage.image)
//                    .apply(requestOptions)
//                    .into(holder.ivImage);
            holder.tvTimeWithImage.setText(CommonUtil.convertTimeToSimpleTime(notiMessage.time));

            holder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageViewActivity.class);
                    intent.putExtra(ImageViewActivity.EXTRA_NOTIMSG, notiMessage);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }else{
            // 이미지가 없을 경우
            holder.tvMsg.setText(notiMessage.msg);
            holder.tvTime.setText(CommonUtil.convertTimeToSimpleTime(notiMessage.time));
        }

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
        ImageView ivImage;
        TextView tvTimeWithImage;

        FavViewHolder(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tvMsg);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            viewIcon = itemView.findViewById(R.id.tvIcon);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTimeWithImage = itemView.findViewById(R.id.tvTimeWithImage);
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
