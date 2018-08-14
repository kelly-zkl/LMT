package com.wisec.scanner.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wisec.scanner.R;
import com.wisec.scanner.base.BaseActivity;
import com.wisec.scanner.bean.ClearScanner;
import com.wisec.scanner.ui.scan.ScanFragment;
import com.wisec.scanner.ui.scanset.ScanSetActivity;
import com.wisec.scanner.ui.set.SetFragment;
import com.wisec.scanner.ui.test.TestFragment;
import com.wisec.scanner.utils.AsecUtil;
import com.wisec.scanner.utils.HttpUtils;
import com.wisec.scanner.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private DrawerLayout mDrawer;
    private View mView, mScan, mDark;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<Fragment> fragments;

    private PopupWindow popupWindow;

    private long timeMillis;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ScanFragment scanFragment = ScanFragment.newInstance("1", "2");
        TestFragment testFragment = TestFragment.newInstance("1", "2");
        SetFragment setFragment = SetFragment.newInstance("1", "2");
        fragments = new ArrayList<>();
        fragments.add(scanFragment);
        fragments.add(testFragment);
        fragments.add(setFragment);
        initUI();

        try {
            timeMillis = System.currentTimeMillis();
            version = "v1.0";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("identify", String.valueOf(timeMillis));
            jsonObject.put("version", version);
            outTime(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断应用是否过期
//        try {
//            timeMillis = System.currentTimeMillis();
//            version = "v1.0";
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("identify", String.valueOf(timeMillis));
//            jsonObject.put("version", version);
//            outTime(jsonObject.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 接口调用是否过期
     */
    private void outTime(final String content) {
        OkHttpClient client = new OkHttpClient();
        //构建FormBody，传入要提交的参数

        LogUtils.I("http", content);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), content);
        final Request request = new Request.Builder()
                .url(HttpUtils.HTTP_URL)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.I("http", e.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        exitApp("网络错误，请检查您的网络");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                LogUtils.I("http", responseStr);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(responseStr);
                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("msg");
                            if (code.equals("000000")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                boolean enabled = data.getBoolean("enabled");
                                long timestamp = data.getLong("timestamp");
                                String token = data.getString("token");
                                if (enabled) {
                                    boolean isTrue = new AsecUtil().isEnable(String.valueOf(timeMillis) + version, enabled, timestamp, token);
                                    LogUtils.I("http", isTrue + "");
                                    if (isTrue) {//验证成功
                                        Toast.makeText(HomeActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        exitApp("验证失败");
                                    }
                                } else {
                                    exitApp("已过期");
                                }
                            } else {
                                exitApp(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    //退出程序
    private void exitApp(String text) {
        Toast.makeText(HomeActivity.this, text, Toast.LENGTH_SHORT).show();
        mHandler.sendEmptyMessageDelayed(20, 2000);
    }

    //关闭进程
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 20:
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                default:
                    break;
            }
        }
    };

    private void initUI() {
        mView = findViewById(R.id.home_view);
        mScan = mView.findViewById(R.id.btn_scan);
        mDark = mView.findViewById(R.id.dark);
        mScan.setOnClickListener(this);
//        mDrawer = findViewById(R.id.drawer_layout);
//        mView.findViewById(R.id.btn_nav).setOnClickListener(this);
        //LOG设置
//        findViewById(R.id.btn_log_set).setOnClickListener(this);
        //测试设置
//        findViewById(R.id.btn_test_set).setOnClickListener(this);

        ((CheckBox) mView.findViewById(R.id.btn_lock)).setOnCheckedChangeListener(this);

        showBtn(true, 0);
        initViewPager();
    }

    private void initViewPager() {
        viewPager = mView.findViewById(R.id.vp_home);
        radioGroup = mView.findViewById(R.id.rg);
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                fragments.set(position, fragment);
                return fragment;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
            }
        };
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0, true);
        viewPager.setOnPageChangeListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void showBtn(boolean isShow, int posotion) {
//        ((TextView) mView.findViewById(R.id.tv_name)).setText(isShow ? "扫频" : "测试");
        ((TextView) mView.findViewById(R.id.tv_name)).setText(posotion == 0 ? "扫频" : posotion == 1 ? "测试" : "设置");
        mView.findViewById(R.id.btn_lock).setVisibility(View.GONE);
//        mView.findViewById(R.id.btn_lock).setVisibility(isShow ? View.VISIBLE : View.GONE);
        mView.findViewById(R.id.btn_scan).setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    private void showPopu() {
        if (popupWindow == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popu_home, null, false);
            contentView.findViewById(R.id.btn_scan_set).setOnClickListener(this);
            contentView.findViewById(R.id.btn_clear).setOnClickListener(this);
            popupWindow = new PopupWindow(contentView, getResources().getDimensionPixelSize(R.dimen.PX380), ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mDark.setVisibility(View.GONE);
                }
            });
        }
        mDark.setVisibility(View.VISIBLE);
        popupWindow.showAsDropDown(mScan, 0, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
        showBtn(true, position);
//        if ((position % 3) == 0) {
//            showBtn(true);
//        } else {
//            showBtn(false);
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_scan:
                viewPager.setCurrentItem(0, true);
                showBtn(true, 0);
                break;
            case R.id.rbtn_test:
                viewPager.setCurrentItem(1, true);
                showBtn(false, 1);
                break;
            case R.id.rbtn_set:
                viewPager.setCurrentItem(2, true);
                showBtn(false, 2);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_nav://打开侧边栏
//                mDrawer.openDrawer(Gravity.START);
//                break;
//            case R.id.btn_log_set://log设置
////                mDrawer.closeDrawers();
////                startActivity(new Intent(HomeActivity.this, LogSetActivity.class));
//                break;
//            case R.id.btn_test_set://测试设置
////                mDrawer.closeDrawers();
////                startActivity(new Intent(HomeActivity.this, TestSetActivity.class));
//                break;
            case R.id.btn_scan:
                showPopu();
                break;
            case R.id.btn_scan_set://扫频设置
                startActivity(new Intent(HomeActivity.this, ScanSetActivity.class));
                popupWindow.dismiss();
                break;
            case R.id.btn_clear://清空扫频数据
                popupWindow.dismiss();
                EventBus.getDefault().post(new ClearScanner());
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            LogUtils.I("http", "锁频");
        } else {
            LogUtils.I("http", "解除锁频");
        }
    }
}
