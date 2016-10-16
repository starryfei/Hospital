package com.cyansoft.maodou_manger.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyansoft.maodou_manger.Activity.AddClassActivity;
import com.cyansoft.maodou_manger.Activity.AddDoctorActivity;
import com.cyansoft.maodou_manger.R;

/**
 * Created by author:StarryFei on 2016/8/14 0014.
 * Email:760646630fei@sina.com
 */
public class DetailFragment extends Fragment implements View.OnClickListener {

    private LinearLayout l1,l2,l3,l4,l5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feileicontext,container,false);
        l1 = (LinearLayout) view.findViewById(R.id.l1);
        l2 = (LinearLayout) view.findViewById(R.id.l2);
        l3 = (LinearLayout) view.findViewById(R.id.l3);
        l4 = (LinearLayout) view.findViewById(R.id.l4);
        l5 = (LinearLayout) view.findViewById(R.id.l5);

        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        l5.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.l1:
                Intent a = new Intent(getActivity(),AddClassActivity.class);
                startActivity(a);
                break;
            case R.id.l2:
                Intent intent = new Intent(getActivity(), AddDoctorActivity.class);
                startActivity(intent);
                break;
            case R.id.l3:

                break;
            case R.id.l4:

                break;
            case R.id.l5:
                Toast.makeText(getActivity(),"此功能暂未开放,敬请期待。。。",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}

