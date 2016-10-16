package com.cyansoft.maodou_manger.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.JavaBean.Yuyue;
import com.cyansoft.maodou_manger.R;
import com.demievil.library.RefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ReservationActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean isLogin = false;
    private ListView list_yuyue;
    private TextView total_money, commit_order;
    private String path = "http://www.starryfei.cn:8080/MVC/YuyueServlet";
    private List<Yuyue> mList;
    //数据源
    private MyApplication myappication;
    private ImageView iBack;
    private CheckBox check_all;
    //存放已选中的商品集合
//    private List<Yuyue> list_choosed = new ArrayList<>();
    //存放价格的list集合
    private List<Integer> list_money = new ArrayList<Integer>();
    int totalMoney = 0;
    private ProgressDialog rDialog;
    private RefreshLayout mRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_reservation);
        myappication = (MyApplication) getApplication();
        if (!myappication.isLogin) {
            Toast.makeText(ReservationActivity.this, "请登录后在查看预约订单!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ReservationActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        initview();
    }

    private void initview() {
        mRefreshLayout = (RefreshLayout) findViewById(R.id.swipe_containe);
        list_yuyue = (ListView) findViewById(R.id.listview_yuyue);
        total_money = (TextView) findViewById(R.id.total_money);
        commit_order = (TextView) findViewById(R.id.commit_order);
        iBack = (ImageView) findViewById(R.id.i_back);
        check_all = (CheckBox) findViewById(R.id.checkbox_chooseall);
        iBack.setOnClickListener(this);
        commit_order.setOnClickListener(this);
        mRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        mRefreshLayout.setChildView(list_yuyue);

        //使用SwipeRefreshLayout的下拉刷新监听
        //use SwipeRefreshLayout OnRefreshListener
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new SousuoAsynTack().execute(path);//execute启动AsynTack
                mRefreshLayout.setRefreshing(false);

//                progressBar.setVisibility(View.GONE);
            }
        });
        list_yuyue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) list_yuyue.getChildAt(position).findViewById(R.id.item_checkbox);
                TextView state = (TextView) list_yuyue.getChildAt(position).findViewById(R.id.state);
                final TextView doctor = (TextView) list_yuyue.getChildAt(position).findViewById(R.id.item_textview_productname);
                ImageView delete = (ImageView) list_yuyue.getChildAt(position).findViewById(R.id.item_del);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
//                                rDialog = new ProgressDialog(getApplication());
//                                rDialog.setTitle("提示");
//                                rDialog.setMessage("正在提交,请稍后....");
//                                rDialog.setCancelable(true);
//                                rDialog.show();
                                String name = myappication.getUsername().toString();
                                String doctorname = doctor.getText().toString();
                                HttpURLConnection urlConnection = null;
                                URL url = null;
                                try {
                                    String u = "http://www.starryfei.cn:8080/MVC/DeleteYuyueServlet?username=" + URLEncoder.encode(name, "UTF-8")
                                            + "&doctorname=" + URLEncoder.encode(doctorname, "UTF-8");
                                    Log.d("aa", u);
                                    url = new URL(u);
                                    urlConnection = (HttpURLConnection) url.openConnection();
                                    urlConnection.setConnectTimeout(5000);
                                    urlConnection.setRequestMethod("GET");
                                    urlConnection.connect();
                                    // 设置连接超时为5秒
                                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                                        rDialog.dismiss();
                                        Looper.prepare();
                                        Toast.makeText(ReservationActivity.this, "预约取消成功!", Toast.LENGTH_SHORT).show();
                                        Looper.loop();
//
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                });
                if (mList.get(position).state.equals("success")) {
                    checkBox.setChecked(false);
                    Toast.makeText(ReservationActivity.this, "已经预约成功!", Toast.LENGTH_SHORT).show();
                } else {
                    checkBox.setChecked(true);
                    if (checkBox.isChecked()) {
                        int money = Integer.valueOf(mList.get(position).money);
                        totalMoney += money;
                        state.setText("success");
                    }
                    total_money.setText("总价：￥" + totalMoney);

                }

            }
        });
        new SousuoAsynTack().execute(path);//execute启动AsynTack
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.i_back:
                finish();
                break;
            case R.id.commit_order:
                if (total_money.getText().toString().equals("")) {
                    Toast.makeText(ReservationActivity.this, "请选择你要预约的订单进行支付!", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("是否立即支付?");
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setMessage("支付金额为:" + total_money.getText().toString());
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(ReservationActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("取消", null);

                    builder.show();
                }
        }
    }

    class SousuoAsynTack extends AsyncTask<String, Void, List<Yuyue>> {

        @Override
        protected List<Yuyue> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<Yuyue> yuyue) {
            super.onPostExecute(yuyue);
            NewsAdapter newsAdapter = new NewsAdapter(getApplicationContext(), yuyue);
            list_yuyue.setAdapter(newsAdapter);
        }
    }

    /**
     * 讲URL对应的json格式转化为NewsBean
     *
     * @param url
     * @return
     */
    private List<Yuyue> getJsonData(String url) {
        List<Yuyue> YuyueList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            Log.d("aa", jsonString);
            JSONObject jsonobject;
            Yuyue yuyue;
            try {
//                jsonobject = new JSONObject(jsonString);
                JSONArray jsonarray = new JSONArray(jsonString);
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    yuyue = new Yuyue();
//                    yuyue.username=jsonobject.getString("username");
                    yuyue.doctorname = jsonobject.getString("doctorname");
                    yuyue.time = jsonobject.getString("time");
                    yuyue.money = jsonobject.getString("money");
                    yuyue.cla = jsonobject.getString("class");
                    yuyue.state = jsonobject.getString("state");
                    if (jsonobject.getString("msg").equals("success") && jsonobject.getString("username").equals(myappication.getUsername())) {
                        YuyueList.add(yuyue);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return YuyueList;
    }

    /**
     * 通过inputSteam
     *
     * @param is
     * @return
     */
    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");//字节流is转化为字符流
            BufferedReader br = new BufferedReader(isr);
            try {
                while ((line = br.readLine()) != null) {
                    result += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * NewsAdapter是listview的适配器
     */
    public class NewsAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        public NewsAdapter(Context context, List<Yuyue> data) {
            mList = data;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.item_reservation, null);
//                viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.mDoctorname = (TextView) convertView.findViewById(R.id.item_textview_productname);
                viewHolder.mTime = (TextView) convertView.findViewById(R.id.time);
                viewHolder.Money = (TextView) convertView.findViewById(R.id.item_text_price);
                viewHolder.State = (TextView) convertView.findViewById(R.id.state);
                viewHolder.Cla = (TextView) convertView.findViewById(R.id.cla);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
//            String url = mList.get(position).photo;
//            x.image().bind(viewHolder.ivIcon, url);
            viewHolder.mDoctorname.setText(mList.get(position).doctorname);
            viewHolder.mTime.setText("时间:" + mList.get(position).time);
            viewHolder.Money.setText(mList.get(position).money);
            viewHolder.Cla.setText("科室:" + mList.get(position).cla);
            viewHolder.State.setText(mList.get(position).state);

            return convertView;

        }

        class ViewHolder {
            private TextView mDoctorname, mTime, Money, Cla, State;
            private ImageView ivIcon;
        }
    }
}
