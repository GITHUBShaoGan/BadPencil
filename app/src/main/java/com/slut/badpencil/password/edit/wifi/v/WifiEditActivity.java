package com.slut.badpencil.password.edit.wifi.v;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.ScanResult;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.badpencil.R;
import com.slut.badpencil.config.AppConfig;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;
import com.slut.badpencil.notification.subject.PasswordSubject;
import com.slut.badpencil.password.edit.wifi.p.WifiEditPresenter;
import com.slut.badpencil.password.edit.wifi.p.WifiEditPresenterImpl;
import com.slut.badpencil.password.label.v.LabelActivity;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;
import com.slut.badpencil.utils.SPUtils;
import com.slut.badpencil.utils.ToastUtils;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.slut.badpencil.password.label.v.LabelActivity.EXTRA_OUTPUT_LIST;

public class WifiEditActivity extends AppCompatActivity implements WifiEditView {

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
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;

    private String primaryUUID = null;
    private WifiPassword primaryWifiPassword;
    private ArrayList<PassLabel> primaryLabels;
    private ArrayList<PassLabel> extraLabels;
    public static final String EXTRA_UUID = "uuid";
    public static final String EXTRA_SCAN_RESULT = "scan_result";

    private WifiEditPresenter wifiEditPresenter;
    private static final int REQUEST_SET_LABELS = 2032;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_edit);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        wifiEditPresenter = new WifiEditPresenterImpl(this);
        primaryLabels = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_SCAN_RESULT)) {
                ScanResult scanResult = intent.getParcelableExtra(EXTRA_SCAN_RESULT);
                if (scanResult != null) {
                    name.setText(scanResult.SSID + "");
                }
            }
            if (intent.hasExtra(EXTRA_UUID)) {
                primaryUUID = intent.getStringExtra(EXTRA_UUID);
                wifiEditPresenter.query(primaryUUID);
            }
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
                    tilTitle.setError("");
                }
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String t = title.getText().toString().trim();
                if (TextUtils.isEmpty(t)) {
                    tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
                } else {
                    tilTitle.setError("");
                }

                if (TextUtils.isEmpty(s.toString().trim())) {
                    tilPassword.setError(ResUtils.getString(R.string.error_password_cannot_empty));
                } else {
                    tilPassword.setError("");
                }
            }
        });
    }

    @Override
    public void onQuerySuccess(Password password, WifiPassword wifiPassword, List<PassLabel> passLabelList) {
        primaryLabels = new ArrayList<>(passLabelList);
        extraLabels = new ArrayList<>(passLabelList);
        primaryWifiPassword = wifiPassword.appendFullPass(password);

        flowLayout.removeAllViews();
        for (PassLabel passLabel : extraLabels) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_label, new LinearLayout(this), false);
            TextView textView = (TextView) view.findViewById(R.id.name);
            textView.setText(passLabel.getName() + "");
            flowLayout.addView(view);
        }

        title.setText(password.getTitle());
        name.setText(password.getAccount());
        this.password.setText(RSAUtils.decrypt(password.getPassword()));
        routerAddr.setText(RSAUtils.decrypt(wifiPassword.getRouterIP()));
        routerPassword.setText(RSAUtils.decrypt(wifiPassword.getRouterPassword()));
        operatorsAccount.setText(RSAUtils.decrypt(wifiPassword.getOperatorAccount()));
        operatorsPassword.setText(RSAUtils.decrypt(wifiPassword.getOperatorPassword()));
        remark.setText(password.getRemark());
    }

    @Override
    public void onQueryError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onUIChange() {
        if (primaryWifiPassword == null) {
            //新建
            insert();
        } else {
            //更新
            update();
        }
    }

    @Override
    public void onUINotChange() {
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
            builder.setTitle(R.string.title_dialog_tips);
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
    public void onInsertEmptyTitle() {
        tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
    }

    @Override
    public void onInsertEmptyPassword() {
        tilPassword.setError(ResUtils.getString(R.string.error_password_cannot_empty));
    }

    @Override
    public void onInsertSuccess(String uuid) {
        ToastUtils.showShort(R.string.success_pass_create);
        PasswordSubject.getInstances().notifyItemInserted(uuid);
        Intent intent = getIntent();
        if (intent != null) {
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public void onInsertError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onUpdateEmptyTitle() {
        tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
    }

    @Override
    public void onUpdateEmptyPassword() {
        tilPassword.setError(ResUtils.getString(R.string.error_password_cannot_empty));
    }

    @Override
    public void onUpdateSuccess(String uuid) {
        PasswordSubject.getInstances().notifyItemChanged(uuid);
        finish();
    }

    @Override
    public void onUpdateError(String msg) {
        ToastUtils.showShort(msg);
    }

    private void checkUI() {
        String t = title.getText().toString().trim();
        String n = name.getText().toString().trim();
        String p = password.getText().toString().trim();
        String ra = routerAddr.getText().toString().trim();
        String rp = routerPassword.getText().toString().trim();
        String oa = operatorsAccount.getText().toString().trim();
        String op = operatorsPassword.getText().toString().trim();
        String r = remark.getText().toString().trim();

        wifiEditPresenter.checkUI(primaryWifiPassword, t, n, p, ra, rp, oa, op, r, primaryLabels, extraLabels);
    }

    private void insert() {
        String t = title.getText().toString().trim();
        String n = name.getText().toString().trim();
        String p = password.getText().toString().trim();
        String ra = routerAddr.getText().toString().trim();
        String rp = routerPassword.getText().toString().trim();
        String oa = operatorsAccount.getText().toString().trim();
        String op = operatorsPassword.getText().toString().trim();
        String r = remark.getText().toString().trim();

        wifiEditPresenter.insert(t, n, p, ra, rp, oa, op, r, extraLabels);
    }

    private void update() {
        String t = title.getText().toString().trim();
        String n = name.getText().toString().trim();
        String p = password.getText().toString().trim();
        String ra = routerAddr.getText().toString().trim();
        String rp = routerPassword.getText().toString().trim();
        String oa = operatorsAccount.getText().toString().trim();
        String op = operatorsPassword.getText().toString().trim();
        String r = remark.getText().toString().trim();

        wifiEditPresenter.update(primaryWifiPassword.getUuid(), t, n, p, ra, rp, oa, op, r, extraLabels);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wifi_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.label:
                Intent intent = new Intent(this, LabelActivity.class);
                intent.putExtra(LabelActivity.EXTRA_LABEL_LIST, extraLabels);
                startActivityForResult(intent, REQUEST_SET_LABELS);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SET_LABELS:
                    if (data != null) {
                        if (data.hasExtra(EXTRA_OUTPUT_LIST)) {
                            extraLabels = data.getParcelableArrayListExtra(EXTRA_OUTPUT_LIST);
                            if (extraLabels != null) {
                                flowLayout.removeAllViews();
                                for (PassLabel passLabel : extraLabels) {
                                    View view = LayoutInflater.from(this).inflate(R.layout.view_label, new LinearLayout(this), false);
                                    TextView textView = (TextView) view.findViewById(R.id.name);
                                    textView.setText(passLabel.getName() + "");
                                    flowLayout.addView(view);
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }
}
