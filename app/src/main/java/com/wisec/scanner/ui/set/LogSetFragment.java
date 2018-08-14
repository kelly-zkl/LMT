package com.wisec.scanner.ui.set;

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
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LogSetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogSetFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private TextView mTvStart, mTvSize, mTvFile;
    private Switch mSwStart;
    private String path, fileName = "/logs.hdl";

    private int targetSdkVersion;
    private static final String TAG = "LogSetFragment";
    private Context context;

    public LogSetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogSetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogSetFragment newInstance(String param1, String param2) {
        LogSetFragment fragment = new LogSetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_log_set, container, false);

        initView();
        return view;
    }

    /**
     * 初始化view
     */
    private void initView() {
        context = getActivity();

        path = SDCardUtils.getSDCardPath() + "com.wisec.scanner/Logs";
        LogUtils.I(TAG, "文件目录 = " + path + fileName);

        checkPermission();

        if (!selfPermissionGranted("android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android
                    .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //创建文件夹
            createFiles();
        }

        view.findViewById(R.id.rl_model).setOnClickListener(this);
//        findViewById(R.id.rl_open_file).setOnClickListener(this);

        mTvStart = view.findViewById(R.id.tv_start);
        mTvSize = view.findViewById(R.id.tv_storage_size);
        mTvFile = view.findViewById(R.id.tv_file);
        mSwStart = view.findViewById(R.id.sw_log);

        mSwStart.setOnCheckedChangeListener(this);

        mTvFile.setText(path);

        String space = SDCardUtils.getFreeSpace();
        mTvSize.setText("剩余存储空间" + space);

        mHandler.sendEmptyMessageDelayed(ConstUtils.QUERY_SIZE, ConstUtils.SEC * 10);
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
            Toast.makeText(context, "剩余空间" + SDCardUtils.getFreeSpace(), Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        ui_cmd_agent.stop_sd_log();
        if (mHandler != null) {
            mHandler.removeMessages(ConstUtils.QUERY_SIZE);
        }
    }
}
