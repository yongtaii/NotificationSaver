package com.rnd.jyong.notificationsaver.view.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.preference.JPreference;
import com.rnd.jyong.notificationsaver.databinding.BottomSheetReviewBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class ReviewBottomSheetDialog extends BottomSheetDialogFragment {

    BottomSheetReviewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME,R.style.BottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater,R.layout.bottom_sheet_review,container,false);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.layoutReviewNow.setOnClickListener( view -> {
            launchMarket(getActivity().getPackageName());
            this.dismiss();
        });
        binding.btnCancel.setOnClickListener( view -> {
            JPreference.setLastReviewDialogTime(System.currentTimeMillis()+((long)1000*60*60*24*50));
            finishActivity();
        });
        binding.btnLater.setOnClickListener( view -> {
            finishActivity();
        });
        getDialog().setCanceledOnTouchOutside(false);
    }

    public void finishActivity(){
        getActivity().finish();
    }

    public void launchMarket(String appPackageName){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
