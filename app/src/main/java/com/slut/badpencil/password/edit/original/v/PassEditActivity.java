package com.slut.badpencil.password.edit.original.v;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.badpencil.R;
import com.slut.badpencil.config.AppConfig;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.password.edit.original.p.PassEditPresenter;
import com.slut.badpencil.password.edit.original.p.PassEditPresenterImpl;
import com.slut.badpencil.password.label.v.LabelActivity;
import com.slut.badpencil.utils.ResUtils;
import com.slut.badpencil.utils.SPUtils;
import com.slut.badpencil.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassEditActivity extends AppCompatActivity implements PassEditView {

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

    private Password primaryPassword = null;
    private ArrayList<PassLabel> primaryArrayLabelList = null;
    private PassEditPresenter presenter;

    public static final String EXTRA_PASSWORD = "password";
    private static final int REQUEST_SET_LABELS = 1020;

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

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_PASSWORD)) {
                primaryPassword = intent.getParcelableExtra(EXTRA_PASSWORD);
            }
        }

        presenter = new PassEditPresenterImpl(this);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUI();
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
                if (TextUtils.isEmpty(editable.toString().trim())) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
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
                if (TextUtils.isEmpty(t)) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
                    tilTitle.setError("");
                }

                if (TextUtils.isEmpty(editable.toString().trim())) {
                    tilAccount.setError(ResUtils.getString(R.string.error_account_cannot_empty));
                } else {
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

                if (TextUtils.isEmpty(t)) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
                    tilTitle.setError("");
                }

                if (TextUtils.isEmpty(a)) {
                    tilAccount.setError(ResUtils.getString(R.string.error_account_cannot_empty));
                } else {
                    tilAccount.setError("");
                }


                if (TextUtils.isEmpty(editable.toString().trim())) {
                    tilPassword.setError(ResUtils.getString(R.string.error_password_cannot_empty));
                } else {
                    tilPassword.setError("");
                }
            }
        });
    }


    /**
     * 开始插入数据
     */
    private void save() {
        String t = title.getText().toString().trim();
        String a = account.getText().toString().trim();
        String p = password.getText().toString().trim();
        String r = remark.getText().toString().trim();

        presenter.create(t, a, p, r);
    }

    private void checkUI() {
        String t = title.getText().toString().trim();
        String a = account.getText().toString().trim();
        String p = password.getText().toString().trim();
        String r = remark.getText().toString().trim();

        presenter.checkUI(primaryPassword, t, a, p, r);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            checkUI();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onUIChange() {
        //用户编辑了内容
        if (primaryPassword == null) {
            //新建模式
            save();
        } else {
            //更新模式
        }
    }

    @Override
    public void onUINotChange() {
        //用户未编辑内容
        finish();
    }

    @Override
    public void onInputInvalid() {
        //用户输入无效
        boolean isShowAllow = true;
        if (SPUtils.contains(AppConfig.SPConfig.FILE_NAME, AppConfig.SPConfig.SHOW_DIALOG_PASSEDIT_EMPTY)) {
            isShowAllow = (Boolean) SPUtils.get(AppConfig.SPConfig.FILE_NAME, AppConfig.SPConfig.SHOW_DIALOG_PASSEDIT_EMPTY, true);
        }
        if (isShowAllow) {
            View childView = LayoutInflater.from(this).inflate(R.layout.view_dialog_text_with_cb, new LinearLayout(this), false);
            TextView textView = (TextView) childView.findViewById(R.id.msg);
            final CheckBox checkBox = (CheckBox) childView.findViewById(R.id.checkbox);
            textView.setText(R.string.msg_dialog_pass_edit_empty);
            checkBox.setText(R.string.cb_never_show_again);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_warning_amber_24dp);
            builder.setTitle(R.string.title_dialog_pass_edit_empty);
            builder.setView(childView);
            builder.setPositiveButton(R.string.action_dialog_giveup_edit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //sharepreference记录下下次是否显示状态
                    SPUtils.put(AppConfig.SPConfig.FILE_NAME, AppConfig.SPConfig.SHOW_DIALOG_PASSEDIT_EMPTY, !checkBox.isChecked());
                    finish();
                }
            });
            builder.setNegativeButton(R.string.action_dialog_return_to_edit, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            finish();
        }
    }

    @Override
    public void onCreateSuccess(Password password) {
        ToastUtils.showShort(R.string.success_pass_create_success);
        finish();
    }

    @Override
    public void onCreateEmptyTitle() {
        tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
    }

    @Override
    public void onCreateEmptyAccount() {
        tilAccount.setError(ResUtils.getString(R.string.error_account_cannot_empty));
    }

    @Override
    public void onCreateEmptyPassword() {
        tilPassword.setError(ResUtils.getString(R.string.error_password_cannot_empty));
    }

    @Override
    public void onCreateError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.label:
                Intent intent = new Intent(this, LabelActivity.class);
                startActivityForResult(intent, REQUEST_SET_LABELS);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
