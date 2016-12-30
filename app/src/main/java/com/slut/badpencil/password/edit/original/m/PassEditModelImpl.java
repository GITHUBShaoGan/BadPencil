package com.slut.badpencil.password.edit.original.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.dao.password.PassLabelBindDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public class PassEditModelImpl implements PassEditModel {

    @Override
    public void checkUI(Password primaryPassword, String title, String account, String password, String remark, ArrayList<PassLabel> passLabels, ArrayList<PassLabel> primaryPassLabels, OnCheckUICallback onCheckUICallback) {
        if (primaryPassword == null) {
            if (TextUtils.isEmpty(title.trim()) &&
                    TextUtils.isEmpty(account.trim()) &&
                    TextUtils.isEmpty(password.trim()) &&
                    TextUtils.isEmpty(remark.trim())) {
                if (passLabels != null) {
                    if (passLabels.size() > 0) {
                        onCheckUICallback.onInputInvalid();
                    } else {
                        onCheckUICallback.onUINotChange();
                    }
                } else {
                    onCheckUICallback.onUINotChange();
                }
            } else {
                if (!TextUtils.isEmpty(title.trim()) && !TextUtils.isEmpty(account.trim()) && !TextUtils.isEmpty(password.trim())) {
                    onCheckUICallback.onUIChange();
                } else {
                    onCheckUICallback.onInputInvalid();
                }
            }
        } else {
            if (TextUtils.equals(primaryPassword.getTitle(), title.trim()) &&
                    TextUtils.equals(primaryPassword.getAccount(), account.trim()) &&
                    TextUtils.equals(primaryPassword.getPassword(), RSAUtils.encrypt(password.trim())) &&
                    TextUtils.equals(primaryPassword.getRemark(), remark.trim())) {
                if (passLabels != null && primaryPassLabels != null) {
                    if (passLabels.size() == primaryPassLabels.size()) {
                        int sameCount = 0;
                        for (PassLabel passLabel : passLabels) {
                            for (PassLabel primaryLabel : primaryPassLabels) {
                                if (primaryLabel.getUuid().equals(passLabel.getUuid())) {
                                    sameCount++;
                                    break;
                                }
                            }
                        }
                        if (sameCount == passLabels.size()) {
                            onCheckUICallback.onUINotChange();
                        } else {
                            onCheckUICallback.onUIChange();
                        }
                    } else {
                        onCheckUICallback.onUIChange();
                    }
                } else {
                    onCheckUICallback.onUINotChange();
                }
            } else {
                if (!TextUtils.isEmpty(title.trim()) && !TextUtils.isEmpty(account.trim()) && !TextUtils.isEmpty(password.trim())) {
                    onCheckUICallback.onUIChange();
                } else {
                    onCheckUICallback.onInputInvalid();
                }
            }
        }
    }

    @Override
    public void create(String title, String account, String password, String remark, ArrayList<PassLabel> passLabelArrayList, OnCreateCallback onCreateCallback) {
        if (TextUtils.isEmpty(title.trim())) {
            onCreateCallback.onCreateEmptyTitle();
            return;
        }
        if (TextUtils.isEmpty(account.trim())) {
            onCreateCallback.onCreateEmptyAccount();
            return;
        }
        if (TextUtils.isEmpty(password.trim())) {
            onCreateCallback.onCreateEmptyPassword();
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        String newPass = RSAUtils.encrypt(password);
        Password insertPass = new Password(uuid, title, account, newPass, remark, Password.Type.DEFAULT, stamp, stamp);
        try {
            //创建成功
            PasswordDao.getInstances().createSingle(insertPass);
            for (PassLabel passLabel : passLabelArrayList) {
                String bindUuid = UUID.randomUUID().toString();
                String labelUuid = passLabel.getUuid();
                long s = System.currentTimeMillis();
                PassLabelBind passLabelBind = new PassLabelBind(bindUuid, uuid, labelUuid, s);
                PassLabelBindDao.getInstances().createSingle(passLabelBind);
            }
            onCreateCallback.onCreateSuccess(insertPass);
        } catch (Exception e) {
            //创建失败
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onCreateCallback.onCreateError(e.getLocalizedMessage());
            } else {
                onCreateCallback.onCreateError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

    @Override
    public void queryLabels(Password password, OnQueryLabelListener onQueryLabelListener) {
        if (password == null) {
            onQueryLabelListener.onQueryError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        List<PassLabelBind> passLabelBindList = null;
        try {
            passLabelBindList = PassLabelBindDao.getInstances().queryByPass(password.getUuid());
            List<PassLabel> passLabelList = new ArrayList<>();
            for (PassLabelBind passLabelBind : passLabelBindList) {
                PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                passLabelList.add(passLabel);
            }
            onQueryLabelListener.onQuerySuccess(passLabelList);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onQueryLabelListener.onQueryError(e.getLocalizedMessage());
            } else {
                onQueryLabelListener.onQueryError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

}
