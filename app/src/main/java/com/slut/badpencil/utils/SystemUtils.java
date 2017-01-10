package com.slut.badpencil.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.slut.badpencil.App;
import com.slut.badpencil.R;

/**
 * Created by shijianan on 2017/1/10.
 */

public class SystemUtils {

    public static void copy(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) App.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(text, text);
        clipboardManager.setPrimaryClip(clipData);
        ToastUtils.showShort(R.string.success_copy);
    }

}
