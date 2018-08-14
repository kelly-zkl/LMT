package com.wisec.scanner.ui.testset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisec.scanner.R;
import com.wisec.scanner.bean.AttachParam;
import com.wisec.scanner.utils.ConstUtils;
import com.wisec.scanner.utils.SPUtils;

public class AttachSetActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtSpace, mEtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach_set);

        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.attach);
        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);
        findViewById(R.id.layout_title).findViewById(R.id.tv_save).setOnClickListener(this);

        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mEtSpace = findViewById(R.id.et_space);
        mEtCount = findViewById(R.id.et_count);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return://返回
                finish();
                break;
            case R.id.tv_save://保存
                save();
                break;
        }
    }

    /**
     * 保存attach的参数
     */
    private void save() {
        if (TextUtils.isEmpty(mEtSpace.getText().toString().trim()) ||
                TextUtils.isEmpty(mEtCount.getText().toString().trim())) {
            Toast.makeText(this, "请输入间隔、执行次数", Toast.LENGTH_SHORT).show();
            return;
        }

        int space = Integer.parseInt(mEtSpace.getText().toString().trim());
        int time = space + 3;
        int count = Integer.parseInt(mEtCount.getText().toString().trim());

        AttachParam param = new AttachParam(time, space, count);

        SPUtils spUtils = new SPUtils(this, ConstUtils.SHARE_NAME);
        spUtils.putObj(ConstUtils.ATTACH_KEY, param);

        finish();
    }
}
