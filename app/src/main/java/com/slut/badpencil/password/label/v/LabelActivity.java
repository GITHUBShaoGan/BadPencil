package com.slut.badpencil.password.label.v;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slut.badpencil.R;
import com.slut.badpencil.config.AppConfig;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.password.label.adapter.LabelAdapter;
import com.slut.badpencil.password.label.p.LabelPresenter;
import com.slut.badpencil.password.label.p.LabelPresenterImpl;
import com.slut.badpencil.utils.ResUtils;
import com.slut.badpencil.widget.OnLoadScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LabelActivity extends AppCompatActivity implements LabelView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.til_name)
    TextInputLayout tilName;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.create)
    Button create;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private LabelAdapter adapter;

    private long pageNO = 1;

    private LabelPresenter labelPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        labelPresenter = new LabelPresenterImpl(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LabelAdapter();
        adapter.setPassLabelList(new ArrayList<PassLabel>());
        recyclerView.setAdapter(adapter);

        labelPresenter.load(pageNO, AppConfig.PAGE_SIZE);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString().trim())) {
                    create.setEnabled(false);
                    create.setClickable(false);
                    tilName.setError(ResUtils.getString(R.string.error_label_name_cannot_empty));
                } else {
                    create.setEnabled(true);
                    create.setClickable(true);
                    tilName.setError("");
                }
            }
        });
        recyclerView.addOnScrollListener(new OnLoadScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                labelPresenter.load(pageNO, AppConfig.PAGE_SIZE);
            }
        });
    }

    @OnClick(R.id.create)
    void onCreateClick() {
        String n = name.getText().toString().trim();
        labelPresenter.create(n);
    }

    @Override
    public void onLoadSuccess(boolean isCompleted, List<PassLabel> passLabelList) {
        adapter.getPassLabelList().addAll(passLabelList);
        adapter.notifyDataSetChanged();
        pageNO++;
    }

    @Override
    public void onLoadError(String msg) {

    }

    @Override
    public void onCreateSuccess(PassLabel passLabel) {
        adapter.getPassLabelList().add(0, passLabel);
        adapter.notifyItemInserted(0);
        name.setText("");
        tilName.setError("");
    }

    @Override
    public void onCreateExists() {
        tilName.setError(ResUtils.getString(R.string.error_label_name_exists));
    }

    @Override
    public void onCreateError(String msg) {
        tilName.setError(msg + "");
    }
}
