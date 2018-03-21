package date.bitman.mylibrarys;

import android.app.Application;

import date.bitman.utils_lib.InitJar;


/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2017-10-19 16:35
 */

public class BApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitJar.init(this);
    }
}
