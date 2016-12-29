package com.slut.badpencil.password.edit.mode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slut.badpencil.App;
import com.slut.badpencil.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/28.
 */

public class EditModeAdapter extends RecyclerView.Adapter<EditModeAdapter.ItemViewHolder> {

    private String[] titleArr;
    private int[] iconArr;
    private OnItemClickListener onItemClickListener;

    public EditModeAdapter(String[] titleArr, int[] iconArr) {
        this.titleArr = titleArr;
        this.iconArr = iconArr;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_pass_edit_mode, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        if (titleArr != null && iconArr != null && position < titleArr.length && iconArr.length == titleArr.length) {
            holder.name.setText(titleArr[position] + "");
            holder.image.setImageResource(iconArr[position]);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (titleArr != null) {
            return titleArr.length;
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }
}
