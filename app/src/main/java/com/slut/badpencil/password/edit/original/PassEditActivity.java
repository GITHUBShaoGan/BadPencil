package com.slut.badpencil.password.edit.original;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.slut.badpencil.R;
import com.slut.badpencil.utils.ResUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassEditActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_title)
    TextInputLayout tilTitle;
    @BindView(R.id.til_account)
    TextInputLayout tilAccount;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.account)
    AutoCompleteTextView account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.remark)
    EditText remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_edit);
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
                if(TextUtils.isEmpty(editable.toString().trim())){
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                }else{
                    tilTitle.setError("");
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
                if(TextUtils.isEmpty(t)){
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                }else{
                    tilTitle.setError("");
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
                String a = account.getText().toString().trim();

                if(TextUtils.isEmpty(t)){
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                }else{
                    tilTitle.setError("");
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
