package com.slut.badpencil.password.edit.website.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WebsitePassword;
import com.slut.badpencil.database.dao.password.PassLabelBindDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.database.dao.password.WebsitePassDao;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/30.
 */

public class WebsiteEditModelImpl implements WebsiteEditModel {

    @Override
    public void queryLabels(WebsitePassword websitePassword, OnQueryLabelsListener onQueryLabelsListener) {
        if (websitePassword == null) {
            onQueryLabelsListener.onQueryError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        List<PassLabelBind> passLabelBindList = null;
        try {
            passLabelBindList = PassLabelBindDao.getInstances().queryByPass(websitePassword.getUuid());
            List<PassLabel> passLabelList = new ArrayList<>();
            for (PassLabelBind passLabelBind : passLabelBindList) {
                PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                passLabelList.add(passLabel);
            }
            onQueryLabelsListener.onQuerySuccess(passLabelList);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onQueryLabelsListener.onQueryError(e.getLocalizedMessage());
            } else {
                onQueryLabelsListener.onQueryError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

    @Override
    public void checkUI(WebsitePassword primaryPassword, String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList, ArrayList<PassLabel> primaryLabelList, OnCheckUIListener onCheckUIListener) {
        if (primaryPassword == null) {
            if (TextUtils.isEmpty(title.trim()) &&
                    TextUtils.isEmpty(account.trim()) &&
                    TextUtils.isEmpty(password.trim()) &&
                    TextUtils.equals(url.trim(), ResUtils.getString(R.string.header_website)) &&
                    TextUtils.isEmpty(remark.trim())) {
                if (passLabelArrayList != null) {
                    if (passLabelArrayList.isEmpty()) {
                        onCheckUIListener.onUINotChange();
                    } else {
                        onCheckUIListener.onInputInvalid();
                    }
                } else {
                    onCheckUIListener.onUINotChange();

                }
            } else {
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                    onCheckUIListener.onUIChange();
                } else {
                    onCheckUIListener.onInputInvalid();
                }
            }
        } else {
            if (TextUtils.equals(primaryPassword.getTitle(), title.trim()) &&
                    TextUtils.equals(primaryPassword.getAccount(), account.trim()) &&
                    TextUtils.equals(primaryPassword.getPassword(), RSAUtils.encrypt(password.trim())) &&
                    TextUtils.equals(primaryPassword.getUrl(), url.trim()) &&
                    TextUtils.equals(primaryPassword.getRemark(), remark.trim())) {
                if (passLabelArrayList != null && primaryLabelList != null) {
                    if (passLabelArrayList.size() == primaryLabelList.size()) {
                        int sameCount = 0;
                        for (PassLabel passLabel : passLabelArrayList) {
                            for (PassLabel primaryLabel : primaryLabelList) {
                                if (primaryLabel.getUuid().equals(passLabel.getUuid())) {
                                    sameCount++;
                                    break;
                                }
                            }
                        }
                        if (sameCount == passLabelArrayList.size()) {
                            onCheckUIListener.onUINotChange();
                        } else {
                            onCheckUIListener.onUIChange();
                        }
                    } else {
                        onCheckUIListener.onUIChange();
                    }
                } else {
                    if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                        onCheckUIListener.onUIChange();
                    } else {
                        onCheckUIListener.onInputInvalid();
                    }
                }
            }
        }
    }

    @Override
    public void create(String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabelArrayList, OnCreateListener onCreateListener) {
        if (TextUtils.isEmpty(title.trim())) {
            onCreateListener.onCreateEmptyTitle();
            return;
        }
        if (TextUtils.isEmpty(account.trim())) {
            onCreateListener.onCreateEmptyAccount();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            onCreateListener.onCreateEmptyPassword();
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        Password newPassword = new Password(uuid, title, account, RSAUtils.encrypt(password), remark, Password.Type.WEBSITE, stamp, stamp);
        WebsitePassword websitePassword = new WebsitePassword(uuid, title, account, RSAUtils.encrypt(password), remark, Password.Type.WEBSITE, stamp, stamp, uuid, url);
        try {
            PasswordDao.getInstances().createSingle(newPassword);
            WebsitePassDao.getInstances().createSingle(websitePassword);
            for (PassLabel passLabel : passLabelArrayList) {
                String bindUuid = UUID.randomUUID().toString();
                String labelUuid = passLabel.getUuid();
                long s = System.currentTimeMillis();
                PassLabelBind passLabelBind = new PassLabelBind(bindUuid, uuid, labelUuid, s);
                PassLabelBindDao.getInstances().createSingle(passLabelBind);
            }
            onCreateListener.onCreateSuccess(websitePassword);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onCreateListener.onCreateError(e.getLocalizedMessage());
            } else {
                onCreateListener.onCreateError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

    @Override
    public void update(WebsitePassword websitePassword, String title, String account, String password, String url, String remark, ArrayList<PassLabel> passLabels, OnUpdateListener onUpdateListener) {
        if (websitePassword == null) {
            onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_cannot_update_null));
            return;
        }
        if (TextUtils.isEmpty(title.trim())) {
            onUpdateListener.onUpdateEmptyTitle();
            return;
        }
        if (TextUtils.isEmpty(account.trim())) {
            onUpdateListener.onUpdateEmptyAccount();
            return;
        }
        if (TextUtils.isEmpty(password.trim())) {
            onUpdateListener.onUpdateEmptyPassword();
            return;
        }
        String uuid = websitePassword.getPassUuid();
        String passAfterEncrypt = RSAUtils.encrypt(password);
        try {
            PasswordDao.getInstances().updateSingle(uuid, title, account, passAfterEncrypt, remark);
            WebsitePassDao.getInstances().updateSingle(uuid, url);
            PassLabelBindDao.getInstances().deleteByPasswordUUID(uuid);
            for (PassLabel passLabel : passLabels) {
                String id = UUID.randomUUID().toString();
                PassLabelBind passLabelBind = new PassLabelBind(id, uuid, passLabel.getUuid(), System.currentTimeMillis());
                PassLabelBindDao.getInstances().createSingle(passLabelBind);
            }
            websitePassword.setTitle(title);
            websitePassword.setAccount(account);
            websitePassword.setPassword(passAfterEncrypt);
            websitePassword.setUrl(url);
            websitePassword.setRemark(remark);
            websitePassword.setUpdateStamp(System.currentTimeMillis());
            onUpdateListener.onUpdateSuccess(websitePassword);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onUpdateListener.onUpdateError(e.getLocalizedMessage());
            } else {
                onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

}
