package com.slut.badpencil.main.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.slut.badpencil.App;
import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.widget.RoundedLetterView;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shijianan on 2017/1/4.
 */

public class PassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Password> passwordList;
    private List<List<PassLabel>> passLabelLists;

    public PassAdapter() {
    }

    public List<Password> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<Password> passwordList) {
        this.passwordList = passwordList;
    }

    public List<List<PassLabel>> getPassLabelLists() {
        return passLabelLists;
    }

    public void setPassLabelLists(List<List<PassLabel>> passLabelLists) {
        this.passLabelLists = passLabelLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case Password.Type.DEFAULT:
                viewHolder = new OriginalViewHolder(LayoutInflater.from(App.getContext()).inflate(R.layout.item_pass_original, new LinearLayout(App.getContext()), false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (passwordList != null && position < passwordList.size()) {
            Password password = passwordList.get(position);
            if (password != null) {
                switch (getItemViewType(position)) {
                    case Password.Type.DEFAULT:
                        OriginalViewHolder originalViewHolder = (OriginalViewHolder) holder;
                        originalViewHolder.title.setText(password.getTitle() + "");
                        originalViewHolder.account.setText(password.getAccount() + "");
                        originalViewHolder.letterView.setTitleText(password.getAccount().charAt(0) + "");
                        break;
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (passwordList != null && position < passwordList.size()) {
            return passwordList.get(position).getType();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (passwordList != null) {
            return passwordList.size();
        }
        return 0;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.letter)
        RoundedLetterView letterView;
        @BindView(R.id.account)
        TextView account;
        @BindView(R.id.flowLayout)
        FlowLayout flowLayout;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
