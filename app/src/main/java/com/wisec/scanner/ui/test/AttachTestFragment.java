package com.wisec.scanner.ui.test;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wisec.scanner.R;
import com.wisec.scanner.adapter.ItemTestAdapter;
import com.wisec.scanner.bean.AttachParam;
import com.wisec.scanner.bean.FailBean;
import com.wisec.scanner.ui.testset.AttachSetActivity;
import com.wisec.scanner.utils.DateUtils;
import com.wisec.scanner.utils.LogUtils;
import com.wisec.scanner.utils.SPUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import diag.cmd.ui_cmd_mode_lpm;
import diag.cmd.ui_cmd_mode_online;
import diag.ui_cmd_agent;

import static com.wisec.scanner.utils.ConstUtils.ATTACH_KEY;
import static com.wisec.scanner.utils.ConstUtils.ATTACH_LPM;
import static com.wisec.scanner.utils.ConstUtils.ATTACH_ONLINE;
import static com.wisec.scanner.utils.ConstUtils.SEC;
import static com.wisec.scanner.utils.ConstUtils.SHARE_NAME;
import static diag.ui_cmd_code.cmd_code_mode_lpm;
import static diag.ui_cmd_code.cmd_code_mode_online;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttachTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttachTestFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "AttachTestFragment";

    private View view;
    private Switch mSwTest;
    private TextView mTvReq, mTvRej, mTvSuc;

    private RecyclerView recyclerView;
    private List<FailBean> failBeans;
    private ItemTestAdapter adapter;

    private int allCount = 0, successCount = 0, count = 0, space = 0;
    private boolean isRemove;

    public AttachTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AttachTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttachTestFragment newInstance(String param1, String param2) {
        AttachTestFragment fragment = new AttachTestFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_test_attach, container, false);

        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        failBeans = new ArrayList<>();
        adapter = new ItemTestAdapter(failBeans, getActivity());

        recyclerView = view.findViewById(R.id.rcv_test);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        View serView = LayoutInflater.from(getContext()).inflate(R.layout.layout_test_header, recyclerView, false);
        adapter.setHeaderView(serView);
        recyclerView.setAdapter(adapter);

        mSwTest = view.findViewById(R.id.sw_attach);
        mTvReq = view.findViewById(R.id.tv_req);
        mTvRej = view.findViewById(R.id.tv_reject);
        mTvSuc = view.findViewById(R.id.tv_success);

        mSwTest.setOnCheckedChangeListener(this);

        count();
    }

    /**
     * 获取测试周期、间隔、次数
     */
    private void getCount() {
        SPUtils spUtils = new SPUtils(getActivity(), SHARE_NAME);
        AttachParam attachParam = spUtils.getObj(ATTACH_KEY, AttachParam.class);
        if (null != attachParam) {
            count = attachParam.getCount();
            space = attachParam.getSpace();
        } else {
            Toast.makeText(getActivity(), "请设置测试参数", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), AttachSetActivity.class));
        }
    }

    /**
     * 计算
     */
    private void count() {
        if (allCount != 0) {
            mTvReq.setText(allCount + "");
            mTvRej.setText(successCount + "");
            mTvSuc.setText(String.format("%.2f", (successCount * 100.0) / allCount) + "%");
        } else {
            mTvReq.setText("0");
            mTvRej.setText("0");
            mTvSuc.setText("0.00%");
        }
    }

    /**
     * mode lpm命令
     */
    private void startLpm() {
        if (count > 0) {
            ui_cmd_agent.mode_lpm();//间隔1s之后查询lpm状态
            mHandler.sendEmptyMessageDelayed(cmd_code_mode_lpm, SEC);
            count--;
        }
    }

    /**
     * lpm返回状态
     */
    private void updateState() {
        LogUtils.D(TAG, "lpm成功 = " + ui_cmd_mode_lpm.is_mode_lpm_ok());

        allCount++;//成功之后间隔10s调用online
        mHandler.sendEmptyMessageDelayed(ATTACH_ONLINE, SEC);

        if (!ui_cmd_mode_lpm.is_mode_lpm_ok()) {//发送成功
            FailBean failBean = new FailBean(DateUtils.getDefaultDate(System.currentTimeMillis()), "lpm fail");
            Collections.reverse(failBeans);
            failBeans.add(failBean);
            Collections.reverse(failBeans);
            adapter.notifyDataSetChanged();
        }
        count();
    }

    /**
     * mode online命令
     */
    private void modeOnline() {
        ui_cmd_agent.mode_online();//间隔1s后查询online是否成功
        mHandler.sendEmptyMessageDelayed(cmd_code_mode_online, SEC);
    }

    /**
     * online返回状态
     */
    private void updateOnline() {
        LogUtils.D(TAG, "Online成功 = " + ui_cmd_mode_online.is_mode_online_ok());
        if (ui_cmd_mode_online.is_mode_online_ok()) {//接入成功
            successCount++;//成功之后间隔10s调用lpm
        } else {//失败记录一次
            FailBean failBean = new FailBean(DateUtils.getDefaultDate(System.currentTimeMillis()), "online fail");
            Collections.reverse(failBeans);
            failBeans.add(failBean);
            Collections.reverse(failBeans);
            adapter.notifyDataSetChanged();
        }
        if (!isRemove)
            mHandler.sendEmptyMessageDelayed(ATTACH_LPM, SEC * space);

        count();
    }

    /**
     * 测试开关
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {//打开后重新计数
            isRemove = false;
            allCount = 0;
            successCount = 0;
            count = 0;
            space = 0;
            getCount();
            count();
            mHandler.sendEmptyMessage(ATTACH_LPM);
        } else {
            mHandler.removeMessages(ATTACH_LPM);
            mHandler.removeMessages(ATTACH_ONLINE);
            isRemove = true;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ATTACH_LPM://LPM
                    startLpm();
                    break;
                case ATTACH_ONLINE://ONLINE
                    modeOnline();
                    break;
                case cmd_code_mode_lpm://lpm结果反馈
                    LogUtils.D(TAG, "lpm成功 = " + ui_cmd_mode_lpm.rsp_code);
                    updateState();
                    break;
                case cmd_code_mode_online://online结果反馈
                    LogUtils.D(TAG, "Online成功 = " + ui_cmd_mode_online.rsp_code);
                    updateOnline();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mHandler != null) {
            mHandler.removeMessages(ATTACH_LPM);
            mHandler.removeMessages(ATTACH_ONLINE);
            mHandler = null;
        }
    }
}
