package com.slut.badpencil.main.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.slut.badpencil.App;
import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.database.bean.password.WebsitePassword;
import com.slut.badpencil.database.dao.password.ServerPasswordDao;
import com.slut.badpencil.database.dao.password.WebsitePassDao;
import com.slut.badpencil.utils.ImageLoadUtils;
import com.slut.badpencil.widget.RoundedLetterView;

import org.apmem.tools.layouts.FlowLayout;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shijianan on 2017/1/4.
 */

public class PassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Password> passwordList;
    private List<List<PassLabel>> passLabelLists;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

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
            case Password.Type.WEBSITE:
                viewHolder = new WebsiteViewHolder(LayoutInflater.from(App.getContext()).inflate(R.layout.item_pass_website, new LinearLayout(App.getContext()), false));
                break;
            case Password.Type.SEVER:
                viewHolder = new ServerViewHolder(LayoutInflater.from(App.getContext()).inflate(R.layout.item_pass_server, new LinearLayout(App.getContext()), false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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
                    case Password.Type.WEBSITE:
                        WebsiteViewHolder websiteViewHolder = (WebsiteViewHolder) holder;
                        websiteViewHolder.title.setText(password.getTitle() + "");
                        websiteViewHolder.account.setText(password.getAccount() + "");
                        try {
                            WebsitePassword websitePassword = WebsitePassDao.getInstances().querySingle(password.getUuid());
                            ImageLoader.getInstance().displayImage(websitePassword.getUrl() + File.separator + "favicon.ico", websiteViewHolder.icon, ImageLoadUtils.initWebsiteIconOption());
                            websiteViewHolder.url.setText(websitePassword.getUrl() + "");
                        } catch (SQLException e) {

                        }
                        break;
                    case Password.Type.SEVER:
                        ServerViewHolder serverViewHolder = (ServerViewHolder) holder;
                        serverViewHolder.title.setText(password.getTitle() + "");
                        serverViewHolder.account.setText(password.getAccount() + "");
                        try {
                            ServerPassword serverPassword = ServerPasswordDao.getInstances().querySingle(password.getUuid());
                            serverViewHolder.ip.setText(serverPassword.getAddress() + "");
                            serverViewHolder.port.setText(serverPassword.getPort() + "");
                        } catch (SQLException e) {

                        }
                        break;
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(v, position);
                    }
                });
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

        private TextView title;
        private RoundedLetterView letterView;
        private TextView account;

        public OriginalViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            letterView = (RoundedLetterView) itemView.findViewById(R.id.letter);
            account = (TextView) itemView.findViewById(R.id.account);
        }
    }

    public class WebsiteViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView title;
        private TextView url;
        private TextView account;

        public WebsiteViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            url = (TextView) itemView.findViewById(R.id.url);
            account = (TextView) itemView.findViewById(R.id.account);
        }
    }

    public class ServerViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView ip;
        private TextView port;
        private TextView account;

        public ServerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            ip = (TextView) itemView.findViewById(R.id.ip);
            port = (TextView) itemView.findViewById(R.id.port);
            account = (TextView) itemView.findViewById(R.id.account);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }
}
