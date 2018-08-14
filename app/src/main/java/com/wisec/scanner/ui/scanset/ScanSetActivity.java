package com.wisec.scanner.ui.scanset;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.LockBandBean;
import com.wisec.scanner.bean.LockEarfcnBean;
import com.wisec.scanner.bean.LockNetBean;
import com.wisec.scanner.bean.LockPciBean;
import com.wisec.scanner.utils.ConstUtils;
import com.wisec.scanner.utils.LogUtils;
import com.wisec.scanner.utils.NumberUtils;
import com.wisec.scanner.utils.SPUtils;
import com.wisec.scanner.utils.SystemManager;

import diag.ui_cmd_agent;

import static com.wisec.scanner.utils.ConstUtils.LOCK_BAND_KEY;
import static com.wisec.scanner.utils.ConstUtils.LOCK_EARFCN_KEY;
import static com.wisec.scanner.utils.ConstUtils.LOCK_NET_KEY;
import static com.wisec.scanner.utils.ConstUtils.LOCK_PCI_KEY;
import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_DEFAULT_NETWORK;

public class ScanSetActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ScanSetActivity";

    private SPUtils spUtils;

    private TextView mTvNet, mTvBand, mTvEarfcn, mTvPci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_set);

        spUtils = new SPUtils(this, ConstUtils.SHARE_NAME);

        initView();

    }

    private void initView() {
        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.san_set);
        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);

        findViewById(R.id.rl_net).setOnClickListener(this);
        findViewById(R.id.rl_band).setOnClickListener(this);
        findViewById(R.id.rl_earfcn).setOnClickListener(this);
        findViewById(R.id.rl_pci).setOnClickListener(this);
        findViewById(R.id.btn_default).setOnClickListener(this);

        mTvNet = findViewById(R.id.tv_lock_net);
        mTvBand = findViewById(R.id.tv_lock_band);
        mTvEarfcn = findViewById(R.id.tv_lock_earfcn);
        mTvPci = findViewById(R.id.tv_lock_pci);

        initData();
    }

    //数据初始化
    private void initData() {
        LockBandBean lockBandBean = spUtils.getObj(LOCK_BAND_KEY, LockBandBean.class);
        StringBuilder sb = new StringBuilder();
        if (null != lockBandBean) {
            if (lockBandBean.isBand1())
                sb.append("band1，");
            if (lockBandBean.isBand3())
                sb.append("band3，");
            if (lockBandBean.isBand5())
                sb.append("band5，");
            if (lockBandBean.isBand7())
                sb.append("band7，");
            if (lockBandBean.isBand8())
                sb.append("band8，");
            if (lockBandBean.isBand38())
                sb.append("band38，");
            if (lockBandBean.isBand39())
                sb.append("band39，");
            if (lockBandBean.isBand40())
                sb.append("band40，");
            if (lockBandBean.isBand41())
                sb.append("band41，");
        } else {
            lockAllBand();
            sb.append("band1，band3，band5，band7，band8，band38，band39，band40，band41，");
        }
        if (!TextUtils.isEmpty(sb.toString())) {
            mTvBand.setText(sb.toString().substring(0, sb.length() - 1));
        }

        LockEarfcnBean lockEarfcnBean = spUtils.getObj(LOCK_EARFCN_KEY, LockEarfcnBean.class);
        if (null != lockEarfcnBean) {
            mTvEarfcn.setText(lockEarfcnBean.getEarfcn() + "");
        }

        LockPciBean lockPciBean = spUtils.getObj(LOCK_PCI_KEY, LockPciBean.class);
        if (null != lockPciBean) {
            mTvPci.setText(lockPciBean.getEarfcn() + "");
        }

        LockNetBean lockNetBean = spUtils.getObj(LOCK_NET_KEY, LockNetBean.class);
        if (null != lockNetBean) {
            mTvNet.setText(lockNetBean.getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return:
                finish();
                break;
            case R.id.rl_net://锁网
                startActivity(new Intent(ScanSetActivity.this, LockNetActivity.class));
                break;
            case R.id.rl_band://锁频段
                startActivity(new Intent(ScanSetActivity.this, LockBandActivity.class));
                break;
            case R.id.rl_earfcn://锁频点
                startActivity(new Intent(ScanSetActivity.this, LockEarfcnActivity.class));
                break;
            case R.id.rl_pci://锁pci
                startActivity(new Intent(ScanSetActivity.this, LockPciActivity.class));
                break;
            case R.id.btn_default://恢复默认设置
                showDialog();
                break;
            case R.id.btn_confirm://恢复默认锁频/网/频段/频点
                spUtils.remove(LOCK_BAND_KEY);
                spUtils.remove(LOCK_EARFCN_KEY);
                spUtils.remove(LOCK_PCI_KEY);
                spUtils.remove(LOCK_NET_KEY);
                ui_cmd_agent.set_preferred_network(NETWORK_MODE_DEFAULT_NETWORK);
                lockAllBand();//频段
                SystemManager.unlock_earfcn();
                SystemManager.unlock_pci();
                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }

    /**
     * 锁定全band
     */
    private int indexs[] = {1, 3, 5, 7, 8, 38, 39, 40, 41};

    private void lockAllBand() {
        long bandHex = 0;
        for (int i = 0; i < indexs.length; i++) {
            LogUtils.I(TAG, "index = " + indexs[i]);
            bandHex += Math.pow(2, indexs[i] - 1);
        }

        LockBandBean lockBand = new LockBandBean(true, true, true, true,
                true, true, true, true, true);
        spUtils.putObj(LOCK_BAND_KEY, lockBand);

        LogUtils.I(TAG, "10进制 = " + bandHex);
        LogUtils.I(TAG, "2进制 = " + NumberUtils.hexStringToBinary(String.format("%x", bandHex)));
        LogUtils.I(TAG, "16进制 = " + String.format("%x", bandHex));
        LogUtils.I(TAG, "16位16进制 = " + String.format("%016X", bandHex));
        /**锁定band*/
        ui_cmd_agent.lock_band(bandHex);
    }

    private AlertDialog dialog;

    private void showDialog() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).create();
            View dialogView = LayoutInflater.from(this)
                    .inflate(R.layout.dialog_confirm_layout, null);
            dialog.setView(dialogView);
            ((TextView) dialogView.findViewById(R.id.tv_dialog_content)).setText("设置后将重启手机，确认要恢复默认设置？");
            dialogView.findViewById(R.id.btn_cancel).setOnClickListener(this);
            dialogView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        }
        dialog.show();
    }
}
