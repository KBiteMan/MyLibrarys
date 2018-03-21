package date.bitman.mylibrarys;

import android.view.View;
import android.view.ViewGroup;

import date.bitman.utils_lib.view.BaseDialog;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2017-10-19 16:11
 */

public class MyDialog extends BaseDialog {
    @Override
    protected Theme initTheme() {
        Theme theme = new Theme();
        theme.width = ViewGroup.LayoutParams.MATCH_PARENT;
        return theme;
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_my;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected OnClickListener setOnClickListerner() {
        return null;
    }
}
