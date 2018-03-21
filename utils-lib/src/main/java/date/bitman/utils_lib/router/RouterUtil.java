package date.bitman.utils_lib.router;

import android.content.Context;

import date.bitman.designdemo.router.lib.BabyRouter;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2018-03-21 15:46
 */

public class RouterUtil {
    private static RouterUtil mInstance = null;
    private Context mContext;
    private RouterUtil(Context context){
        mContext = context;
    }

    public static RouterUtil getInstance(Context context){
        if (mInstance == null){
            mInstance = new RouterUtil(context);
        }
        return mInstance;
    }

    public UriApi jump(){
        UriApi uriApi = BabyRouter.getInstance(mContext).create(UriApi.class);
        return uriApi;
    }
}
