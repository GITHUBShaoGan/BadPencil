package com.slut.badpencil.password.show.website.v;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;
import com.slut.badpencil.password.show.website.p.PassWebPresenter;
import com.slut.badpencil.password.show.website.p.PassWebPresenterImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PassWebsiteActivity extends AppCompatActivity implements PassWebView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String primaryUUID = null;
    public static final String EXTRA_UUID = "uuid";

    private PassWebPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_website);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView(){
        presenter = new PassWebPresenterImpl(this);
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra(EXTRA_UUID)){
                primaryUUID = intent.getStringExtra(EXTRA_UUID);
                presenter.query(primaryUUID);
            }
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onQuerySuccess(Password password, WebsitePassword websitePassword, List<PassLabel> passLabelList) {
        toolbar.setTitle(password.getTitle());

    }

    @Override
    public void onQueryError(String msg) {

    }
}
