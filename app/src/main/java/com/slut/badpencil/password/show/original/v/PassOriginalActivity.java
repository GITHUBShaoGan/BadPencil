package com.slut.badpencil.password.show.original.v;

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
import com.slut.badpencil.notification.observer.PasswordObserver;
import com.slut.badpencil.notification.subject.PasswordSubject;
import com.slut.badpencil.password.edit.original.v.PassEditActivity;
import com.slut.badpencil.password.show.original.p.PassOriginalPresenter;
import com.slut.badpencil.password.show.original.p.PassOriginalPresenterImpl;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;
import com.slut.badpencil.utils.SPUtils;
import com.slut.badpencil.utils.SystemUtils;
import com.slut.badpencil.utils.TimeUtils;
import com.slut.badpencil.utils.ToastUtils;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassOriginalActivity extends AppCompatActivity implements PassOriginalView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.password)
    TextView password;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.update)
    TextView update;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;

    private String uuid = null;
    public static final String EXTRA_UUID = "uuid";

    private PassOriginalPresenter presenter;

    private PasswordObserver passwordObserver = new PasswordObserver() {
        @Override
        public void itemChanged(Object obj) {
            super.itemChanged(obj);
            presenter.query(uuid);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PasswordSubject.getInstances().removeObserver(passwordObserver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_original);
        ButterKnife.bind(this);
        initView();
        initListener();
        PasswordSubject.getInstances().registerObserver(passwordObserver);
    }

    private void initView() {
        presenter = new PassOriginalPresenterImpl(this);

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

    @OnClick(R.id.copy_account)
    void copyAccount() {
        SystemUtils.copy(account.getText().toString());
    }

    @OnClick(R.id.copy_password)
    void copyPassword() {
        SystemUtils.copy(password.getText().toString());
    }

    @Override
    public void onQuerySuccess(Password password, List<PassLabel> passLabelList) {
        toolbar.setTitle(password.getTitle());
        account.setText(password.getAccount());
        this.password.setText(RSAUtils.decrypt(password.getPassword()));
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
        getMenuInflater().inflate(R.menu.pass_original, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(this, PassEditActivity.class);
                intent.putExtra(PassEditActivity.EXTRA_PASSWORD, uuid);
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
