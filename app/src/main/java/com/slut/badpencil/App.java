package com.slut.badpencil;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.slut.badpencil.database.DBConfig;
import com.slut.badpencil.database.DBHelper;
import com.slut.badpencil.database.dao.UserConfigDao;
import com.slut.badpencil.database.dao.password.PassLabelDao;
import com.slut.badpencil.database.dao.password.PasswordDao;
import com.slut.badpencil.utils.FileUtils;

/**
 * Created by 七月在线科技 on 2016/12/27.
 */

public class App extends Application {

    private static Context context;//全局上下文
    private static DBHelper dbHelper;
    private int activityCount = 0;
    private static boolean isLocked = false;

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        initHelper();
        initDao();
        initUniversalImageLoader();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount == 0) {
                    setIsLocked(true);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    public static void initHelper() {
        dbHelper = new DBHelper(getContext(), DBConfig.DB_NAME, null, DBConfig.DB_VERSION);
    }

    private void initDao() {
        UserConfigDao.getInstances().initDao();
        PasswordDao.getInstances().initDao();
        PassLabelDao.getInstances().initDao();
    }

    public static boolean isLocked() {
        return isLocked;
    }

    public static void setIsLocked(boolean isLocked) {
        App.isLocked = isLocked;
    }

    private void initUniversalImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)//鐠佸墽鐤嗙痪璺ㄢ柤娴兼ê鍘涚痪?
                .threadPoolSize(4)//缁捐法鈻煎Ч鐘插敶閸旂姾娴囬惃鍕殶闁?閹恒劏宕橀懠鍐ㄦ纯1-5閸愬懌鈧?
                .denyCacheImageMultipleSizesInMemory()//瑜版挸鎮撴稉鈧稉鐚唕i閼惧嘲褰囨稉宥呮倱婢堆冪毈閻ㄥ嫬娴橀悧鍥╃处鐎涙ê鍩岄崘鍛摠娑擃厽妞傞崣顏嗙处鐎涙ü绔存稉顏傗偓鍌欑瑝鐠佸墽鐤嗛惃鍕樈姒涙顓绘导姘辩处鐎涙ê顦挎稉顏冪瑝閸氬苯銇囩亸蹇曟畱閸ュ墽澧?
                .memoryCacheExtraOptions(480, 800)//閸愬懎鐡ㄧ紓鎾崇摠閺傚洣娆㈤惃鍕付婢堆囨毐鎼?
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))//閸愬懎鐡ㄧ紓鎾崇摠閺傜懓绱?鏉╂瑩鍣烽崣顖欎簰閹广垺鍨氶懛顏勭箒閻ㄥ嫬鍞寸€涙绱︾€涙ê鐤勯悳鑸偓?閹恒劏宕楲ruMemoryCache,闁挾鎮婇懛顏勭箒閹冲倻娈?
                .memoryCacheSize(10 * 1024 * 1024)//閸愬懎鐡ㄧ紓鎾崇摠閻ㄥ嫭娓舵径褍鈧?
                .diskCache(new UnlimitedDiskCache(FileUtils.createImageCacheSavePath()))//閸欘垯浜掗懛顏勭暰娑斿绱︾€涙鐭惧?
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//鐎甸€涚箽鐎涙娈慤RL鏉╂稖顢戦崝鐘茬槕娣囨繂鐡?
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000))//鐠佸墽鐤嗘潻鐐村复閺冨爼妫?s,鐡掑懏妞傞弮鍫曟？30s
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
