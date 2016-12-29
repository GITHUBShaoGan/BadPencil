package com.slut.badpencil.password.edit.wifi;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.slut.badpencil.R;
import com.slut.badpencil.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiEditActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_title)
    TextInputLayout tilTitle;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.routerAddr)
    EditText routerAddr;
    @BindView(R.id.routerPassword)
    EditText routerPassword;
    @BindView(R.id.operatorAccount)
    EditText operatorsAccount;
    @BindView(R.id.operatorPassword)
    EditText operatorsPassword;
    @BindView(R.id.remark)
    EditText remark;

    public static final String EXTRA_SCAN_RESULT = "scan_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_edit);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_SCAN_RESULT)) {
                ScanResult scanResult = intent.getParcelableExtra(EXTRA_SCAN_RESULT);
                if (scanResult != null) {
                    name.setText(scanResult.SSID + "");
                }
            }
        }
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
