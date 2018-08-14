package com.wisec.scanner.ui.scanset;

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
import com.wisec.scanner.bean.LockPciBean;
import com.wisec.scanner.utils.SPUtils;
import com.wisec.scanner.utils.SystemManager;

import static com.wisec.scanner.utils.ConstUtils.LOCK_PCI_KEY;
import static com.wisec.scanner.utils.ConstUtils.SHARE_NAME;

public class LockPciActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText earfcnEt, pciEt;

    private SPUtils spUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pci);

        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.lock_pci);
        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);
        findViewById(R.id.layout_title).findViewById(R.id.tv_save).setOnClickListener(this);

        initData();
    }

    private void initData() {
        spUtils = new SPUtils(this, SHARE_NAME);
        LockPciBean lockPciBean = spUtils.getObj(LOCK_PCI_KEY, LockPciBean.class);

        earfcnEt = findViewById(R.id.et_earfcn);
        pciEt = findViewById(R.id.et_pci);

        if (null != lockPciBean) {
            earfcnEt.setText(lockPciBean.getEarfcn() + "");
            pciEt.setText(lockPciBean.getPci() + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return:
                finish();
                break;
            case R.id.tv_save://保存
                if (TextUtils.isEmpty(earfcnEt.getText().toString().trim())) {
                    Toast.makeText(this, "请输入频点号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pciEt.getText().toString().trim())) {
                    Toast.makeText(this, "请输入PCI", Toast.LENGTH_SHORT).show();
                    return;
                }
                showDialog();
                break;
            case R.id.btn_confirm://确认
                lockPCI();
                dialog.dismiss();
                break;
            case R.id.btn_cancel://取消
                dialog.dismiss();
                break;
        }
    }

    private void lockPCI() {
        if (TextUtils.isEmpty(earfcnEt.getText().toString().trim())) {
            Toast.makeText(this, "请输入频点号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pciEt.getText().toString().trim())) {
            Toast.makeText(this, "请输入PCI", Toast.LENGTH_SHORT).show();
            return;
        }
        int earfcn = Integer.parseInt(earfcnEt.getText().toString().trim());
        int pci = Integer.parseInt(pciEt.getText().toString().trim());

        LockPciBean lockPci = new LockPciBean(0, pci, earfcn);
        spUtils.putObj(LOCK_PCI_KEY, lockPci);

        SystemManager.lock_pci(earfcn, pci);

        Toast.makeText(this, "锁PCI号命令下发成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    private AlertDialog dialog;

    private void showDialog() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this).create();
            View dialogView = LayoutInflater.from(this)
                    .inflate(R.layout.dialog_confirm_layout, null);
            dialog.setView(dialogView);
            ((TextView) dialogView.findViewById(R.id.tv_dialog_content)).setText("保存后将重启手机，确认要锁PCI？");
            dialogView.findViewById(R.id.btn_cancel).setOnClickListener(this);
            dialogView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        }
        dialog.show();
    }
}
