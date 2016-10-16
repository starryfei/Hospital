package com.cyansoft.maodou_manger.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText t_name, t_pwd, t_phone, t_email, t_info;
    private TextView t_back;
    private Button submit;
    private MyApplication myApplication;
    private ProgressDialog rDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_info);
        myApplication = (MyApplication) getApplication();
        if (!myApplication.isLogin){
            Toast.makeText(UpdateInfoActivity.this,"请登录后在查看个人信息!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UpdateInfoActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
        initview();
    }

    private void initview() {
        t_name = (EditText) findViewById(R.id.user_name);
        t_pwd = (EditText) findViewById(R.id.user_pwd);
        t_phone = (EditText) findViewById(R.id.user_phone);
        t_email = (EditText) findViewById(R.id.user_email);
        t_info = (EditText) findViewById(R.id.user_info);
        submit = (Button) findViewById(R.id.update_btn);
        t_back = (TextView) findViewById(R.id.back_text);

        t_name.setText(myApplication.getUsername());
        t_phone.setText(myApplication.getUserphone());
        t_pwd.setText(myApplication.getUserPwd());
        t_email.setText(myApplication.getUseremail());
        t_info.setText(myApplication.getUserinfo());
        submit.setOnClickListener(this);
        t_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_text:
                finish();
                break;
            case R.id.update_btn:
                if (t_name.getText().toString() == null || "".equals(t_name.getText().toString().trim())) {
                    ShowToask("用户名不能为空");
                    break;
                }
                if (t_pwd.getText().toString() == null || "".equals(t_pwd.getText().toString().trim())) {
                    ShowToask("密码不能为空");
                    Log.e("tag", "======Password");
                    break;
                }
                if (t_phone.getText().toString() == null || "".equals(t_phone.getText().toString().trim())) {
                    ShowToask("电话号码不能为空");
                    break;
                }
                if (t_email.getText().toString() == null || "".equals(t_email.getText().toString().trim())) {
                    ShowToask("邮箱不能为空");
                    break;
                }
                // 提示框
                rDialog = new ProgressDialog(this);
                rDialog.setTitle("提示");
                rDialog.setMessage("正在更改,请稍后....");
                rDialog.setCancelable(true);
                rDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = t_name.getText().toString();
                        String pwd = t_pwd.getText().toString();
                        String phone = t_phone.getText().toString();
                        String email = t_email.getText().toString();
                        String info = t_info.getText().toString();
                        HttpURLConnection urlConnection = null;
                        URL url = null;
                        try {
                            String u = "http://www.starryfei.cn:8080/MVC/UpdateUserServlet?username=" + URLEncoder.encode(name, "UTF-8") + "&userpwd=" + URLEncoder.encode(pwd, "UTF-8") + "&userphone=" + URLEncoder.encode(phone, "UTF-8") + "" +
                                    "&useremail=" + URLEncoder.encode(email, "UTF-8") + "&userinfo=" + URLEncoder.encode(info, "UTF-8");
                            url = new URL(u);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setConnectTimeout(5000);
                            urlConnection.setRequestMethod("GET");
                            urlConnection.connect();
                            // 设置连接超时为5秒
                            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                rDialog.dismiss();
                                Looper.prepare();
                                Toast.makeText(UpdateInfoActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                                finish();
                                Looper.loop();
//

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    private void ShowToask(String name) {
        Toast.makeText(UpdateInfoActivity.this, name, Toast.LENGTH_SHORT).show();
    }
}
