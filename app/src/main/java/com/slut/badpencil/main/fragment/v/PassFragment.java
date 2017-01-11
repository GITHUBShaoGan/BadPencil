package com.slut.badpencil.main.fragment.v;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.slut.badpencil.notification.observer.PasswordObserver;
import com.slut.badpencil.notification.subject.PasswordSubject;
import com.slut.badpencil.password.show.original.v.PassOriginalActivity;
import com.slut.badpencil.password.show.server.v.PassServerActivity;
import com.slut.badpencil.password.show.website.v.PassWebsiteActivity;
import com.slut.badpencil.password.show.wifi.v.PassWifiActivity;
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
    private PassAdapter adapter;
    private LinearLayoutManager layoutManager;
    private PassPresenter presenter;

    private long pageNo = 1;//分页加载页码

    public PassFragment() {
        // Required empty public constructor
    }

    private PasswordObserver passwordObserver = new PasswordObserver() {

        @Override
        public void itemInserted(Object obj) {
            super.itemInserted(obj);
            presenter.notifyItemInsert(obj.toString());
        }

        @Override
        public void itemChanged(Object obj) {
            super.itemChanged(obj);
            presenter.notifyItemChanged(obj.toString(), adapter.getPasswordList());
        }

        @Override
        public void itemRemoved(Object obj) {
            super.itemRemoved(obj);
            presenter.notifyItemRemove(obj.toString(),adapter.getPasswordList());
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PasswordSubject.getInstances().registerObserver(passwordObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PasswordSubject.getInstances().removeObserver(passwordObserver);
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
    public void notifyItemChangeSuccess(int position, Password password, List<PassLabel> passLabelList) {
        adapter.getPasswordList().set(position, password);
        adapter.getPassLabelLists().set(position, passLabelList);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void notifyItemChangeError(String msg) {

    }

    @Override
    public void notifyItemInsertSuccess(Password password, List<PassLabel> passLabelList) {
        adapter.getPasswordList().add(0, password);
        adapter.getPassLabelLists().add(0, passLabelList);
        adapter.notifyItemInserted(0);
        recyclerView.smoothScrollBy(0, 0);
    }

    @Override
    public void notifyItemInsertError(String msg) {

    }

    @Override
    public void notifyItemRemoveSuccess(int position) {
        adapter.getPasswordList().remove(position);
        adapter.getPassLabelLists().remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemRemoveError(String msg) {

    }

    @Override
    public void onItemClick(View view, int position) {
        if (adapter != null && adapter.getPasswordList() != null && position < adapter.getPasswordList().size()) {
            Password password = adapter.getPasswordList().get(position);
            switch (password.getType()) {
                case Password.Type.DEFAULT:
                    Intent intent2Default = new Intent(getActivity(), PassOriginalActivity.class);
                    intent2Default.putExtra(PassOriginalActivity.EXTRA_UUID, password.getUuid());
                    startActivity(intent2Default);
                    break;
                case Password.Type.SERVER:
                    Intent intent = new Intent(getActivity(), PassServerActivity.class);
                    intent.putExtra(PassServerActivity.EXTRA_PASSWORD, password.getUuid());
                    startActivity(intent);
                    break;
                case Password.Type.WEBSITE:
                    Intent intent2Web = new Intent(getActivity(), PassWebsiteActivity.class);
                    intent2Web.putExtra(PassWebsiteActivity.EXTRA_UUID, password.getUuid());
                    startActivity(intent2Web);
                    break;
                case Password.Type.WIFI:
                    Intent intent2Wifi = new Intent(getActivity(), PassWifiActivity.class);
                    intent2Wifi.putExtra(PassWifiActivity.EXTRA_UUID, password.getUuid());
                    startActivity(intent2Wifi);
                    break;
            }
        }
    }
}
