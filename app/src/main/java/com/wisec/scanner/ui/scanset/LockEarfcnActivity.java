package com.wisec.scanner.ui.scanset;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.LockEarfcnBean;
import com.wisec.scanner.utils.SPUtils;
import com.wisec.scanner.utils.SystemManager;

import static com.wisec.scanner.utils.ConstUtils.LOCK_EARFCN_KEY;
import static com.wisec.scanner.utils.ConstUtils.SHARE_NAME;

public class LockEarfcnActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText earfcnEt;

    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_earfcn);

        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.lock_earfcn);
        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);
        findViewById(R.id.layout_title).findViewById(R.id.tv_save).setOnClickListener(this);

        initData();
    }

    private void initData() {
        spUtils = new SPUtils(this, SHARE_NAME);
        LockEarfcnBean lockEarfcnBean = spUtils.getObj(LOCK_EARFCN_KEY, LockEarfcnBean.class);

        earfcnEt = findViewById(R.id.et_earfcn);

        if (null != lockEarfcnBean) {
            earfcnEt.setText(lockEarfcnBean.getEarfcn() + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return://返回
                finish();
                break;
            case R.id.tv_save://保存
                if (TextUtils.isEmpty(earfcnEt.getText().toString().trim())) {
                    Toast.makeText(this, "请输入频点号", Toast.LENGTH_SHORT).show();
                    return;
                }
                showDialog();
                break;
            case R.id.btn_confirm://确认
                lockEarfcn();
                dialog.dismiss();
                break;
            case R.id.btn_cancel://取消
                dialog.dismiss();
                break;
        }
    }

    //锁频点
    @SuppressLint("ShowToast")
    private void lockEarfcn() {
        if (TextUtils.isEmpty(earfcnEt.getText().toString().trim())) {
            Toast.makeText(this, "请输入频点号", Toast.LENGTH_SHORT).show();
            return;
        }
        int earfcn = Integer.parseInt(earfcnEt.getText().toString().trim());

        LockEarfcnBean lockEarfcn = new LockEarfcnBean(0, earfcn);
        spUtils.putObj(LOCK_EARFCN_KEY, lockEarfcn);

        SystemManager.lock_earfcn(earfcn);

        Toast.makeText(this, "锁频点号命令下发成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    private AlertDialog dialog;

    private void showDialog() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).create();
            View dialogView = LayoutInflater.from(this)
                    .inflate(R.layout.dialog_confirm_layout, null);
            dialog.setView(dialogView);
            ((TextView) dialogView.findViewById(R.id.tv_dialog_content)).setText("保存后将重启手机，确认要锁频点？");
            dialogView.findViewById(R.id.btn_cancel).setOnClickListener(this);
            dialogView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        }
        dialog.show();
    }
}
