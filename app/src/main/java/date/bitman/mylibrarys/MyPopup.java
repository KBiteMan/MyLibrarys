package date.bitman.mylibrarys;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import date.bitman.utils_lib.view.popup.BasePopupWindow;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2018-01-10 13:16
 */

public class MyPopup extends BasePopupWindow {

    public MyPopup(Activity activity) {
        super(activity);
    }

    public MyPopup(Activity activity, int width, int height) {
        super(activity, width, height);
    }

    @Override
    public int getPopupView() {
        return R.layout.popup_favour;
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    @Override
    protected void initData(View view) {

    }
}
