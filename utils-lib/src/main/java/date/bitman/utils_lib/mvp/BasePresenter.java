package date.bitman.utils_lib.mvp;

import java.lang.ref.WeakReference;
import java.util.Date;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 *
 */

public abstract class BasePresenter<M extends BaseModel,V extends BaseView> {
    public M mModel;
    private WeakReference<V> mWeak;

    public BasePresenter(){
        mModel = createModel();
    }

    public void attach(V v){
        mWeak = new WeakReference<V>(v);
    }

    public void detach(){
        if (mWeak != null){
            mWeak.clear();
            mWeak = null;
        }
    }

    public V getView(){
        if (mWeak != null){
            return mWeak.get();
        }
        return null;
    }

    protected abstract M createModel();
}