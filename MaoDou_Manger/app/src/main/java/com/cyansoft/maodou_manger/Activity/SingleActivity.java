package com.cyansoft.maodou_manger.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.cyansoft.maodou_manger.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by author:StarryFei on 2016/6/11 0011.
 * Email:760646630fei@sina.com
 */
public class SingleActivity extends Activity implements OnClickListener {
    private EditText mEditText_name, mEditText_pwd, mEditText_phone, mEditText_email, mEditText_surepwd;
    private Button mSubmitButton;
    private ProgressDialog rDialog;
    private ImageView selectImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.single_activity);
        initview();
    }

    private void initview() {
        mEditText_name = (EditText) findViewById(R.id.NickName_Single);
        mEditText_pwd = (EditText) findViewById(R.id.PassWord_Single);
        mEditText_phone = (EditText) findViewById(R.id.UserPhone_singleActivity);
        mEditText_surepwd = (EditText) findViewById(R.id.PassWord_AginSingle);
        mEditText_email = (EditText) findViewById(R.id.UserEmil_single);
        mSubmitButton = (Button) findViewById(R.id.conmmit_Button);
        selectImg = (ImageView) findViewById(R.id.userImage_single_zhuce);
        selectImg.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userImage_single_zhuce:

                break;
            case R.id.conmmit_Button:
                if (mEditText_name.getText().toString() == null || "".equals(mEditText_name.getText().toString().trim())) {
                    ShowToask("用户名不能为空");
                    break;
                }
                if (mEditText_pwd.getText().toString() == null || "".equals(mEditText_pwd.getText().toString().trim())) {
                    ShowToask("密码不能为空");
                    Log.e("tag", "======Password");
                    break;
                }
                if (mEditText_phone.getText().toString() == null || "".equals(mEditText_phone.getText().toString().trim())) {
                    ShowToask("电话号码不能为空");
                    break;
                }
                if (mEditText_surepwd.getText().toString() == null || "".equals(mEditText_surepwd.getText().toString().trim())) {
                    ShowToask("确认密码不能为空");
                    break;
                }
                if (!mEditText_surepwd.getText().toString().equals(mEditText_pwd.getText().toString())) {
                    ShowToask("确认密码和密码不一致,请重新输入");
                    mEditText_surepwd.setText("");
                    break;
                }
                if (mEditText_email.getText().toString() == null || "".equals(mEditText_email.getText().toString().trim())) {
                    ShowToask("邮箱不能为空");
                    break;
                }
                // 提示框
                rDialog = new ProgressDialog(this);
                rDialog.setTitle("提示");
                rDialog.setMessage("正在注册,请稍后....");
                rDialog.setCancelable(true);
                rDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String name = mEditText_name.getText().toString();
                        String pwd = mEditText_pwd.getText().toString();
                        String phone = mEditText_phone.getText().toString();
                        String email = mEditText_email.getText().toString();
                        HttpURLConnection urlConnection = null;
                        URL url = null;
                        try {
                            String u = "http://www.starryfei.cn:8080/MVC/RegisterServlet?username=" + URLEncoder.encode(name, "UTF-8")
                                    + "&userpwd=" + URLEncoder.encode(pwd, "UTF-8") + "&userphone=" + URLEncoder.encode(phone, "UTF-8") + "&useremail=" + URLEncoder.encode(email, "UTF-8")
                                    + "&userinfo=" + URLEncoder.encode("暂无个人信息", "UTF-8");
                            url = new URL(u);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setConnectTimeout(5000);
                            urlConnection.setRequestMethod("GET");
                            urlConnection.connect();
                            // 设置连接超时为5秒
                            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                rDialog.dismiss();
                                Looper.prepare();

                                Toast.makeText(SingleActivity.this, "注册成功!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SingleActivity.this, LoginActivity.class);
                                startActivity(i);
                                Looper.loop();
//

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
//
        }
    }

    private void ShowToask(String name) {
        Toast.makeText(SingleActivity.this, name, Toast.LENGTH_SHORT).show();
    }

}
