package com.cyansoft.maodou_manger.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.R;
import com.cyansoft.maodou_manger.Service.WebService;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements OnClickListener {
    private TextView backtext, taobaotext, weixintext, weibotext, qqtext, newUsertext, wangpasswordtext;
    private EditText Username, Password;
    private Button login;
    private String nowusername;
    private String nowpassword;
//    存储用户信息
    private SharedPreferences sp;
    // 创建等待框
    private ProgressDialog dialog;
    // 返回的数据
    private String info;
private MyApplication    myApplication;
    // 返回主线程更新数据

    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();

    }


    //控件初始化
    private void initView() {
        backtext = (TextView) findViewById(R.id.back_textview_loginActivity);
        taobaotext = (TextView) findViewById(R.id.taobao_login);
        weixintext = (TextView) findViewById(R.id.weixin_login);
        weibotext = (TextView) findViewById(R.id.weibo_login);
        qqtext = (TextView) findViewById(R.id.qq_login);
        newUsertext = (TextView) findViewById(R.id.newUser_gotosingle);
        wangpasswordtext = (TextView) findViewById(R.id.wangPassword_xiugai);
        Username = (EditText) findViewById(R.id.UserName_login);
        Password = (EditText) findViewById(R.id.password_login);
        login = (Button) findViewById(R.id.login_login_BUtton);
        backtext.setOnClickListener(this);
        taobaotext.setOnClickListener(this);
        weixintext.setOnClickListener(this);
        weibotext.setOnClickListener(this);
        qqtext.setOnClickListener(this);
        newUsertext.setOnClickListener(this);
        wangpasswordtext.setOnClickListener(this);
        Username.setOnClickListener(this);
        Password.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_textview_loginActivity:
                finish();
                break;
            case R.id.taobao_login:
                ShowToask("淘宝");
                break;
            case R.id.weixin_login:
                ShowToask("微信");
                break;
            case R.id.weibo_login:
                ShowToask("微博");
                break;
            case R.id.qq_login:
                ShowToask("qq");
                break;
            case R.id.newUser_gotosingle:
                Intent intent = new Intent(LoginActivity.this, SingleActivity.class);
                startActivity(intent);
                break;
            case R.id.wangPassword_xiugai:
                ShowToask("忘了就忘了吧，忘了吧……");
                break;
            case R.id.login_login_BUtton:
                // 检测网络，无法检测wifi
                if (!checkNetwork()) {
                    Toast toast = Toast.makeText(LoginActivity.this, "网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                } else {
                    Log.e("tag", "======login_button");
                    if (Username.getText().toString() == null || "".equals(Username.getText().toString().trim())) {
                        ShowToask("手机号不能为空");
//                    isok = false;
                        Log.e("tag", "======Username");
                        break;
                    }
                    if (Password.getText().toString() == null || "".equals(Password.getText().toString().trim())) {
                        ShowToask("密码不能为空");
//                    isok = false;
                        Log.e("tag", "======Password");
                        break;
                    }
                    // 提示框
                    dialog = new ProgressDialog(this);
                    dialog.setTitle("提示");
                    dialog.setMessage("正在登陆，请稍后...");
                    dialog.setCancelable(true);
                    dialog.show();
                    // 创建子线程，
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String username = Username.getText().toString();
                            String password = Password.getText().toString();
                            String path = "http://www.starryfei.cn:8080/MVC/JSONDemoServlet";
                            path = path + "?username=" + username + "&password=" + password;
                            new NewsAsynTack().execute(path);//execute启动AsynTack
                        }
                    }).start();
                    break;

                }

        }
    }

    private void ShowToask(String name) {
        Toast.makeText(LoginActivity.this, name, Toast.LENGTH_SHORT).show();
    }

    // 检测网络
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }


    /**
     * 实现网络异步访问
     * <url类型,进度值类型,返回值类型>
     */
    class NewsAsynTack extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return getJsonData(params[0]);

        }

    }

    /**
     * 讲URL对应的json格式转化为String
     *
     * @param url
     * @return
     */
    private String getJsonData(String url) {
        String getMsg = null;
        try {
            String jsonString = readStream(new URL(url).openStream());
            Log.d("aa", jsonString);
            JSONObject jsonobject;
            try {
                JSONArray jsonarray = new JSONArray(jsonString);
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);
                    getMsg = jsonobject.getString("msg");
                    String getName = jsonobject.getString("username");
                    String getPwd = jsonobject.getString("userpwd");
                    String getPhone = jsonobject.getString("userphone");
                    String getEmail = jsonobject.getString("useremail");
                    String getInfo = jsonobject.getString("userinfo");
                    if (getMsg.equals("success")) {
                        if (getName.equals(Username.getText().toString()) && getPwd.equals(Password.getText().toString())) {
                            myApplication= (MyApplication) getApplication();
                            myApplication.setUsername(getName);
                            myApplication.setUserphone(getPhone);
                            myApplication.setUseremail(getEmail);
                            myApplication.setUserinfo(getInfo);
                            myApplication.setUserPwd(getPwd);
                            myApplication.isLogin=true;
                            //记住用户名、密码、
                            Intent in = new Intent(LoginActivity.this, FirstActivity.class);
//                            Bundle bd = new Bundle();
//                            in.putExtra("ISLOGIN",true);
//                            in.putExtra("USER_NAME", getName);
//                            in.putExtras(bd);
                            startActivity(in);
                        } else {
                            dialog.dismiss();
                            finish();
                        }
                    } else {
                        dialog.dismiss();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return getMsg;
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
}
