package date.bitman.utils_lib.view.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2018-01-10 11:40
 */

public abstract class BasePopupWindow implements BasePopup {
    private Activity mActivity;
    private View mView;
    private PopupWindow mPopupWindow;
    public BasePopupHelper mHelper;

    public BasePopupWindow(Activity activity) {
        initPopup(activity, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public BasePopupWindow(Activity activity, int width, int height) {
        initPopup(activity, width, height);
    }

    private void initPopup(Activity activity, int w, int h) {
        mActivity = activity;
        mHelper = new BasePopupHelper();
        mHelper.setOffsetX(0);
        mHelper.setOffsetY(0);
        mView = LayoutInflater.from(mActivity).inflate(getPopupView(), null, false);
        mPopupWindow = new PopupWindow(mView, w, h);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        initData(mView);
    }

    protected abstract void initData(View view);

    private void bashShow(View view) {
        if (view != null) {
            int[] size = getViewSize(view);
            if (Build.VERSION.SDK_INT >= 19) {
                mPopupWindow.showAsDropDown(view, 0,-mPopupWindow.getWidth(),Gravity.CENTER);
            } else {
                mPopupWindow.showAtLocation(view,Gravity.CENTER, 0, 0);
            }
        } else {
            mPopupWindow.showAtLocation(mActivity.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
        }
    }

    public void show() {
        bashShow(null);
    }

    public void show(View view) {
        bashShow(view);
    }

    private int[] getViewSize(View view) {
        int[] offset = {mHelper.getOffsetX(), mHelper.getOffsetY()};
        mHelper.getAuchorLocation(view);
        offset[1] = -view.getHeight() - view.getHeight() - offset[1];
        return offset;
    }
}
