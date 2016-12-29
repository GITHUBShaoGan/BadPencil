package com.slut.badpencil.password.edit.wifi.list;

import android.media.Image;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slut.badpencil.App;
import com.slut.badpencil.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ItemViewHolder> {

    private List<ScanResult> scanResultList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private WifiManager wifiManager;

    public WifiListAdapter(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    public List<ScanResult> getScanResultList() {
        return scanResultList;
    }

    public void setScanResultList(List<ScanResult> scanResultList) {
        this.scanResultList = scanResultList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(App.getContext()).inflate(R.layout.item_wifi_list, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        if (scanResultList != null && position < scanResultList.size()) {
            holder.ssid.setText(scanResultList.get(position).SSID + "");
            int levelBelowFive = wifiManager.calculateSignalLevel(scanResultList.get(position).level, 5);
            switch (levelBelowFive) {
                case 0:
                    holder.level.setImageResource(R.drawable.ic_signal_wifi_0_bar_grey600_24dp);
                    break;
                case 1:
                    holder.level.setImageResource(R.drawable.ic_signal_wifi_1_bar_grey600_24dp);
                    break;
                case 2:
                    holder.level.setImageResource(R.drawable.ic_signal_wifi_2_bar_grey600_24dp);
                    break;
                case 3:
                    holder.level.setImageResource(R.drawable.ic_signal_wifi_3_bar_grey600_24dp);
                    break;
                case 4:
                    holder.level.setImageResource(R.drawable.ic_signal_wifi_4_bar_grey600_24dp);
                    break;
            }
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
        if (scanResultList != null) {
            return scanResultList.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ssid)
        TextView ssid;
        @BindView(R.id.level)
        ImageView level;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }

}
