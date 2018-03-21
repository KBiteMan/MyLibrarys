package date.bitman.utils_lib.view;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import date.bitman.utils_lib.R;

/**
 * <p>Title: </p>
 * <p>Description:</p>
 *
 * @author 张禹
 * @date 2017-10-12 13:39
 */

public abstract class BaseDialog extends DialogFragment {
    private Theme mTheme;

    public BaseDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTheme = initTheme();
        if (mTheme == null){
            mTheme = new Theme();
        }
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);

        if (mTheme.backgroundDrawable != 0){
            window.setBackgroundDrawableResource(mTheme.backgroundDrawable);
        }else {
            window.setBackgroundDrawableResource(R.drawable.bg_dialog);
        }
        final LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        View topView = inflater.inflate(R.layout.dialog_top, container, false);
        TextView leftTitle = (TextView) topView.findViewById(R.id.tv_rd_left);
        TextView centerTitle = (TextView) topView.findViewById(R.id.tv_rd_center);
        ImageView rightIV = (ImageView) topView.findViewById(R.id.iv_rd_right);
        if (!TextUtils.isEmpty(mTheme.leftTitle)){
            leftTitle.setText(mTheme.leftTitle);
            if (mTheme.leftSize == 0){
                leftTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTheme.leftSize);
            }else {
                leftTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            }
            if (mTheme.leftColor != 0){
                //noinspection deprecation
                leftTitle.setTextColor(getResources().getColor(mTheme.leftColor));
            }else {
                leftTitle.setTextColor(0xff87b2fe);
            }
        }
        if (!TextUtils.isEmpty(mTheme.centerTitle)){
            centerTitle.setText(mTheme.centerTitle);
            if (mTheme.centerSize != 0){
                centerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTheme.centerSize);
            }else {
                centerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            }
            if (mTheme.centerColor != 0){
                //noinspection deprecation
                centerTitle.setTextColor(getResources().getColor(mTheme.centerColor));
            }else {
                centerTitle.setTextColor(0xff87b2fe);
            }
        }
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        View view = inflater.inflate(setLayout(), container, false);
        layout.addView(topView);
        layout.addView(view);
        if (mTheme.showBottom) {
            View bottomView = inflater.inflate(R.layout.dialog_bottom, container, false);
            TextView cancel = (TextView) bottomView.findViewById(R.id.tv_rd_cancel);
            TextView certain = (TextView) bottomView.findViewById(R.id.tv_rd_certain);
            cancel.setText(mTheme.bLeftText);
            certain.setText(mTheme.bRightText);
            final OnClickListener listerner = setOnClickListerner();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listerner != null){
                        listerner.leftListener();
                    }
                    dismiss();
                }
            });
            certain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listerner != null){
                        listerner.rightListener();
                    }
                    dismiss();
                }
            });

            layout.addView(bottomView);
        }
        initView(view);
        initData();
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (mTheme.wrap){
            window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }else {
            if (mTheme.height == 0 && mTheme.width == 0){
                window.setLayout(920,ViewGroup.LayoutParams.WRAP_CONTENT);
            }else if (mTheme.width == 0 && mTheme.height != 0){
                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,mTheme.height);
            }else if (mTheme.width != 0 && mTheme.height == 0){
                window.setLayout(mTheme.width,ViewGroup.LayoutParams.WRAP_CONTENT);
            }else {
                window.setLayout(mTheme.width,mTheme.height);
            }
        }
        window.setGravity(mTheme.gravity);
    }

    protected abstract Theme initTheme();
    protected abstract int setLayout();
    protected abstract void initView(View view);
    protected abstract void initData();

    public interface OnClickListener{
        void leftListener();
        void rightListener();
    }

    protected abstract OnClickListener setOnClickListerner();

    public void show(Context context){
        FragmentManager manager = ((Activity) context).getFragmentManager();
        show(manager,context.getClass().getSimpleName());
    }

    public class Theme{
        public boolean showBottom = true;                      //是否显示底部按钮，默认有
        public @DrawableRes int backgroundDrawable;     //背景，默认为蓝色圆角
        public String centerTitle;                      //中央标题内容，默认无
        public String leftTitle;                          //左侧标题内容，默认无
        public String bRightText = "确定";                //底部右侧按钮内容，默认确定
        public String bLeftText = "取消";                 //底部左侧按钮内容，默认取消
        public int width;                               //弹窗宽，默认920
        public int height;                              //弹窗高，默认自适
        public int gravity;                             //弹窗位置，默认居中
        public int centerSize;                          //中央标题文本大小，默认16sp
        public int leftSize;                            //左侧标题文本大小,默认16sp
        public int leftColor;                           //左侧标题颜色，默认淡蓝色
        public int centerColor;                         //中央标题颜色，默认淡蓝色
        public boolean wrap = true;                     //默认为包裹布局
    }
}
