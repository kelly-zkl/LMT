package com.wisec.scanner.ui.scanset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.LockNetBean;
import com.wisec.scanner.utils.SPUtils;

import diag.ui_cmd_agent;

import static com.wisec.scanner.utils.ConstUtils.LOCK_NET_KEY;
import static com.wisec.scanner.utils.ConstUtils.SHARE_NAME;
import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_DEFAULT_NETWORK;
import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_GSM_ONLY;
import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_LTE_ONLY;
import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_WCDMA_ONLY;

public class LockNetActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private SPUtils spUtils;
    private int net = 0, index = 0;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_net);

        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.lock_net);
        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);
        findViewById(R.id.layout_title).findViewById(R.id.tv_save).setOnClickListener(this);

        initData();

    }

    private void initData() {
        spUtils = new SPUtils(this, SHARE_NAME);
        LockNetBean lockNetBean = spUtils.getObj(LOCK_NET_KEY, LockNetBean.class);

        ((RadioGroup) findViewById(R.id.rg_net)).setOnCheckedChangeListener(this);

        if (null != lockNetBean) {
            ((RadioButton) ((RadioGroup) findViewById(R.id.rg_net)).getChildAt(lockNetBean.getIndex())).setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return://返回
                finish();
                break;
            case R.id.tv_save://保存
                lockNet();
                break;
        }
    }

    private void lockNet() {
        if (net == 0) {
            Toast.makeText(this, "请选择网络", Toast.LENGTH_SHORT).show();
            return;
        }

        LockNetBean lockNet = new LockNetBean(net, name, index);
        spUtils.putObj(LOCK_NET_KEY, lockNet);

        ui_cmd_agent.set_preferred_network(net);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbtn_auto://自动
                net = NETWORK_MODE_DEFAULT_NETWORK;
                index = 0;
                name = "--";
                break;
            case R.id.rbtn_4g://4g
                net = NETWORK_MODE_LTE_ONLY;
                index = 1;
                name = "4G";
                break;
            case R.id.rbtn_3g://3g
                net = NETWORK_MODE_WCDMA_ONLY;
                index = 2;
                name = "3G";
                break;
            case R.id.rbtn_2g://2g
                net = NETWORK_MODE_GSM_ONLY;
                index = 3;
                name = "2G";
                break;
        }
    }
}
