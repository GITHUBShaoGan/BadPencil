package com.slut.badpencil.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.slut.badpencil.R;

/**
 * Created by shijianan on 2017/1/5.
 */

public class ImageLoadUtils {

    public static DisplayImageOptions initWebsiteIconOption(){
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        builder.showImageForEmptyUri(R.drawable.ic_paper_grey_24);
        builder.showImageOnFail(R.drawable.ic_paper_grey_24);
        builder.showImageOnLoading(R.drawable.ic_paper_grey_24);
        return builder.build();
    }

}
