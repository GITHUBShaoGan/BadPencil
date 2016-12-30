package com.slut.badpencil.password.label.v;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slut.badpencil.R;
import com.slut.badpencil.config.AppConfig;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.password.edit.original.v.PassEditActivity;
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
    public static final String EXTRA_LABEL_LIST = "label_list";
    public static final String EXTRA_OUTPUT_LIST = "label";
    private ArrayList<PassLabel> passLabelArrayList = null;

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

        passLabelArrayList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_LABEL_LIST)) {
                passLabelArrayList = intent.getParcelableArrayListExtra(EXTRA_LABEL_LIST);
            }
        }

        labelPresenter = new LabelPresenterImpl(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new LabelAdapter();
        adapter.setPassLabelList(new ArrayList<PassLabel>());
        adapter.setIsCheckList(new ArrayList<Boolean>());
        recyclerView.setAdapter(adapter);

        labelPresenter.load(pageNO, AppConfig.PAGE_SIZE, passLabelArrayList);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWithExtra();
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
                labelPresenter.load(pageNO, AppConfig.PAGE_SIZE, passLabelArrayList);
            }
        });
    }

    @OnClick(R.id.create)
    void onCreateClick() {
        String n = name.getText().toString().trim();
        labelPresenter.create(n);
    }

    @Override
    public void onLoadSuccess(boolean isCompleted, List<PassLabel> passLabelList, List<Boolean> isCheckList) {
        adapter.getPassLabelList().addAll(passLabelList);
        adapter.getIsCheckList().addAll(isCheckList);
        adapter.notifyDataSetChanged();
        pageNO++;
    }

    @Override
    public void onLoadError(String msg) {

    }

    @Override
    public void onCreateSuccess(PassLabel passLabel) {
        adapter.getPassLabelList().add(0, passLabel);
        adapter.getIsCheckList().add(0, true);
        adapter.notifyItemInserted(0);
        name.setText("");
        tilName.setError("");
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onCreateExists() {
        tilName.setError(ResUtils.getString(R.string.error_label_name_exists));
    }

    @Override
    public void onCreateError(String msg) {
        tilName.setError(msg + "");
    }

    private void finishWithExtra() {
        ArrayList<PassLabel> passLabelArrayList = new ArrayList<>();
        if (adapter != null) {
            List<PassLabel> passLabels = adapter.getPassLabelList();
            List<Boolean> isCheckList = adapter.getIsCheckList();
            if (passLabels != null && isCheckList != null && passLabels.size() == isCheckList.size()) {
                for (int i = 0; i < passLabels.size(); i++) {
                    if (isCheckList.get(i)) {
                        passLabelArrayList.add(passLabels.get(i));
                    }
                }
            }
        }
        Intent intent = getIntent();
        if (intent != null) {
            intent.putParcelableArrayListExtra(EXTRA_OUTPUT_LIST, passLabelArrayList);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishWithExtra();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
