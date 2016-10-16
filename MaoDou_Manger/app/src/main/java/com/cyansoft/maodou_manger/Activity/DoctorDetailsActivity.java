package com.cyansoft.maodou_manger.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.R;

import org.xutils.x;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoctorDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img;
    private TextView t_name, t_cla, t_phone, t_resume, t_info, t_special, t_zhicheng, t_money, t_state, t_total;
    private ImageView iBack;
    private TextView t_into, t_buynow;
    private MyApplication myApplication;
    private ImageView yuyue_Img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_doctor_details);
        myApplication = (MyApplication) getApplication();
//        myApplication.setUsername(name);
        initview();
    }

    private void initview() {
        img = (ImageView) findViewById(R.id.doctor_img);
        t_name = (TextView) findViewById(R.id.doctor_name);
        t_cla = (TextView) findViewById(R.id.doctor_cla);
        t_info = (TextView) findViewById(R.id.doctor_info);
        t_phone = (TextView) findViewById(R.id.doctor_phone);
        t_resume = (TextView) findViewById(R.id.doctor_resume);
        t_special = (TextView) findViewById(R.id.doctor_special);
        t_zhicheng = (TextView) findViewById(R.id.doctor_zhicheng);
        t_money = (TextView) findViewById(R.id.doctor_money);
        t_state = (TextView) findViewById(R.id.doctor_state);
        iBack = (ImageView) findViewById(R.id.img_back);
        t_into = (TextView) findViewById(R.id.doctor_addtocar);
        t_buynow = (TextView) findViewById(R.id.doctor_buy_now);
        yuyue_Img = (ImageView) findViewById(R.id.gouwuche);
        t_total = (TextView) findViewById(R.id.doctor_toatal);
        yuyue_Img.setOnClickListener(this);
        iBack.setOnClickListener(this);
        t_into.setOnClickListener(this);
        t_buynow.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bds = intent.getExtras();
        if (intent != null) {
            String name = bds.getString("name");
            String cla = bds.getString("class");
            String phone = bds.getString("phone");
            String zhicheng = bds.getString("zhicheng");
            String resume = bds.getString("resume");
            String info = bds.getString("info");
            String special = bds.getString("special");
            String money = bds.getString("money");
            String state = bds.getString("state");
            String total = bds.getString("total");
//            t_name.setText(name + "医生主页");
//            t_cla.setText("所属科室: " + cla);
//            t_special.setText("擅长领域: " + special);
//            t_zhicheng.setText("职称: " + zhicheng);
//            t_info.setText("出诊时间: " + resume);
//            t_resume.setText("个人简历: " + info);
//            t_phone.setText("联系方式: " + phone);
//            t_money.setText("¥" + money);
            t_name.setText(name);
            t_cla.setText(cla);
            t_special.setText(special);
            t_zhicheng.setText(zhicheng);
            t_info.setText(resume);
            t_resume.setText(info);
            t_phone.setText(phone);
            t_money.setText(money);
            t_state.setText(state);
            t_total.setText(total);
            String imgUrl = bds.getString("photo");
            x.image().bind(img, imgUrl);
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.doctor_addtocar:
                if (myApplication.isLogin) {
                    if (t_state.getText().toString().equals("就诊")) {
//                    Toast.makeText(DoctorDetailsActivity.this, "预约成功!", Toast.LENGTH_SHORT).show();
                        int a = Integer.parseInt(t_total.getText().toString());
                        if (a > 0) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String doctorname = t_name.getText().toString();
                                    URL aa = null;
                                    HttpURLConnection urlConnection = null;
                                    String Ur= null;
                                    try {
                                        Ur = "http://www.starryfei.cn:8080/MVC/UpdateTotalServlet?username="+ URLEncoder.encode(doctorname, "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        aa = new URL(Ur);
                                        Log.d("aa", Ur);
                                        urlConnection = (HttpURLConnection) aa.openConnection();
                                        urlConnection.setConnectTimeout(5000);
                                        urlConnection.setRequestMethod("GET");
                                        urlConnection.connect();
                                        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                                        }
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    } catch (ProtocolException e1) {
                                        e1.printStackTrace();
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            }).start();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String username = myApplication.getUsername().toString();
                                        String doctorname = t_name.getText().toString();
                                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                                        String time = formatter.format(curDate).toString();
                                        String money = t_money.getText().toString();
                                        String cla = t_cla.getText().toString();
                                        String state = "failure";
                                        HttpURLConnection urlConnection = null;
                                        URL url = null;
                                        String u = "http://www.starryfei.cn:8080/MVC/InsertYuyueServlet?username=" + URLEncoder.encode(username, "UTF-8")
                                                + "&doctorname=" + URLEncoder.encode(doctorname, "UTF-8") + "&time=" + URLEncoder.encode(time, "UTF-8") + "&money=" + URLEncoder.encode(money, "UTF-8")
                                                + "&cla=" + URLEncoder.encode(cla, "UTF-8") + "&state=" + URLEncoder.encode(state, "UTF-8");
                                        url = new URL(u);
                                        Log.d("aa", u);
                                        urlConnection = (HttpURLConnection) url.openConnection();
                                        urlConnection.setConnectTimeout(5000);
                                        urlConnection.setRequestMethod("GET");
                                        urlConnection.connect();
                                        // 设置连接超时为5秒
                                        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            Toast.makeText(DoctorDetailsActivity.this, "预约成功!", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(DoctorDetailsActivity.this, ReservationActivity.class);
                            startActivity(in);

                        } else {
                            Toast.makeText(DoctorDetailsActivity.this, "医生预约名额已满,无法预约!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DoctorDetailsActivity.this, "医生处于停诊状态,暂时无法预约!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(DoctorDetailsActivity.this, "请登录后再预约!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DoctorDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.doctor_buy_now:
                if (myApplication.isLogin) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("是否立即支付?");
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setMessage("支付金额为:" + t_money.getText().toString());
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            Toast.makeText(DoctorDetailsActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
//                            android.os.Process.killProcess(android.os.Process.myPid());

                        }
                    }).setNegativeButton("取消", null);

                    builder.show();
                } else {
                    Toast.makeText(DoctorDetailsActivity.this, "请登录后再支付!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DoctorDetailsActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            case R.id.gouwuche:
                Intent in = new Intent(DoctorDetailsActivity.this, ReservationActivity.class);
                startActivity(in);
                break;
        }
    }
}
