package com.wisec.scanner.ui.testset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wisec.scanner.R;

public class PingSetActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_set);

        ((TextView) findViewById(R.id.layout_title).findViewById(R.id.tv_title)).setText(R.string.ping);
        findViewById(R.id.layout_title).findViewById(R.id.tv_return).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_return:
                finish();
                break;
        }
    }
}
