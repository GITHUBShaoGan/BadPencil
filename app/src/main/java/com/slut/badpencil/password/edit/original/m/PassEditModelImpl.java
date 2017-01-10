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

    /**
     * 检验用户是否修改过界面
     *
     * @param primaryPassword   原先传入的密码对象
     * @param title             标题
     * @param account           账号
     * @param password          密码
     * @param remark            备注
     * @param passLabels        待更新的标签信息
     * @param primaryPassLabels 原来的标签信息
     * @param onCheckUICallback 监听器
     */
    @Override
    public void checkUI(Password primaryPassword, String title, String account, String password, String remark, ArrayList<PassLabel> passLabels, ArrayList<PassLabel> primaryPassLabels, OnCheckUICallback onCheckUICallback) {
        if (primaryPassword == null) {
            //insert mode
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
            //update mode
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
    public void queryLabels(String uuid, OnQueryLabelListener onQueryLabelListener) {
        if (TextUtils.isEmpty(uuid)) {
            onQueryLabelListener.onQueryError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        Password password = null;
        List<PassLabelBind> passLabelBindList = null;
        try {
            password = PasswordDao.getInstances().querySingle(uuid);
            passLabelBindList = PassLabelBindDao.getInstances().queryByPass(uuid);
            List<PassLabel> passLabelList = new ArrayList<>();
            for (PassLabelBind passLabelBind : passLabelBindList) {
                PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                passLabelList.add(passLabel);
            }
            onQueryLabelListener.onQuerySuccess(password,passLabelList);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onQueryLabelListener.onQueryError(e.getLocalizedMessage());
            } else {
                onQueryLabelListener.onQueryError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

    /**
     * 更新密码信息
     *
     * @param password           待更新的密码对象
     * @param title              标题
     * @param account            账号信息
     * @param pass               密码
     * @param remark             备注
     * @param passLabelArrayList 标签信息
     * @param onUpdateListener   监听器
     */
    @Override
    public void update(Password password, String title, String account, String pass, String remark, ArrayList<PassLabel> passLabelArrayList, OnUpdateListener onUpdateListener) {
        if (password == null) {
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
        if (TextUtils.isEmpty(pass.trim())) {
            onUpdateListener.onUpdateEmptyPassword();
            return;
        }
        String uuid = password.getUuid();
        try {
            PasswordDao.getInstances().updateSingle(uuid, title, account, RSAUtils.encrypt(pass), remark);
            PassLabelBindDao.getInstances().deleteByPasswordUUID(uuid);
            for (PassLabel passLabel : passLabelArrayList) {
                String id = UUID.randomUUID().toString();
                PassLabelBind passLabelBind = new PassLabelBind(id, uuid, passLabel.getUuid(), System.currentTimeMillis());
                PassLabelBindDao.getInstances().createSingle(passLabelBind);
            }
            password.setTitle(title);
            password.setAccount(account);
            password.setPassword(RSAUtils.encrypt(pass));
            password.setRemark(remark);
            onUpdateListener.onUpdateSuccess(password);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onUpdateListener.onUpdateError(e.getLocalizedMessage());
            } else {
                onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

}
