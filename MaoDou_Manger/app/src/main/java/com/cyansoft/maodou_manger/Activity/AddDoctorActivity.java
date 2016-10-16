package com.cyansoft.maodou_manger.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddDoctorActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayAdapter<String> adapter = null;
    private EditText xm, zc, ssks, czsj, ccly, lxfs, grjl, yyfy,minge;
    private Button btn;
    private Spinner stateSpinner,claSpinner;
    private ProgressDialog rDialog;
    private static final String[] chose = {"就诊", "停诊"};
    private String text;
    private static int i = 0;
    private ImageView iBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_doctor);

        xm = (EditText) findViewById(R.id.xingming);
        zc = (EditText) findViewById(R.id.zhicheng);
        ssks = (EditText) findViewById(R.id.suoshukeshi);
        czsj = (EditText) findViewById(R.id.chuzhenshijian);
        ccly = (EditText) findViewById(R.id.chanshanglingyu);
        lxfs = (EditText) findViewById(R.id.lianxifangshi);
        grjl = (EditText) findViewById(R.id.gerenjianli);
        yyfy = (EditText) findViewById(R.id.yuyuefeiyong);
        minge = (EditText) findViewById(R.id.yuyueminge);
        stateSpinner = (Spinner) findViewById(R.id.spinner_chose);
        btn = (Button) findViewById(R.id.tianjia);
        iBack = (ImageView) findViewById(R.id.im_back);
        iBack.setOnClickListener(this);

        btn.setOnClickListener(this);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chose);
        //设置下拉列表风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到spinner中去
        stateSpinner.setAdapter(adapter);
        stateSpinner.setVisibility(View.VISIBLE);//设置默认显示
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                text = ((TextView) arg1).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.tianjia:
                if (xm.getText().toString() == null || "".equals(xm.getText().toString().trim())) {
                    ShowToask("医生姓名不能为空");
                    break;
                }
                if (zc.getText().toString() == null || "".equals(zc.getText().toString().trim())) {
                    ShowToask("职称不能为空");
                    Log.e("tag", "======Password");
                    break;
                }
                if (ssks.getText().toString() == null || "".equals(ssks.getText().toString().trim())) {
                    ShowToask("所属科室不能为空");
                    break;
                }
                if (czsj.getText().toString() == null || "".equals(czsj.getText().toString().trim())) {
                    ShowToask("出诊时间不能为空");
                    break;
                }
                if (ccly.getText().toString() == null || "".equals(ccly.getText().toString().trim())) {
                    ShowToask("擅长领域不能为空");
                    break;
                }
                if (lxfs.getText().toString() == null || "".equals(lxfs.getText().toString().trim())) {
                    ShowToask("联系方式不能为空");
                    break;
                }
                if (grjl.getText().toString() == null || "".equals(grjl.getText().toString().trim())) {
                    ShowToask("个人简介不能为空");
                    break;
                }
                if (yyfy.getText().toString() == null || "".equals(yyfy.getText().toString().trim())) {
                    ShowToask("预约费用不能为空");
                    break;
                }
                // 提示框
                rDialog = new ProgressDialog(this);
                rDialog.setTitle("提示");
                rDialog.setMessage("正在添加医生信息,请稍后....");
                rDialog.setCancelable(true);
                rDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = xm.getText().toString();
                        String zhicheng = zc.getText().toString();
                        String cla = ssks.getText().toString();
                        String time = czsj.getText().toString();
                        String special = ccly.getText().toString();
                        String phone = lxfs.getText().toString();
                        String resume = grjl.getText().toString();
                        String money = yyfy.getText().toString();
                        String total = minge.getText().toString();
                        HttpURLConnection urlConnection = null;
                        URL url = null;
                        i++;
                        try {
                            String u = "http://www.starryfei.cn:8080/MVC/InsertDoctorsServlet?name=" + URLEncoder.encode(name, "UTF-8")
                                    + "&zhicheng=" + URLEncoder.encode(zhicheng, "UTF-8") + "&cla=" + URLEncoder.encode(cla, "UTF-8") + "&info=" + URLEncoder.encode(time, "UTF-8")
                                    + "&special=" + URLEncoder.encode(special, "UTF-8") + "&phone=" + URLEncoder.encode(phone, "UTF-8")
                                    + "&resume=" + URLEncoder.encode(resume, "UTF-8") + "&money=" + URLEncoder.encode(money, "UTF-8")
                                    + "&photo=" + URLEncoder.encode("http://www.starryfei.cn/fei/" + i + ".jpg", "UTF-8") + "&state=" + URLEncoder.encode(text, "UTF-8")+ "&total=" + URLEncoder.encode(total, "UTF-8");
                            url = new URL(u);
                            Log.d("aa",u);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setConnectTimeout(5000);
                            urlConnection.setRequestMethod("GET");
                            urlConnection.connect();
                            // 设置连接超时为5秒
                            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                rDialog.dismiss();
                                Looper.prepare();

                                Toast.makeText(AddDoctorActivity.this, "添加医生信息成功!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(AddDoctorActivity.this, FirstActivity.class);
                                startActivity(i);
                                Looper.loop();
//

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
//
        }
    }



    private void ShowToask(String name) {
        Toast.makeText(AddDoctorActivity.this, name, Toast.LENGTH_SHORT).show();
    }

}
