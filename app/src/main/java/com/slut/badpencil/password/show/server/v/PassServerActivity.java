package com.slut.badpencil.password.show.server.v;

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
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.notification.observer.PasswordObserver;
import com.slut.badpencil.notification.subject.PasswordSubject;
import com.slut.badpencil.password.edit.server.v.ServerEditActivity;
import com.slut.badpencil.password.show.server.p.PassServerPresenter;
import com.slut.badpencil.password.show.server.p.PassServerPresenterImpl;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.SPUtils;
import com.slut.badpencil.utils.TimeUtils;
import com.slut.badpencil.utils.ToastUtils;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassServerActivity extends AppCompatActivity implements PassServerView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ip)
    TextView ip;
    @BindView(R.id.port)
    TextView port;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.password)
    TextView pass;
    @BindView(R.id.remark)
    TextView remark;
    @BindView(R.id.update)
    TextView update;
    @BindView(R.id.create)
    TextView create;
    @BindView(R.id.flowLayout)
    FlowLayout flowLayout;

    private String uuid;
    private PassServerPresenter presenter;

    public static final String EXTRA_PASSWORD = "password";

    private PasswordObserver passwordObserver = new PasswordObserver() {
        @Override
        public void itemInserted(Object obj) {
            super.itemInserted(obj);
        }

        @Override
        public void itemChanged(Object obj) {
            super.itemChanged(obj);
            presenter.query(obj.toString());
        }

        @Override
        public void itemRemoved(Object obj) {
            super.itemRemoved(obj);
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
        setContentView(R.layout.activity_pass_server);
        ButterKnife.bind(this);
        initView();
        initListener();
        PasswordSubject.getInstances().registerObserver(passwordObserver);
    }

    private void initView() {
        presenter = new PassServerPresenterImpl(this);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_PASSWORD)) {
                uuid = intent.getStringExtra(EXTRA_PASSWORD);
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
    public void onQuerySuccess(Password password, ServerPassword serverPassword, List<PassLabel> passLabelList) {
        toolbar.setTitle(password.getTitle());
        ip.setText(serverPassword.getAddress() + "");
        port.setText(serverPassword.getPort() + "");
        account.setText(password.getAccount());
        pass.setText(RSAUtils.decrypt(password.getPassword()));
        remark.setText(password.getRemark());
        create.setText(TimeUtils.calInterval(password.getCreateStamp(), System.currentTimeMillis()));
        update.setText(TimeUtils.calInterval(password.getUpdateStamp(), System.currentTimeMillis()));

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
    public void onDeleteError(String uuid) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pass_server, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(this, ServerEditActivity.class);
                intent.putExtra(ServerEditActivity.EXTRA_PASSWORD, uuid);
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
