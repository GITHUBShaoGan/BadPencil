package com.slut.badpencil.password.edit.wifi.list;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.slut.badpencil.R;
import com.slut.badpencil.password.edit.wifi.WifiEditActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiListActivity extends AppCompatActivity implements WifiListAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private WifiManager wifiManager;
    private WifiListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        adapter = new WifiListAdapter(wifiManager);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    updateHandler.sendEmptyMessage(0x1234);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<ScanResult> scanResults = wifiManager.getScanResults();
            adapter.setScanResultList(scanResults);
            adapter.notifyDataSetChanged();
        }
    };

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
        getMenuInflater().inflate(R.menu.wifi_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.skip:
                Intent intent = new Intent(this, WifiEditActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, WifiEditActivity.class);
        intent.putExtra(WifiEditActivity.EXTRA_SCAN_RESULT, adapter.getScanResultList().get(position));
        startActivity(intent);
    }
}
