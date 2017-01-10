package com.slut.badpencil.password.edit.server.v;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.badpencil.R;
import com.slut.badpencil.config.AppConfig;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.notification.subject.PasswordSubject;
import com.slut.badpencil.password.edit.server.p.ServerEditPresenter;
import com.slut.badpencil.password.edit.server.p.ServerEditPresenterImpl;
import com.slut.badpencil.password.label.v.LabelActivity;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;
import com.slut.badpencil.utils.SPUtils;
import com.slut.badpencil.utils.StringUtils;
import com.slut.badpencil.utils.ToastUtils;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.slut.badpencil.password.label.v.LabelActivity.EXTRA_OUTPUT_LIST;

public class ServerEditActivity extends AppCompatActivity implements ServerEditView {

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
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;

    private String primaryUUID = null;
    private ServerPassword primaryServerPassword = null;
    private ArrayList<PassLabel> primaryPassLabelList;
    private ArrayList<PassLabel> extraPassLabelList;

    private ServerEditPresenter presenter;

    public static final String EXTRA_PASSWORD = "uuid";
    private static final int REQUEST_SET_LABELS = 1034;

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

        presenter = new ServerEditPresenterImpl(this);
        primaryPassLabelList = new ArrayList<>();
        extraPassLabelList = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_PASSWORD)) {
                primaryUUID = intent.getStringExtra(EXTRA_PASSWORD);
                presenter.queryLabel(primaryUUID);
            }
        }
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

                if (TextUtils.isEmpty(editable.toString().trim())) {
                    tilPort.setError(ResUtils.getString(R.string.error_server_port_illegal));
                } else {
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

                if (TextUtils.isEmpty(p)) {
                    tilPort.setError(ResUtils.getString(R.string.error_server_port_illegal));
                } else {
                    tilPort.setError("");
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

                if (TextUtils.isEmpty(p)) {
                    tilPort.setError(ResUtils.getString(R.string.error_server_port_illegal));
                } else {
                    tilPort.setError("");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.server_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.label:
                Intent intent = new Intent(this, LabelActivity.class);
                intent.putExtra(LabelActivity.EXTRA_LABEL_LIST, extraPassLabelList);
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
                            extraPassLabelList = data.getParcelableArrayListExtra(EXTRA_OUTPUT_LIST);
                            if (extraPassLabelList != null) {
                                flowLayout.removeAllViews();
                                for (PassLabel passLabel : extraPassLabelList) {
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

    @Override
    public void onQuerySuccess(Password password, ServerPassword serverPassword, List<PassLabel> passLabelList) {
        flowLayout.removeAllViews();
        primaryPassLabelList = new ArrayList<>(passLabelList);
        primaryServerPassword = serverPassword.appendFullPass(password);
        extraPassLabelList = new ArrayList<>(passLabelList);
        for (PassLabel passLabel : primaryPassLabelList) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_label, new LinearLayout(this), false);
            TextView textView = (TextView) view.findViewById(R.id.name);
            textView.setText(passLabel.getName() + "");
            flowLayout.addView(view);
        }

        title.setText(password.getTitle());
        address.setText(serverPassword.getAddress());
        port.setText(serverPassword.getPort()+"");
        account.setText(password.getAccount());
        this.password.setText(RSAUtils.decrypt(password.getPassword()));
        remark.setText(password.getRemark());
    }

    @Override
    public void onQueryError(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * 用户改变了界面
     */
    @Override
    public void onUIChange() {
        if(primaryServerPassword == null){
            //insert mode
            insert();
        }else{
            //update mode
            update();
        }
    }

    /**
     * 用户未改变界面
     */
    @Override
    public void onUINotChange() {
        finish();
    }

    /**
     * 用户输入无效
     */
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
    public void onInsertSuccess(ServerPassword serverPassword) {
        PasswordSubject.getInstances().notifyItemInserted(serverPassword.getPassUUID());
        ToastUtils.showShort(R.string.success_pass_create);
        Intent intent = getIntent();
        if(intent!=null){
            setResult(RESULT_OK,intent);
        }
        finish();
    }

    @Override
    public void onInsertEmptyTitle() {
        tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
    }

    @Override
    public void onInsertIllegalIP() {
        tilAddress.setError(ResUtils.getString(R.string.error_server_address_illegal));
    }

    @Override
    public void onInsertIllegalPort() {
        tilPort.setError(ResUtils.getString(R.string.error_server_port_illegal));
    }

    @Override
    public void onInsertEmptyAccount() {
        tilAccount.setError(ResUtils.getString(R.string.error_account_cannot_empty));
    }

    @Override
    public void onInsertEmptyPassword() {
        tilPassword.setError(ResUtils.getString(R.string.error_password_cannot_empty));
    }

    @Override
    public void onInsertError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onUpdateSuccess(String uuid) {
        PasswordSubject.getInstances().notifyItemChanged(uuid);
        ToastUtils.showShort(R.string.success_pass_update);
        finish();
    }

    @Override
    public void onUpdateEmptyTitle() {
        tilTitle.setError(ResUtils.getString(R.string.error_title_cannot_empty));
    }

    @Override
    public void onUpdateIllegalIP() {
        tilAddress.setError(ResUtils.getString(R.string.error_server_address_illegal));
    }

    @Override
    public void onUpdateIllegalPort() {
        tilPort.setError(ResUtils.getString(R.string.error_server_port_illegal));
    }

    @Override
    public void onUpdateEmptyAccount() {
        tilAccount.setError(ResUtils.getString(R.string.error_account_cannot_empty));
    }

    @Override
    public void onUpdateEmptyPassword() {
        tilPassword.setError(ResUtils.getString(R.string.error_password_cannot_empty));
    }

    @Override
    public void onUpdateError(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * 检查ui是否被修改
     */
    private void checkUI() {
        String t = title.getText().toString().trim();
        String ip = address.getText().toString().trim();
        String p = port.getText().toString().trim();
        String a = account.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String r = remark.getText().toString().trim();

        presenter.checkUI(primaryServerPassword,t,ip,p,a,pass,r,primaryPassLabelList,extraPassLabelList);
    }

    /**
     * 插入新数据
     */
    private void insert(){
        String t = title.getText().toString().trim();
        String ip = address.getText().toString().trim();
        String p = port.getText().toString().trim();
        String a = account.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String r = remark.getText().toString().trim();

        presenter.insert(t,ip,p,a,pass,r,extraPassLabelList);
    }

    /**
     * 更新密码
     */
    private void update(){
        String t = title.getText().toString().trim();
        String ip = address.getText().toString().trim();
        String p = port.getText().toString().trim();
        String a = account.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String r = remark.getText().toString().trim();

        presenter.update(primaryServerPassword,t,ip,p,a,pass,r,extraPassLabelList);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            checkUI();
        }
        return super.onKeyDown(keyCode, event);
    }
}
