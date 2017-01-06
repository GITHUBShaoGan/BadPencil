package com.slut.badpencil.main.fragment.v;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slut.badpencil.R;
import com.slut.badpencil.config.AppConfig;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.main.fragment.adapter.PassAdapter;
import com.slut.badpencil.main.fragment.p.PassPresenter;
import com.slut.badpencil.main.fragment.p.PassPresenterImpl;
import com.slut.badpencil.password.show.server.v.PassServerActivity;
import com.slut.badpencil.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PassView, PassAdapter.OnItemClickListener {

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private View rootView = null;
    private static volatile PassFragment instances = null;

    private PassAdapter adapter;
    private LinearLayoutManager layoutManager;
    private PassPresenter presenter;

    private long pageNo = 1;//分页加载页码

    public static PassFragment getInstances() {
        if (instances == null) {
            synchronized (PassFragment.class) {
                if (instances == null) {
                    instances = new PassFragment();
                }
            }
        }
        return instances;
    }

    public PassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_pass, container, false);
        }
        ButterKnife.bind(this, rootView);
        initView();
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        presenter = new PassPresenterImpl(this);
        refreshLayout.setColorSchemeResources(R.color.colorGoogleRed, R.color.colorGoogleGreen);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PassAdapter();
        adapter.setPasswordList(new ArrayList<Password>());
        adapter.setPassLabelLists(new ArrayList<List<PassLabel>>());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                PassFragment.this.onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        adapter.getPasswordList().clear();
        adapter.getPassLabelLists().clear();
        pageNo = 1;
        presenter.loadMore(pageNo, AppConfig.PAGE_SIZE);
    }

    @Override
    public void onLoadSuccess(int type, List<Password> passwordList, List<List<PassLabel>> passlabelLists) {
        adapter.getPasswordList().addAll(0, passwordList);
        adapter.getPassLabelLists().addAll(0, passlabelLists);
        adapter.notifyDataSetChanged();

        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (adapter != null && adapter.getPasswordList() != null && position < adapter.getPasswordList().size()) {
            Password password = adapter.getPasswordList().get(position);
            switch (password.getType()){
                case Password.Type.SEVER:
                    Intent intent = new Intent(getActivity(), PassServerActivity.class);
                    intent.putExtra(PassServerActivity.EXTRA_PASSWORD,password.getUuid());
                    startActivity(intent);
                    break;
            }
        }
    }
}
