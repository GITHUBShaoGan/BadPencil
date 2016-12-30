package com.slut.badpencil.password.edit.original.m;

import android.text.TextUtils;

import com.slut.badpencil.R;
import com.slut.badpencil.database.bean.password.Password;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.rsa.RSAUtils;
import com.slut.badpencil.utils.ResUtils;

import org.w3c.dom.Text;

import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public class PassEditModelImpl implements PassEditModel {

    @Override
    public void checkUI(Password primaryPassword, String title, String account, String password, String remark, OnCheckUICallback onCheckUICallback) {
        if (primaryPassword == null) {
            if (TextUtils.isEmpty(title.trim()) &&
                    TextUtils.isEmpty(account.trim()) &&
                    TextUtils.isEmpty(password.trim()) &&
                    TextUtils.isEmpty(remark.trim())) {
                onCheckUICallback.onUINotChange();
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
                    TextUtils.equals(primaryPassword.getRemark(), RSAUtils.encrypt(remark.trim()))) {
                onCheckUICallback.onUINotChange();
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
    public void create(String title, String account, String password, String remark, OnCreateCallback onCreateCallback) {
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

}
