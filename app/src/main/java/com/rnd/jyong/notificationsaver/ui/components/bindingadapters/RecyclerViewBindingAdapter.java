package com.rnd.jyong.notificationsaver.ui.components.bindingadapters;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rnd.jyong.notificationsaver.ui.components.GeneralBindAdapter;

import java.util.List;

import kotlin.jvm.JvmStatic;

public class RecyclerViewBindingAdapter {

    /**
     * 데이터 갱신 시 사용합니다.
     */
    @BindingAdapter(value = {"replaceAll"}, requireAll = false)
    public static <T> void setItems(RecyclerView view, List<T> list) {
        if(list == null){
            return;
        }

        GeneralBindAdapter adapter = (GeneralBindAdapter) view.getAdapter();
        if (adapter == null) {
            return;
        }
        adapter.setItems(list);
//        if(scrollToTop == null || scrollToTop) { //default : scrollToTop
//            view.scrollToPosition(0);
//        }
    }

//    @BindingAdapter(value = {"replaceAll","scrollToTop"}, requireAll = false)
//    public static <T> void setItems(RecyclerView view, List<T> list, Boolean scrollToTop) {
//        if(list == null){
//            return;
//        }
//
//        GeneralBindAdapter adapter = (GeneralBindAdapter) view.getAdapter();
//        if (adapter == null) {
//            return;
//        }
//        adapter.setItems(list);
//        if(scrollToTop == null || scrollToTop) { //default : scrollToTop
//            view.scrollToPosition(0);
//        }
//    }

}
