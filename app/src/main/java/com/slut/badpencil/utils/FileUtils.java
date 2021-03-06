package com.slut.badpencil.utils;

import android.content.Context;
import android.os.Environment;


import com.slut.badpencil.App;

import java.io.File;

import static android.R.attr.path;

/**
 * Created by 七月在线科技 on 2016/12/7.
 */

public class FileUtils {

    public static File createImageCacheSavePath() {
        String path = App.getContext().getExternalCacheDir() + File.separator + "imageCache";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

}
