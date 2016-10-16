package com.cyansoft.maodou_manger.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyansoft.maodou_manger.R;

/**
 * Created by author:StarryFei on 2016/6/9 0009.
 * Email:760646630fei@sina.com
 */
public class OkFragment extends Fragment {
//    private ListView mListView;
//    private List<DataBean> mDataBeanList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.okfragment,container,false);
//        mListView = (ListView) view.findViewById(R.id.find_list);
//        findAdapter adapter= new findAdapter(this.getContext(),R.layout.finditem,mDataBeanList);
//        mListView.setAdapter(adapter);
        return view;
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.find_fragment, container, false);
//        return view;
//    }
}
