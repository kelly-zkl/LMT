package com.wisec.scanner.ui.scanset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.LockBandBean;
import com.wisec.scanner.utils.LogUtils;
import com.wisec.scanner.utils.NumberUtils;
import com.wisec.scanner.utils.SPUtils;

import diag.ui_cmd_agent;

import static com.wisec.scanner.utils.ConstUtils.LOCK_BAND_KEY;
import static com.wisec.scanner.utils.ConstUtils.SHARE_NAME;

public class LockBandActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "LockBandActivity";

    private int indexs[] = {1, 3, 5, 7, 8, 38, 39, 40, 41};
    private boolean bands[] = {false, false, false, false, false, false, false, false, false};

    private long bandHex = 0;

    private SPUtils spUtils;
    private LockBandBean lockBandBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_band);
        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.lock_band);

        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);
        findViewById(R.id.layout_title).findViewById(R.id.tv_save).setOnClickListener(this);

//        ui_cmd_agent.get_supported_band();
//        long band = ui_cmd_band_get.supported_band | 0xffff;
//        LogUtils.I(TAG, "获取的band = " + band);
//        LogUtils.I(TAG, "2进制 = " + NumberUtils.hexStringToBinary(String.format("%x", band)));
//        LogUtils.I(TAG, "16进制 = " + String.format("%x", band));
//        LogUtils.I(TAG, "16位16进制 = " + String.format("%016X", band));

        spUtils = new SPUtils(this, SHARE_NAME);
        lockBandBean = spUtils.getObj(LOCK_BAND_KEY, LockBandBean.class);
        if (null == lockBandBean) {
            lockBandBean = new LockBandBean(false, false, false, false,
                    false, false, false, false, false);
            bands = new boolean[]{false, false, false, false, false, false, false, false, false};
        } else {
            bands = new boolean[]{lockBandBean.isBand1(), lockBandBean.isBand3(), lockBandBean.isBand5(),
                    lockBandBean.isBand7(), lockBandBean.isBand8(), lockBandBean.isBand38(),
                    lockBandBean.isBand39(), lockBandBean.isBand40(), lockBandBean.isBand41()};
        }

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        ((CheckBox) findViewById(R.id.cb_b1)).setChecked(lockBandBean.isBand1());
        ((CheckBox) findViewById(R.id.cb_b1)).setOnCheckedChangeListener(this);

        ((CheckBox) findViewById(R.id.cb_b3)).setChecked(lockBandBean.isBand3());
        ((CheckBox) findViewById(R.id.cb_b3)).setOnCheckedChangeListener(this);

        ((CheckBox) findViewById(R.id.cb_b5)).setChecked(lockBandBean.isBand5());
        ((CheckBox) findViewById(R.id.cb_b5)).setOnCheckedChangeListener(this);

        ((CheckBox) findViewById(R.id.cb_b7)).setChecked(lockBandBean.isBand7());
        ((CheckBox) findViewById(R.id.cb_b7)).setOnCheckedChangeListener(this);

        ((CheckBox) findViewById(R.id.cb_b8)).setChecked(lockBandBean.isBand8());
        ((CheckBox) findViewById(R.id.cb_b8)).setOnCheckedChangeListener(this);

        ((CheckBox) findViewById(R.id.cb_b38)).setChecked(lockBandBean.isBand38());
        ((CheckBox) findViewById(R.id.cb_b38)).setOnCheckedChangeListener(this);

        ((CheckBox) findViewById(R.id.cb_b39)).setChecked(lockBandBean.isBand39());
        ((CheckBox) findViewById(R.id.cb_b39)).setOnCheckedChangeListener(this);

        ((CheckBox) findViewById(R.id.cb_b40)).setChecked(lockBandBean.isBand40());
        ((CheckBox) findViewById(R.id.cb_b40)).setOnCheckedChangeListener(this);

        ((CheckBox) findViewById(R.id.cb_b41)).setChecked(lockBandBean.isBand41());
        ((CheckBox) findViewById(R.id.cb_b41)).setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return://返回
                finish();
                break;
            case R.id.tv_save://保存
                if (check()) {
                    bandHex = 0;
                    save();
                } else {
                    Toast.makeText(this, "请选择band", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 锁定band
     */
    private void save() {
        for (int i = 0; i < bands.length; i++) {
            if (bands[i]) {
                LogUtils.I(TAG, "index = " + indexs[i]);
                bandHex += Math.pow(2, indexs[i] - 1);
            }
        }

        LockBandBean lockBand = new LockBandBean(bands[0], bands[1], bands[2], bands[3],
                bands[4], bands[5], bands[6], bands[7], bands[8]);
        spUtils.putObj(LOCK_BAND_KEY, lockBand);

        LogUtils.I(TAG, "10进制 = " + bandHex);
        LogUtils.I(TAG, "2进制 = " + NumberUtils.hexStringToBinary(String.format("%x", bandHex)));
        LogUtils.I(TAG, "16进制 = " + String.format("%x", bandHex));
        LogUtils.I(TAG, "16位16进制 = " + String.format("%016X", bandHex));
        /**锁定band*/
        ui_cmd_agent.lock_band(bandHex);

        finish();
    }

    /**
     * 检查是否选择band值
     */
    private boolean check() {
        boolean isSave = false;
        for (int i = 0; i < bands.length; i++) {
            if (bands[i]) {
                isSave = true;
                break;
            }
        }
        return isSave;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_b1:
                bands[0] = isChecked;
                break;
            case R.id.cb_b3:
                bands[1] = isChecked;
                break;
            case R.id.cb_b5:
                bands[2] = isChecked;
                break;
            case R.id.cb_b7:
                bands[3] = isChecked;
                break;
            case R.id.cb_b8:
                bands[4] = isChecked;
                break;
            case R.id.cb_b38:
                bands[5] = isChecked;
                break;
            case R.id.cb_b39:
                bands[6] = isChecked;
                break;
            case R.id.cb_b40:
                bands[7] = isChecked;
                break;
            case R.id.cb_b41:
                bands[8] = isChecked;
                break;
        }
    }
}
