package com.slut.badpencil.password.show.server.v;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.password.show.server.p.PassServerPresenter;
import com.slut.badpencil.password.show.server.p.PassServerPresenterImpl;
import com.slut.badpencil.rsa.RSAUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_server);
        ButterKnife.bind(this);
        initView();
        initListener();
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
        ip.setText(serverPassword.getAddress()+"");
        port.setText(serverPassword.getPort()+"");
        account.setText(password.getAccount());
        pass.setText(RSAUtils.decrypt(password.getPassword()));
        remark.setText(password.getRemark());
        create.setText(TimeUtils.calInterval(password.getCreateStamp(),System.currentTimeMillis()));
        update.setText(TimeUtils.calInterval(password.getUpdateStamp(),System.currentTimeMillis()));

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
}
