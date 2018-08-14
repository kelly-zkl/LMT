package com.wisec.scanner.ui.logset;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wisec.scanner.R;
import com.wisec.scanner.utils.ConstUtils;
import com.wisec.scanner.utils.DateUtils;
import com.wisec.scanner.utils.LogUtils;
import com.wisec.scanner.utils.SDCardUtils;

import java.io.File;

import diag.ui_cmd_agent;

public class LogSetActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView mTvStart, mTvSize, mTvFile;
    private Switch mSwStart;
    private String path, fileName = "/logs.hdl";

    private int targetSdkVersion;
    private static final String TAG = "LogSetActivity";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_set);

        context = LogSetActivity.this;

        path = SDCardUtils.getSDCardPath() + "com.wisec.scanner/Logs";
        LogUtils.I(TAG, "文件目录 = " + path + fileName);

        checkPermission();

        if (!selfPermissionGranted("android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(LogSetActivity.this, new String[]{android
                    .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //创建文件夹
            createFiles();
        }

        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.log_collection_set);
        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);
        findViewById(R.id.rl_model).setOnClickListener(this);
//        findViewById(R.id.rl_open_file).setOnClickListener(this);

        initView();
    }

    public boolean selfPermissionGranted(String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (targetSdkVersion >= Build.VERSION_CODES.M) {//大于23
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {//小于23
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }

    private void checkPermission() {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * view初始化
     */
    private void initView() {
        mTvStart = findViewById(R.id.tv_start);
        mTvSize = findViewById(R.id.tv_storage_size);
        mTvFile = findViewById(R.id.tv_file);
        mSwStart = findViewById(R.id.sw_log);

        mSwStart.setOnCheckedChangeListener(this);

        mTvFile.setText(path);

        String space = SDCardUtils.getFreeSpace();
        mTvSize.setText("剩余存储空间" + space);

        mHandler.sendEmptyMessageDelayed(ConstUtils.QUERY_SIZE, ConstUtils.SEC * 10);
    }

    /**
     * 创建文件夹
     */
    private void createFiles() {
        //新建一个File，传入文件夹目录
        File file = new File(path);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        LogUtils.I(TAG, "文件夹是否存在 = " + file.exists());
        if (!file.exists()) {
            //通过file的mkdirs()方法创建
            boolean isEx = file.mkdirs();
            LogUtils.I(TAG, "创建成功 = " + isEx);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return:
                finish();
                break;
            case R.id.rl_model://导入模板文件
//                startActivity(new Intent(LogSetActivity.this, LogImportActivity.class));
                break;
            case R.id.rl_open_file://打开日志目录
//                File file = new File(path);
//                Uri uri = Uri.fromFile(file);
//                Intent intent = new Intent("android.intent.action.VIEW");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addCategory("android.intent.category.DEFAULT");
//                intent.setDataAndType(uri, "*/*");
//                startActivity(intent);
                break;
        }
    }

    /**
     * 查询内存卡大小
     */
    private void querySize() {
        if (SDCardUtils.isSDCardEnable()) {//判断是否存在sd卡
            String space = SDCardUtils.getFreeSpace();
            mTvSize.setText("剩余存储空间" + space);
            LogUtils.I(TAG, "剩余存储空间 = " + space);
        }
    }

    /**
     * 开始采集log
     * 配置文件，log保存位置，及最大保存文件大小
     */
    private void startLog() {
        int size = (int) (SDCardUtils.getFreeSpaceLong() / 3000);
        LogUtils.I(TAG, "byte = " + SDCardUtils.getFreeSpaceLong());
        LogUtils.I(TAG, "剩余存储空间 = " + SDCardUtils.getFreeSpaceLong() + ",size = " + size);
        String time = DateUtils.getDateString(System.currentTimeMillis(), "yy-MM-dd HH:mm:ss");
        fileName = "/log-" + time + ".hdl";
        ui_cmd_agent.start_sd_log("", path + fileName, size);
    }

    /**
     * 停止采集log
     */
    private void stopLog() {
        ui_cmd_agent.stop_sd_log();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {//true开始采集
            Toast.makeText(LogSetActivity.this, "剩余空间" + SDCardUtils.getFreeSpace(), Toast.LENGTH_SHORT).show();
            mTvStart.setText("结束采集");
            startLog();
        } else {//false结束采集
            mTvStart.setText("开始采集");
            stopLog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //创建文件夹
                    createFiles();
                    break;
                }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstUtils.QUERY_SIZE://定时查询空间大小
                    querySize();
                    mHandler.sendEmptyMessageDelayed(ConstUtils.QUERY_SIZE, ConstUtils.SEC * 10);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ui_cmd_agent.stop_sd_log();
        if (mHandler != null) {
            mHandler.removeMessages(ConstUtils.QUERY_SIZE);
        }
    }
}
