package com.cyansoft.maodou_manger.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyansoft.maodou_manger.Activity.LoginActivity;
import com.cyansoft.maodou_manger.Activity.MyApplication;
import com.cyansoft.maodou_manger.Activity.ReservationActivity;
import com.cyansoft.maodou_manger.Activity.SingleActivity;
import com.cyansoft.maodou_manger.Activity.StopActivity;
import com.cyansoft.maodou_manger.Activity.UpdateInfoActivity;
import com.cyansoft.maodou_manger.R;

public class MeFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences sp;
    private MyApplication myApplication ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.zhdl).setOnClickListener(this);
        view.findViewById(R.id.yyztcx).setOnClickListener(this);
        view.findViewById(R.id.tzxxcx).setOnClickListener(this);
        view.findViewById(R.id.yhzcxxwh).setOnClickListener(this);
        view.findViewById(R.id.tcdqzh).setOnClickListener(this);
        TextView username = (TextView) view.findViewById(R.id.text_username);
        TextView qianming = (TextView) view.findViewById(R.id.text_qianming);
//        myApplication.setUsername(sp.getString("Username", ""));
        String name = ((MyApplication) getActivity().getApplication()).getUsername();
        String ming = ((MyApplication) getActivity().getApplication()).getUserinfo();
        username.setText(name);
        qianming.setText("个性签名: "+ming);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        String test = ((MyApplication) getActivity().getApplication()).getUsername();
//    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.zhdl://
                intent.setClass(getActivity(), LoginActivity.class);
//                intent.putExtra("state", "待付款");
                startActivity(intent);

                break;
            case R.id.yyztcx://代收获
                intent.setClass(getActivity(), ReservationActivity.class);
//                intent.putExtra("state","待收货");
                startActivity(intent);
                break;
            case R.id.yhzcxxwh://代收获
                intent.setClass(getActivity(), UpdateInfoActivity.class);
//                intent.putExtra("state","待收货");
                startActivity(intent);
                break;
            case R.id.tzxxcx://代收获
                intent.setClass(getActivity(), StopActivity.class);
//                intent.putExtra("state","待收货");
                startActivity(intent);
                break;
//            case R.id.tuikuantuihuo://退款退货
//                intent.setClass(getContext(), CarActivity.class);
//                intent.putExtra("state","退");
//                startActivity(intent);
//                break;
//            case R.id.all_order://所有订单
//                intent.setClass(getContext(), CarActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.address_guanli://地址管理
//                intent.setClass(getContext(), DiZhiManager.class);
//                startActivity(intent);
//                break;
//            case R.id.pintiedasai://拼贴大赛
//                intent.setClass(getContext(), PinTieActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.onlign_kefu://在线客服
//                intent.setClass(getContext(), ChatActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.lianxiwomen://联系我们
//                intent.setClass(getContext(), CallUsActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.about://关于我们
//                intent.setClass(getContext(), AboutActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.jiaocheng://豆搭教程
//                intent.setClass(getContext(), JiaoChengActivity.class);
//                startActivity(intent);
//
//                break;
        }
    }

}
