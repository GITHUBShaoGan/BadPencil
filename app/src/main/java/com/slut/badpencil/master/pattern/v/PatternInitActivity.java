package com.slut.badpencil.master.pattern.v;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.slut.badpencil.R;
import com.slut.badpencil.main.MainActivity;
import com.slut.badpencil.master.pattern.p.PatternInitPresenter;
import com.slut.badpencil.master.pattern.p.PatternInitPresenterImpl;
import com.slut.badpencil.utils.ToastUtils;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

public class PatternInitActivity extends SetPatternActivity implements PatternInitView {

    private PatternInitPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        presenter.checkConfig();
    }

    private void initView() {
        presenter = new PatternInitPresenterImpl(this);
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        super.onSetPattern(pattern);
        String password = PatternUtils.patternToSha1String(pattern);
        presenter.create(password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pattern_init, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onCreateError(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void onConfigExists() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onConfigNotExists() {

    }
}
