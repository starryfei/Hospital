package com.cyansoft.maodou_manger.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cyansoft.maodou_manger.Adapter.FragmentAdapter;
import com.cyansoft.maodou_manger.R;

public class FirstActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private RadioGroup mRadioGroup;
    private RadioButton first_btn;
//    private RadioButton second_btn;
    private RadioButton three_btn;
    private RadioButton four_btn;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
//    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 1;
    public static final int PAGE_FOUR = 2;
    private ViewPager vpager;

    private FragmentPagerAdapter mAdapter;

    private SharedPreferences sp;
    private MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first);

        mAdapter = new FragmentAdapter(getSupportFragmentManager());

        initview();

        Intent intent = getIntent();
//        Bundle bds = intent.getExtras();
//        if(intent!=null){
//            String name = intent.getStringExtra("USER_NAME");
//            Boolean IsLogin = intent.getBooleanExtra("ISLOGIN",false);
//            myApplication= (MyApplication) getApplication();
//            myApplication.setUsername(name);
//            myApplication.isLogin =IsLogin;
//            Toast.makeText(FirstActivity.this,""+ myApplication.isLogin,Toast.LENGTH_SHORT).show();
//        }
        first_btn.setChecked(true);
    }

    private void initview() {


        first_btn = (RadioButton) findViewById(R.id.first);
//        second_btn = (RadioButton) findViewById(R.id.second);
        three_btn = (RadioButton) findViewById(R.id.three);
        four_btn = (RadioButton) findViewById(R.id.four);
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup_main);

        mRadioGroup.setOnCheckedChangeListener(this);

        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
        Toast.makeText(FirstActivity.this,""+ myApplication.isLogin,Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
        }
        return false;
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("预约挂号平台");
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setMessage("是否退出当前应用？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        }).setNegativeButton("取消", null);

        builder.show();

    }

    @Override
    //重写ViewPager页面切换的处理方法
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    first_btn.setChecked(true);
                    break;
//                case PAGE_TWO:
//                    second_btn.setChecked(true);
//                    break;
                case PAGE_THREE:
                    three_btn.setChecked(true);
                    break;
                case PAGE_FOUR:
                    four_btn.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.first:
//                setToolbar_title(R.string.tab_name1);
                vpager.setCurrentItem(PAGE_ONE);
                break;
//            case R.id.second:
//                setToolbar_title(R.string.tab_name2);
//                vpager.setCurrentItem(PAGE_TWO);
//                break;
            case R.id.three:
//                setToolbar_title(R.string.tab_name3);
                vpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.four:
//                setToolbar_title(R.string.tab_name4);
                vpager.setCurrentItem(PAGE_FOUR);
                break;
            default:
                break;
        }

    }
}
