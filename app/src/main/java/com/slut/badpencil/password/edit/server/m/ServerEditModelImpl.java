package com.slut.badpencil.password.edit.server.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.PassLabel;
import com.slut.badpencil.database.bean.password.PassLabelBind;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.bean.password.ServerPassword;
import com.slut.badpencil.database.dao.password.PassLabelBindDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.database.dao.password.ServerPasswordDao;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;
import com.slut.badpencil.utils.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by shijianan on 2017/1/4.
 */

public class ServerEditModelImpl implements ServerEditModel {

    @Override
    public void query(String uuid, OnQueryListener onQueryListener) {
        if (TextUtils.isEmpty(uuid)) {
            onQueryListener.onQueryError(ResUtils.getString(R.string.error_cannot_query_from_null));
            return;
        }
        String passUUID = uuid;
        Password password = null;
        ServerPassword serverPassword = null;
        List<PassLabelBind> passLabelBindList = null;
        try {
            password = PasswordDao.getInstances().querySingle(passUUID);
            serverPassword = ServerPasswordDao.getInstances().querySingle(passUUID);
            passLabelBindList = PassLabelBindDao.getInstances().queryByPass(passUUID);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onQueryListener.onQueryError(e.getLocalizedMessage());
            } else {
                onQueryListener.onQueryError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
            return;
        }
        if (password != null && serverPassword != null && passLabelBindList != null) {
            try {
                List<PassLabel> passLabels = new ArrayList<>();
                for (PassLabelBind passLabelBind : passLabelBindList) {
                    PassLabel passLabel = PassLabelDao.getInstances().queryByUUID(passLabelBind.getLabelUuid());
                    if (passLabel != null) {
                        passLabels.add(passLabel);
                    }
                }
                onQueryListener.onQuerySuccess(password, serverPassword, passLabels);
            } catch (SQLException e) {
                if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                    onQueryListener.onQueryError(e.getLocalizedMessage());
                } else {
                    onQueryListener.onQueryError(ResUtils.getString(R.string.error_unknown_exception_happened));
                }
            }
        } else {
            onQueryListener.onQueryError(ResUtils.getString(R.string.error_unknown_exception_happened));
        }
    }

    @Override
    public void checkUI(ServerPassword primaryPassword, String title, String address, String port, String account, String password, String remark, ArrayList<PassLabel> primaryLabels, ArrayList<PassLabel> extraLabels, OnCheckUIListener onCheckUIListener) {
        if (primaryPassword == null) {
            //insert mode
            if (TextUtils.isEmpty(title.trim()) &&
                    TextUtils.isEmpty(address.trim()) &&
                    TextUtils.isEmpty(port.trim()) &&
                    TextUtils.isEmpty(account.trim()) &&
                    TextUtils.isEmpty(password.trim()) &&
                    TextUtils.isEmpty(remark.trim())) {
                if (extraLabels == null) {
                    onCheckUIListener.onUINotChange();
                } else {
                    if (extraLabels.isEmpty()) {
                        onCheckUIListener.onUINotChange();
                    } else {
                        onCheckUIListener.onUIChange();
                    }
                }
            } else {
                if (TextUtils.isEmpty(title.trim()) ||
                        TextUtils.isEmpty(address.trim()) ||
                        TextUtils.isEmpty(port.trim()) ||
                        TextUtils.isEmpty(account.trim()) ||
                        TextUtils.isEmpty(password.trim())) {
                    onCheckUIListener.onInputInvalid();
                } else {
                    onCheckUIListener.onUIChange();
                }
            }
        } else {
            //update mode
            if (TextUtils.equals(title, primaryPassword.getTitle()) &&
                    TextUtils.equals(address, primaryPassword.getAddress()) &&
                    TextUtils.equals(port, primaryPassword.getPort() + "") &&
                    TextUtils.equals(account, primaryPassword.getAccount()) &&
                    TextUtils.equals(password, RSAUtils.decrypt(primaryPassword.getPassword())) &&
                    TextUtils.equals(remark, primaryPassword.getRemark())) {
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
                if (TextUtils.isEmpty(title.trim()) || TextUtils.isEmpty(address.trim()) || TextUtils.isEmpty(port.trim()) || TextUtils.isEmpty(account.trim()) || TextUtils.isEmpty(password.trim())) {
                    onCheckUIListener.onInputInvalid();
                } else {
                    onCheckUIListener.onUIChange();
                }
            }
        }
    }

    @Override
    public void insert(String title, String ip, String port, String account, String password, String remark, ArrayList<PassLabel> passLabelArrayList, OnInsertListener onInsertListener) {
        if (TextUtils.isEmpty(title.trim())) {
            onInsertListener.onInsertEmptyTitle();
            return;
        }
        if (TextUtils.isEmpty(account.trim())) {
            onInsertListener.onInsertEmptyAccount();
            return;
        }
        if (TextUtils.isEmpty(password.trim())) {
            onInsertListener.onInsertEmptyPassword();
            return;
        }
        if (!StringUtils.isIPAddress(ip)) {
            onInsertListener.onInsertIllegalIP();
            return;
        }
        if (!StringUtils.isInteger(port)) {
            onInsertListener.onInsertIllegalPort();
            return;
        }
        String uuid = UUID.randomUUID().toString();
        long stamp = System.currentTimeMillis();
        Password passForInsert = new Password(uuid, title, account, RSAUtils.encrypt(password), remark, Password.Type.SERVER, stamp, stamp);
        try {
            PasswordDao.getInstances().createSingle(passForInsert);
            ServerPassword serverPassword = new ServerPassword(uuid, title, account, RSAUtils.encrypt(password), remark, Password.Type.SERVER, stamp, stamp, uuid, ip, Integer.valueOf(port));
            ServerPasswordDao.getInstances().createSingle(serverPassword);

            for (PassLabel passLabel : passLabelArrayList) {
                String id = UUID.randomUUID().toString();
                PassLabelBind passLabelBind = new PassLabelBind(id, uuid, passLabel.getUuid(), System.currentTimeMillis());
                PassLabelBindDao.getInstances().createSingle(passLabelBind);
            }

            onInsertListener.onInsertSuccess(serverPassword);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onInsertListener.onInsertError(e.getLocalizedMessage());
            } else {
                onInsertListener.onInsertError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

    @Override
    public void update(ServerPassword serverPassword, String title, String ip, String port, String account, String password, String remark, ArrayList<PassLabel> passLabelArrayList, OnUpdateListener onUpdateListener) {
        if (serverPassword == null) {
            onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_cannot_update_null));
            return;
        }
        if (TextUtils.isEmpty(title.trim())) {
            onUpdateListener.onUpdateEmptyTitle();
            return;
        }
        if (!StringUtils.isIPAddress(ip)) {
            onUpdateListener.onUpdateIllegalIP();
            return;
        }
        if (!StringUtils.isInteger(port)) {
            onUpdateListener.onUpdateIllegalPort();
            return;
        }
        if (TextUtils.isEmpty(account)) {
            onUpdateListener.onUpdateEmptyAccount();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            onUpdateListener.onUpdateEmptyPassword();
            return;
        }
        String uuid = serverPassword.getUuid();
        try {
            PasswordDao.getInstances().updateSingle(uuid, title, account, RSAUtils.encrypt(password), remark);
            ServerPasswordDao.getInstances().updateSingle(uuid, ip, port);
            PassLabelBindDao.getInstances().deleteByPasswordUUID(uuid);

            for (PassLabel passLabel : passLabelArrayList) {
                String id = UUID.randomUUID().toString();
                PassLabelBind passLabelBind = new PassLabelBind(id, uuid, passLabel.getUuid(), System.currentTimeMillis());
                PassLabelBindDao.getInstances().createSingle(passLabelBind);
            }

            onUpdateListener.onUpdateSuccess(uuid);
        } catch (SQLException e) {
            if (e != null && !TextUtils.isEmpty(e.getLocalizedMessage())) {
                onUpdateListener.onUpdateError(e.getLocalizedMessage());
            } else {
                onUpdateListener.onUpdateError(ResUtils.getString(R.string.error_unknown_exception_happened));
            }
        }
    }

}
