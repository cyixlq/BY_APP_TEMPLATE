package top.cyixlq.byapptemplate;

import android.app.Application;
import top.cyixlq.network.NetWorkManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().setEnableLog(BuildConfig.DEBUG).init(this);
    }
}
