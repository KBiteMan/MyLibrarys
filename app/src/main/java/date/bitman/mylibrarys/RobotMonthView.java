package date.bitman.mylibrarys;

import android.content.Context;
import android.graphics.Canvas;

import date.bitman.company_lib.calendar.Calendar;
import date.bitman.company_lib.calendar.MonthView;


/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2018-02-27 10:29
 */

public class RobotMonthView extends MonthView {
    public RobotMonthView(Context context) {
        super(context);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {

    }
}
