package com.slut.badpencil.password.show.wifi.v;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.badpencil.R;
import com.slut.badpencil.config.AppConfig;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;
import com.slut.badpencil.notification.observer.PasswordObserver;
import com.slut.badpencil.notification.subject.PasswordSubject;
import com.slut.badpencil.password.edit.wifi.v.WifiEditActivity;
import com.slut.badpencil.password.show.wifi.p.PassWifiPresenter;
import com.slut.badpencil.password.show.wifi.p.PassWifiPresenterImpl;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;
import com.slut.badpencil.utils.SPUtils;
import com.slut.badpencil.utils.TimeUtils;
import com.slut.badpencil.utils.ToastUtils;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassWifiActivity extends AppCompatActivity implements PassWifiView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.password)
    TextView password;
    @BindView(R.id.routerAddr)
    TextView routerAddr;
    @BindView(R.id.routerPassword)
    TextView routerPassword;
    @BindView(R.id.operatorAccount)
    TextView operatorAccount;
    @BindView(R.id.operatorPassword)
    TextView operatorPassword;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;
    @BindView(R.id.update)
    TextView update;

    private PassWifiPresenter presenter;
    private String uuid = null;
    public static final String EXTRA_UUID = "uuid";

    private PasswordObserver passwordObserver = new PasswordObserver() {

        @Override
        public void itemChanged(Object obj) {
            super.itemChanged(obj);
            presenter.query(uuid);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_wifi);
        ButterKnife.bind(this);
        initView();
        initListener();
        PasswordSubject.getInstances().registerObserver(passwordObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PasswordSubject.getInstances().removeObserver(passwordObserver);
    }

    private void initView() {
        presenter = new PassWifiPresenterImpl(this);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_UUID)) {
                uuid = intent.getStringExtra(EXTRA_UUID);
                presenter.query(uuid);

            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onQuerySuccess(Password password, WifiPassword wifiPassword, List<PassLabel> passLabelList) {
        toolbar.setTitle(password.getTitle());
        name.setText(password.getAccount());
        this.password.setText(RSAUtils.decrypt(password.getPassword()));
        routerAddr.setText(RSAUtils.decrypt(wifiPassword.getRouterIP()));
        routerPassword.setText(RSAUtils.decrypt(wifiPassword.getRouterPassword()));
        operatorAccount.setText(RSAUtils.decrypt(wifiPassword.getOperatorAccount()));
        operatorPassword.setText(RSAUtils.decrypt(wifiPassword.getOperatorPassword()));
        remark.setText(password.getRemark());
        update.setText(ResUtils.getString(R.string.tv_update_time) + TimeUtils.calInterval(password.getUpdateStamp(), System.currentTimeMillis()));

        flowLayout.removeAllViews();
        for (PassLabel passLabel : passLabelList) {
            View view = LayoutInflater.from(this).inflate(R.layout.view_label, new LinearLayout(this), false);
            TextView textView = (TextView) view.findViewById(R.id.name);
            textView.setText(passLabel.getName() + "");
            flowLayout.addView(view);
        }
    }

    @Override
    public void onQueryError(String msg) {
        ToastUtils.showShort(msg);
        finish();
    }

    @Override
    public void onDeleteSuccess(String uuid) {
        PasswordSubject.getInstances().notifyItemRemoved(uuid);
        finish();
    }

    @Override
    public void onDeleteError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_wifi, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(this, WifiEditActivity.class);
                intent.putExtra(WifiEditActivity.EXTRA_UUID, uuid);
                startActivity(intent);
                break;
            case R.id.delete:
                delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void delete() {
        boolean isShow = (Boolean) SPUtils.get(AppConfig.SPConfig.FILE_NAME, AppConfig.SPConfig.SHOW_DIALOG_PASS_DELETE, true);
        if (isShow) {
            View childView = LayoutInflater.from(this).inflate(R.layout.view_dialog_text_with_cb, new LinearLayout(this), false);
            TextView textView = (TextView) childView.findViewById(R.id.msg);
            final CheckBox checkBox = (CheckBox) childView.findViewById(R.id.checkbox);
            textView.setText(R.string.msg_dialog_pass_delete_single);
            checkBox.setText(R.string.cb_never_show_again);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_warning_amber_24dp);
            builder.setTitle(R.string.title_dialog_tips);
            builder.setView(childView);
            builder.setPositiveButton(R.string.action_dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //sharepreference记录下下次是否显示状态
                    SPUtils.put(AppConfig.SPConfig.FILE_NAME, AppConfig.SPConfig.SHOW_DIALOG_PASS_DELETE, !checkBox.isChecked());
                    presenter.delete(uuid);
                }
            });
            builder.setNegativeButton(R.string.action_dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            presenter.delete(uuid);
        }
    }

}
