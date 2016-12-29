package com.slut.badpencil.password.edit.server;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.slut.badpencil.R;
import com.slut.badpencil.utils.ResUtils;
import com.slut.badpencil.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServerEditActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_title)
    TextInputLayout tilTitle;
    @BindView(R.id.til_address)
    TextInputLayout tilAddress;
    @BindView(R.id.til_port)
    TextInputLayout tilPort;
    @BindView(R.id.til_account)
    TextInputLayout tilAccount;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.port)
    EditText port;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.remark)
    EditText remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_edit);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString().trim())) {
                    tilTitle.setError("");
                } else {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                }
            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String t = title.getText().toString().trim();
                if (TextUtils.isEmpty(t)) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
                    tilTitle.setError("");
                }

                if (!StringUtils.isIPAddress(editable.toString().trim())) {
                    tilAddress.setError(ResUtils.getString(R.string.error_server_address_illegal));
                } else {
                    tilAddress.setError("");
                }
            }
        });
        port.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String t = title.getText().toString().trim();
                String addr = address.getText().toString().trim();
                if (TextUtils.isEmpty(t)) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
                    tilTitle.setError("");
                }

                if (!StringUtils.isIPAddress(addr)) {
                    tilAddress.setError(ResUtils.getString(R.string.error_server_address_illegal));
                } else {
                    tilAddress.setError("");
                }

                if(TextUtils.isEmpty(editable.toString().trim())){
                    tilPort.setError(ResUtils.getString(R.string.error_server_port_illegal));
                }else{
                    tilPort.setError("");
                }
            }
        });
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String t = title.getText().toString().trim();
                String addr = address.getText().toString().trim();
                String p = port.getText().toString().trim();
                if (TextUtils.isEmpty(t)) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
                    tilTitle.setError("");
                }

                if (!StringUtils.isIPAddress(addr)) {
                    tilAddress.setError(ResUtils.getString(R.string.error_server_address_illegal));
                } else {
                    tilAddress.setError("");
                }

                if(TextUtils.isEmpty(p)){
                    tilPort.setError(ResUtils.getString(R.string.error_server_port_illegal));
                }else{
                    tilPort.setError("");
                }

                if(TextUtils.isEmpty(editable.toString().trim())){
                    tilAccount.setError(ResUtils.getString(R.string.error_account_cannot_empty));
                }else{
                    tilAccount.setError("");
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String t = title.getText().toString().trim();
                String addr = address.getText().toString().trim();
                String p = port.getText().toString().trim();
                String a = account.getText().toString().trim();
                if (TextUtils.isEmpty(t)) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
                    tilTitle.setError("");
                }

                if (!StringUtils.isIPAddress(addr)) {
                    tilAddress.setError(ResUtils.getString(R.string.error_server_address_illegal));
                } else {
                    tilAddress.setError("");
                }

                if(TextUtils.isEmpty(p)){
                    tilPort.setError(ResUtils.getString(R.string.error_server_port_illegal));
                }else{
                    tilPort.setError("");
                }

                if(TextUtils.isEmpty(a)){
                    tilAccount.setError(ResUtils.getString(R.string.error_account_cannot_empty));
                }else{
                    tilAccount.setError("");
                }

                if(TextUtils.isEmpty(editable.toString().trim())){
                    tilPassword.setError(ResUtils.getString(R.string.error_password_cannot_empty));
                }else{
                    tilPassword.setError("");
                }
            }
        });
    }
}
