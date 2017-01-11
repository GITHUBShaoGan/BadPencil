package com.slut.badpencil.password.edit.wifi.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.WifiPassword;
import com.slut.badpencil.database.dao.password.PassLabelBindDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.database.dao.password.WifiPasswordDao;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by shijianan on 2017/1/11.
 */

public class WifiEditModelImpl implements WifiEditModel {

    @Override
    public void query(String uuid, OnQueryListener onQueryListener) {
        if (TextUtils.isEmpty(uuid)) {
            onQueryListener.onQueryError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        try {
            Password password = PasswordDao.getInstances().querySingle(uuid);
            WifiPassword wifiPassword = WifiPasswordDao.getInstances().querySingle(uuid);
            List<PassLabelBind> passLabelBindList = PassLabelBindDao.getInstances().queryByPass(uuid);
            List<PassLabel> passLabelList = new ArrayList<>();
            for (PassLabelBind passLabelBind : passLabelBindList) {
                PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                passLabelList.add(passLabel);
            }
            onQueryListener.onQuerySuccess(password, wifiPassword, passLabelList);
        } catch (SQLException e) {
            onQueryListener.onQueryError(e.getLocalizedMessage());
        }
    }

    @Override
    public void checkUI(WifiPassword primaryPassObj, String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> primaryLabels, ArrayList<PassLabel> extraLabels, OnCheckUIListener onCheckUIListener) {
        if (primaryPassObj == null) {
            //insert mode
            if (TextUtils.isEmpty(title) &&
                    TextUtils.isEmpty(pswd) &&
                    TextUtils.isEmpty(rtAddr) &&
                    TextUtils.isEmpty(rtPswd) &&
                    TextUtils.isEmpty(opAccount) &&
                    TextUtils.isEmpty(opPswd) &&
                    TextUtils.isEmpty(remark)) {
                if (extraLabels != null && !extraLabels.isEmpty()) {
                    onCheckUIListener.onUIChange();
                } else {
                    onCheckUIListener.onUINotChange();
                }
            } else {
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(pswd)) {
                    onCheckUIListener.onUIChange();
                } else {
                    onCheckUIListener.onInputInvalid();
                }
            }
        } else {
            //update mode
            if (TextUtils.equals(primaryPassObj.getTitle(), title) &&
                    TextUtils.equals(primaryPassObj.getAccount(), name) &&
                    TextUtils.equals(primaryPassObj.getPassword(), RSAUtils.encrypt(pswd)) &&
                    TextUtils.equals(primaryPassObj.getRouterIP(), RSAUtils.encrypt(rtAddr)) &&
                    TextUtils.equals(primaryPassObj.getRouterPassword(), RSAUtils.encrypt(rtPswd)) &&
                    TextUtils.equals(primaryPassObj.getOperatorAccount(), RSAUtils.encrypt(opAccount)) &&
                    TextUtils.equals(primaryPassObj.getOperatorPassword(), RSAUtils.encrypt(opPswd)) &&
                    TextUtils.equals(primaryPassObj.getRemark(), remark)) {
                if (extraLabels != null && primaryLabels != null) {
                    if (extraLabels.size() == primaryLabels.size()) {
                        int sameCount = 0;
                        for (PassLabel passLabel : extraLabels) {
                            for (PassLabel primaryLabel : primaryLabels) {
                                if (primaryLabel.getUuid().equals(passLabel.getUuid())) {
                                    sameCount++;
                                    break;
                                }
                            }
                        }
                        if (sameCount == extraLabels.size()) {
                            onCheckUIListener.onUINotChange();
                        } else {
                            onCheckUIListener.onUIChange();
                        }
                    } else {
                        onCheckUIListener.onUIChange();
                    }
                } else {
                    onCheckUIListener.onUINotChange();
                }
            } else {
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(pswd)) {
                    onCheckUIListener.onUIChange();
                } else {
                    onCheckUIListener.onInputInvalid();
                }
            }
        }
    }

    @Override
    public void insert(String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> extraLabels, OnInsertListener onInsertListener) {
        if (TextUtils.isEmpty(title)) {
            onInsertListener.onInsertEmptyTitle();
            return;
        }
        if (TextUtils.isEmpty(pswd)) {
            onInsertListener.onInsertEmptyPassword();
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();

        if (TextUtils.isEmpty(rtAddr)) {
            rtAddr = RSAUtils.encrypt(rtAddr);
        }
        if (TextUtils.isEmpty(rtPswd)) {
            rtAddr = RSAUtils.encrypt(rtPswd);
        }
        if (TextUtils.isEmpty(opAccount)) {
            rtAddr = RSAUtils.encrypt(opAccount);
        }
        if (TextUtils.isEmpty(opPswd)) {
            rtAddr = RSAUtils.encrypt(opPswd);
        }

        Password password = new Password(uuid, title, name, RSAUtils.encrypt(pswd), remark, Password.Type.WIFI, stamp, stamp);
        WifiPassword wifiPassword = new WifiPassword(uuid, rtAddr, rtPswd, opAccount, opPswd);

        try {
            PasswordDao.getInstances().createSingle(password);
            WifiPasswordDao.getInstances().createSingle(wifiPassword);
            if (extraLabels != null) {
                for (PassLabel passLabel : extraLabels) {
                    String id = UUID.randomUUID().toString();
                    long s = System.currentTimeMillis();
                    PassLabelBind passLabelBind = new PassLabelBind(id, uuid, passLabel.getUuid(), s);
                    PassLabelBindDao.getInstances().createSingle(passLabelBind);
                }
            }
            onInsertListener.onInsertSuccess(uuid);
        } catch (SQLException e) {
            onInsertListener.onInsertError(e.getLocalizedMessage());
        }
    }

    @Override
    public void update(String uuid, String title, String name, String pswd, String rtAddr, String rtPswd, String opAccount, String opPswd, String remark, ArrayList<PassLabel> extraLabels, OnUpdateListener onUpdateListener) {
        if (TextUtils.isEmpty(uuid)) {
            onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_cannot_update_null));
            return;
        }
        if (TextUtils.isEmpty(title)) {
            onUpdateListener.onUpdateEmptyTitle();
            return;
        }
        if (TextUtils.isEmpty(pswd)) {
            onUpdateListener.onUpdateEmptyPassword();
            return;
        }
        try {
            PasswordDao.getInstances().updateSingle(uuid, title, name, RSAUtils.encrypt(pswd), remark);
            WifiPasswordDao.getInstances().updateSingle(uuid,RSAUtils.encrypt(rtAddr), RSAUtils.encrypt(rtPswd), RSAUtils.encrypt(opAccount), RSAUtils.encrypt(opPswd));
            onUpdateListener.onUpdateSuccess(uuid);
        } catch (SQLException e) {
            onUpdateListener.onUpdateError(e.getLocalizedMessage());
        }
    }

}
