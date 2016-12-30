package com.slut.badpencil.password.label.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.slut.badpencil.App;
import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ItemViewHolder> {

    private List<PassLabel> passLabelList;

    public List<PassLabel> getPassLabelList() {
        return passLabelList;
    }

    public void setPassLabelList(List<PassLabel> passLabelList) {
        this.passLabelList = passLabelList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_label_with_cb, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (passLabelList != null && position < passLabelList.size()) {
            PassLabel passLabel = passLabelList.get(position);
            if (passLabel != null) {
                holder.name.setText(passLabel.getName() + "");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (passLabelList != null) {
            return passLabelList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.checkbox)
        CheckBox checkBox;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
