package com.cyansoft.maodou_manger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cyansoft.maodou_manger.JavaBean.DataBean;
import com.cyansoft.maodou_manger.R;

import java.util.List;

/**
 * Created by author:StarryFei on 2016/6/9 0009.
 * Email:760646630fei@sina.com
 */
public class findAdapter extends ArrayAdapter<DataBean> {
    private int resourceId;
    public findAdapter(Context context, int resource, List<DataBean> objects) {

        super(context, resource, objects);
        this.resourceId = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewHolder;
        viewHolder= new viewHolder();
        View view;
        DataBean dataBean = getItem(position);
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId ,null);
            viewHolder.sName = (TextView) view.findViewById(R.id.s_name);
            viewHolder.sPrice = (TextView) view.findViewById(R.id.s_price);
            viewHolder.sAddress = (TextView) view.findViewById(R.id.s_address);
            viewHolder.sPhone = (TextView) view.findViewById(R.id.s_phone);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (viewHolder) view.getTag();
        }
        viewHolder.sName.setText(dataBean.getS_name());
        viewHolder.sPrice.setText(dataBean.getMprice());
        viewHolder.sAddress.setText(dataBean.getAddress());
        viewHolder.sPhone.setText(dataBean.getPhone());


        return view;
    }
    class viewHolder{
        private TextView sName;
        private TextView sPrice;
        private TextView sAddress;
        private TextView  sPhone;
    }
}
