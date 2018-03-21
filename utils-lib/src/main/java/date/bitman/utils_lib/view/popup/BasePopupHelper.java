package date.bitman.utils_lib.view.popup;

import android.view.View;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2018-01-11 16:20
 */

public class BasePopupHelper {
    private int[] mAuchorLocations;
    private int offsetX;
    private int offsetY;

    public BasePopupHelper() {
        mAuchorLocations = new int[2];
    }

    public BasePopupHelper getAuchorLocation(View view){
        if (view == null){
            return this;
        }
        view.getLocationOnScreen(mAuchorLocations);
        return this;
    }

    int getAnchorX() {
        return mAuchorLocations[0];
    }

    int getAnchorY() {
        return mAuchorLocations[1];
    }

    int getOffsetX() {
        return offsetX;
    }

    BasePopupHelper setOffsetX(int offsetX) {
        this.offsetX = offsetX;
        return this;
    }

    int getOffsetY() {
        return offsetY;
    }

    BasePopupHelper setOffsetY(int offsetY) {
        this.offsetY = offsetY;
        return this;
    }
}
