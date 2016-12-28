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
import com.slut.badpencil.password.edit.original.PassEditActivity;
import com.slut.badpencil.utils.ResUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditModeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private EditModeAdapter adapter;

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
        int[] iconArr = {R.drawable.ic_website_blue_48, R.drawable.ic_bank_golden_48, R.drawable.ic_server_black_48};
        adapter = new EditModeAdapter(titleArr, iconArr);
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
                startActivity(new Intent(this, PassEditActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
