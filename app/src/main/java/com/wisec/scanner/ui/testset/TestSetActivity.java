package com.wisec.scanner.ui.testset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wisec.scanner.R;
import com.wisec.scanner.ui.logset.LogImportActivity;
import com.wisec.scanner.ui.scanset.ScanSetActivity;

public class TestSetActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_set);

        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.test_set);
        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);
        findViewById(R.id.rl_attach).setOnClickListener(this);
        findViewById(R.id.rl_ping).setOnClickListener(this);
        findViewById(R.id.rl_answer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return:
                finish();
                break;
            case R.id.rl_attach:
                startActivity(new Intent(TestSetActivity.this, AttachSetActivity.class));
                break;
            case R.id.rl_ping:
                startActivity(new Intent(TestSetActivity.this, PingSetActivity.class));
                break;
            case R.id.rl_answer:
                startActivity(new Intent(TestSetActivity.this, AnswerSetActivity.class));
                break;
        }
    }
}
