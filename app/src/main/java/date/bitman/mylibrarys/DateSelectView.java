package date.bitman.mylibrarys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2017-11-06 13:02
 */

public class DateSelectView extends View {

    private TextPaint mTextPaint;
    private Paint mPaint;
    private Rect mRect;
    private String mTitle;
    private boolean mIsSelect;
    private boolean mIsHave;

    public DateSelectView(Context context) {
        this(context, null);
    }

    public DateSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(24);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLUE);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);

        mRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;
        if (!TextUtils.isEmpty(mTitle)){
            mTextPaint.getTextBounds(mTitle,0,mTitle.length(),mRect);
        }

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = mRect.width() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mRect.height() + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsSelect){
            canvas.drawCircle(mRect.width()/2+getPaddingLeft(),mRect.height()/2+getPaddingTop(),
                    20,mPaint);
        }
        canvas.drawText(mTitle, 0, mTitle.length(),
                getWidth()/2-mRect.width()/2-4,
                getHeight()/2+mRect.height()/2, mTextPaint);
        if (mIsHave){
            canvas.drawCircle(mRect.width()+getPaddingLeft()+8,getPaddingTop()-4,
                    5,mPaint);
        }
    }

    public void setTitle(String title) {
        mTitle = title;
        invalidate();
    }

    public void setSelect(boolean isSelect){
        mIsSelect = isSelect;
        invalidate();
    }

    public void isHave(boolean isHave){
        mIsHave = isHave;
        invalidate();
    }

}
