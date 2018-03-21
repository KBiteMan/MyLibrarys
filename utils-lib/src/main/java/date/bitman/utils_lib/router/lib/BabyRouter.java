package date.bitman.utils_lib.router.lib;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2018-03-21 13:48
 */

public class BabyRouter {
    private static BabyRouter mBabyRouter = null;
    private Context mContext;
    private BabyRouter(Context context){
        mContext = context.getApplicationContext();
    }

    public static BabyRouter getInstance(Context context){
        if (mBabyRouter == null){
            mBabyRouter = new BabyRouter(context);
        }
        return mBabyRouter;
    }

    private <T> T proxy(final Class<T> clazz){
        T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                StringBuffer stringBuffer = new StringBuffer();
                RouterUri routerUri = method.getAnnotation(RouterUri.class);
                Log.d("BabyRouter", "jump to ---> "+routerUri.routerUri());
                stringBuffer.append(routerUri.routerUri());
                Annotation[][] params = method.getParameterAnnotations();
                int pos = 0;
                for (int i = 0; i < params.length; i++) {
                    Annotation[] param = params[i];
                    if (param != null && param.length != 0){
                        if(pos == 0){
                            stringBuffer.append("?");
                        }else {
                            stringBuffer.append("&");
                        }
                        pos++;
                        RouterParam annotation = (RouterParam) param[0];
                        stringBuffer.append(annotation.value());
                        stringBuffer.append("=");
                        stringBuffer.append(objects[i]);
                        Log.d("BabyRouter", "jumpParams ---> " + annotation.value() + "=" + objects[i]);
                    }
                }
                openRouterUri(stringBuffer.toString());
                return null;
            }
        });
        return t;
    }

    private void openRouterUri(String url){
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        if (!activities.isEmpty()){
            mContext.startActivity(intent);
        }
    }

    public <T> T create(Class<T> t){
        return proxy(t);
    }

}
