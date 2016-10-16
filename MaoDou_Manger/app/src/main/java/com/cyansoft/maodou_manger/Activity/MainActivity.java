package com.cyansoft.maodou_manger.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.Adapter.Adapter;
import com.cyansoft.maodou_manger.JavaBean.DataBean;
import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.fragment.FindFragment;
import com.cyansoft.maodou_manger.fragment.OkFragment;
import com.cyansoft.maodou_manger.fragment.UpdateFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity  {
 private ListView mListView;
    private List<DataBean> mDataBeanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        inintView();
    }
    private void inintView() {
        mListView= (ListView) findViewById(R.id.fenlei_listview);
        Adapter adapter = new Adapter(MainActivity.this,R.layout.feilei_list_item,mDataBeanList);
        mListView.setAdapter(adapter);
        DataBean dataBean1 = new DataBean("查看订单");
        adapter.add(dataBean1);
        DataBean dataBean2 = new DataBean("确认订单");
        adapter.add(dataBean2);
        DataBean dataBean3 = new DataBean("修改订单");
        adapter.add(dataBean3);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    FragmentManager fragmentManager = getFragmentManager();
//                    FindFragment  = new FindFragment();
                    FindFragment findFragment = new FindFragment();
                    FragmentTransaction transaction = fragmentManager.
                            beginTransaction();
//                    transaction.replace(R.id.frameLayout, findFragment);
                    transaction.commit();
                }
                if (position == 1) {
                    FragmentManager fragmentManager = getFragmentManager();
                    OkFragment okFragment = new OkFragment();
                    FragmentTransaction transaction = fragmentManager.
                            beginTransaction();
                    transaction.replace(R.id.frameLayout, okFragment);
                    transaction.commit();
                }
                if (position == 2) {
                    FragmentManager fragmentManager = getFragmentManager();
                    UpdateFragment updateFragment = new UpdateFragment();
                    FragmentTransaction transaction = fragmentManager.
                            beginTransaction();
                    transaction.replace(R.id.frameLayout, updateFragment);

                    transaction.commit();
                }
            }
        });


    }

}
