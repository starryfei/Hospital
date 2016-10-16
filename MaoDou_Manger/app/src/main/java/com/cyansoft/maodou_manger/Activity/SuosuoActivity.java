package com.cyansoft.maodou_manger.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cyansoft.maodou_manger.JavaBean.Doctor;
import com.cyansoft.maodou_manger.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class SuosuoActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter = null;
    private static final String[] chose = {"科室搜索", "医生姓名"};
    private EditText sousuo_text;
    private Spinner spinner = null;
    private ListView cList_doctors;
    private String cla = null;
    private int id = 0;
    private List<Doctor> mList;
    private TextView sousuo_btn;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_suosuo);

        initview();


    }

    private void initview() {
        sousuo_text = (EditText) findViewById(R.id.sousuo_edittxt);
        cList_doctors = (ListView) findViewById(R.id.list_doctor);
        spinner = (Spinner) findViewById(R.id.spinner_chose);
        sousuo_btn = (TextView) findViewById(R.id.sousuo_btn);
        back = (TextView) findViewById(R.id.sousuo_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chose);
        //设置下拉列表风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到spinner中去
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);//设置默认显示
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                cla = ((TextView) arg1).getText().toString();
//                sousuo_text.setText("按" + ((TextView) arg1).getText());

                if (cla.equals("医生姓名")) {
                    id = 1;
                } else if (cla.equals("科室搜索")) {
                    id = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sousuo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sousuotext = sousuo_text.getText().toString();
                String path = null;
                try {
                    path = "http://www.starryfei.cn:8080/MVC/SelectServlet?id=" + id + "&sousuo=" + URLEncoder.encode(sousuotext, "UTF-8");
                    Log.d("aa", path);
                    new SousuoAsynTack().execute(path);//execute启动AsynTack
                    cList_doctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(SuosuoActivity.this, DoctorDetailsActivity.class);
                            Bundle bd = new Bundle();
                            bd.putString("name", mList.get(position).name);
                            bd.putString("class", mList.get(position).cla);
                            bd.putString("phone", mList.get(position).phone);
                            bd.putString("zhicheng", mList.get(position).zhichen);
                            bd.putString("resume", mList.get(position).resume);
                            bd.putString("info", mList.get(position).info);
                            bd.putString("special", mList.get(position).special);
                            bd.putString("photo", mList.get(position).getPhoto());
                            bd.putString("money", mList.get(position).money);
                            bd.putString("state",mList.get(position).state);
                            intent.putExtras(bd);
                            startActivity(intent);
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class SousuoAsynTack extends AsyncTask<String, Void, List<Doctor>> {
        @Override
        protected List<Doctor> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<Doctor> doctors) {
            super.onPostExecute(doctors);
            NewsAdapter newsAdapter = new NewsAdapter(getApplicationContext(), doctors);
            cList_doctors.setAdapter(newsAdapter);
        }
    }

    /**
     * 讲URL对应的json格式转化为NewsBean
     *
     * @param url
     * @return
     */
    private List<Doctor> getJsonData(String url) {
        List<Doctor> doctorList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            Log.d("aa", jsonString);
            JSONObject jsonobject;
            Doctor doctor;
            try {
//                jsonobject = new JSONObject(jsonString);
                JSONArray jsonarray = new JSONArray(jsonString);
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    doctor = new Doctor();
                    doctor.setName(jsonobject.getString("username"));
//                    doctor.name = jsonobject.getString("username");
                    doctor.cla = jsonobject.getString("class");
                    doctor.phone = jsonobject.getString("phone");
                    doctor.zhichen = jsonobject.getString("zhicheng");
                    doctor.resume = jsonobject.getString("resume");
                    doctor.info = jsonobject.getString("info");
                    doctor.special = jsonobject.getString("special");
                    doctor.setPhoto(jsonobject.getString("photo"));
                    doctor.money = jsonobject.getString("money");
                    doctor.state = jsonobject.getString("state");
                    doctor.total = jsonobject.getString("total");
                    if (jsonobject.getString("msg").equals("success")) {
                        doctorList.add(doctor);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctorList;
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

        public NewsAdapter(Context context, List<Doctor> data) {
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
                convertView = mLayoutInflater.inflate(R.layout.item_layout, null);
                viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.image);
                viewHolder.mTextView1 = (TextView) convertView.findViewById(R.id.username);
                viewHolder.mTextView2 = (TextView) convertView.findViewById(R.id.user_class);
                viewHolder.mTextView3 = (TextView) convertView.findViewById(R.id.dstate);
                viewHolder.mTextView4= (TextView) convertView.findViewById(R.id.total);
                viewHolder.mTextView5 = (TextView) convertView.findViewById(R.id.user_zhicheng);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            String url = mList.get(position).photo;
            x.image().bind(viewHolder.ivIcon, url);
            viewHolder.mTextView1.setText(mList.get(position).name);
            viewHolder.mTextView2.setText(mList.get(position).cla);
            viewHolder.mTextView3.setText(mList.get(position).state);
            viewHolder.mTextView4.setText(mList.get(position).total);
            viewHolder.mTextView5.setText(mList.get(position).zhichen);
            return convertView;

        }

        class ViewHolder {
            private TextView mTextView1, mTextView2,mTextView3,mTextView4,mTextView5;
            private ImageView ivIcon;
        }
    }
}
