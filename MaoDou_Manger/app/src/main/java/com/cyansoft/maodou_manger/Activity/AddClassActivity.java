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
import android.widget.ImageView;
import android.widget.Toast;

import com.cyansoft.maodou_manger.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddClassActivity extends AppCompatActivity implements View.OnClickListener {
 private EditText t_cla,t_discrible;
    private Button btn_submit;
    private ProgressDialog rDialog;
    private ImageView iback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_class);
        initview();
    }

    private void initview() {
     t_cla = (EditText) findViewById(R.id.cla_name);
        t_discrible = (EditText) findViewById(R.id.cla_discrible);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        iback = (ImageView) findViewById(R.id.img_back);
        iback.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                if (t_cla.getText().toString() == null || "".equals(t_cla.getText().toString().trim())) {
                    ShowToask("科室名字不能为空");
                    break;
                }else {
                    // 提示框
                    rDialog = new ProgressDialog(this);
                    rDialog.setTitle("提示");
                    rDialog.setMessage("正在提交,请稍后....");
                    rDialog.setCancelable(true);
                    rDialog.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String cla = t_cla.getText().toString();
                            String discrible = t_discrible.getText().toString();
                            HttpURLConnection urlConnection = null;
                            URL url = null;
                            try {
                                String u = "http://www.starryfei.cn:8080/MVC/InsertClassServlet?cla=" + URLEncoder.encode(cla, "UTF-8")
                                        + "&discrible=" + URLEncoder.encode(discrible, "UTF-8") ;
                                Log.d("aa",u);
                                url = new URL(u);
                                urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setConnectTimeout(5000);
                                urlConnection.setRequestMethod("GET");
                                urlConnection.connect();
                                // 设置连接超时为5秒
                                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                    rDialog.dismiss();
                                    Looper.prepare();

                                    Toast.makeText(AddClassActivity.this, "添加成功!", Toast.LENGTH_SHORT).show();
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
    }
    private void ShowToask(String name) {
        Toast.makeText(AddClassActivity.this, name, Toast.LENGTH_SHORT).show();
    }
}
