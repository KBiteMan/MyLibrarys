package date.bitman.utils_lib;

import android.app.Application;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2017-12-21 16:33
 */

public class InitJar {
    private static Application mApplication;
    private static InitJar mInstance = null;
    private InitJar(){

    }
    public static InitJar getInstance(){
        if (mInstance == null){
            mInstance = new InitJar();
        }
        return mInstance;
    }

    public static void init(Application application){
        mApplication = application;
    }

    public Application getApplication() {
        return mApplication;
    }

}
