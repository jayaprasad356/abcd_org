package abcdjob.workonline.com.qrcode;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import abcdjob.workonline.com.qrcode.helpers.util.SharedPrefUtil;
import abcdjob.workonline.com.qrcode.helpers.util.database.DatabaseUtil;


public class Data_Device extends MultiDexApplication {

    private static Data_Device sInstance;

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        SharedPrefUtil.init(getApplicationContext());
        DatabaseUtil.init(getApplicationContext());
        //MobileAds.initialize(this, "");
    }
}
