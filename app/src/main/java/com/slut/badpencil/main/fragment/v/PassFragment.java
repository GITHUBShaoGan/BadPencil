package com.slut.badpencil.main.fragment.v;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slut.badpencil.R;
import com.slut.badpencil.main.fragment.adapter.PassAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private View rootView = null;
    private static volatile PassFragment instances = null;

    private PassAdapter adapter;
    private LinearLayoutManager layoutManager;

    public static PassFragment getInstances() {
        if(instances == null){
            synchronized (PassFragment.class){
                if(instances == null){
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
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_pass,container,false);
        }
        ButterKnife.bind(this,rootView);
        initView();
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent!=null){
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView(){
        refreshLayout.setColorSchemeResources(R.color.colorGoogleRed,R.color.colorGoogleGreen);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PassAdapter();
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

    }
}
