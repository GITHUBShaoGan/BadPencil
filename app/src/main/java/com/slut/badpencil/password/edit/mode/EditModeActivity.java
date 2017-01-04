package com.slut.badpencil.password.edit.mode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.slut.badpencil.R;
import com.slut.badpencil.password.edit.original.v.PassEditActivity;
import com.slut.badpencil.password.edit.server.v.ServerEditActivity;
import com.slut.badpencil.password.edit.website.v.WebsiteEditActivity;
import com.slut.badpencil.password.edit.wifi.list.WifiListActivity;
import com.slut.badpencil.utils.ResUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditModeActivity extends AppCompatActivity implements EditModeAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private EditModeAdapter adapter;
    private static final int REQUEST_CREATE_PASSWORD = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] titleArr = ResUtils.getStringArray(R.array.mode_password);
        int[] iconArr = {R.drawable.ic_website_blue_48, R.drawable.ic_bank_golden_48, R.drawable.ic_server_black_48, R.drawable.ic_wifi_green_24};
        adapter = new EditModeAdapter(titleArr, iconArr);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.password_edit_mode, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip:
                Intent intent = new Intent(this, PassEditActivity.class);
                startActivityForResult(intent, REQUEST_CREATE_PASSWORD);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                Intent intent2Website = new Intent(this, WebsiteEditActivity.class);
                startActivityForResult(intent2Website, REQUEST_CREATE_PASSWORD);
                break;
            case 2:
                Intent intent2Server = new Intent(this, ServerEditActivity.class);
                startActivityForResult(intent2Server, REQUEST_CREATE_PASSWORD);
                break;
            case 3:
                Intent intent2Wifi = new Intent(this, WifiListActivity.class);
                startActivityForResult(intent2Wifi, REQUEST_CREATE_PASSWORD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            finish();
        }
    }
}
