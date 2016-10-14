package utouu.com.libs;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 李良海 on 2016/10/14 13:02.
 * Function:
 * Desc:
 */

public class TitleBarView extends LinearLayout {

    private static final int DEFAULT_TEXT_COLOR = 0XFFFFFFFF;
    private static final int DEFAULT_BG_COLOR = 0XFF2C2D31;
    private static final int DEFAULT_TEXT_SIZE = 16;

    /**
     * 左边容器
     */
    private LinearLayout mLeftContainer;
    /**
     * 右边容器
     */
    private LinearLayout mCenterContainer;
    /**
     * 左边容器
     */
    private LinearLayout mRightContainer;

    private TextView mLeftTv;
    private TextView mTitleTv;

    /**
     * 下方分割线
     */
    private View mDividerView;

    /**
     * 状态栏高度
     */
    private int mStatusHeight;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    private int mPaddingValue;
    private int mDividerHeight;

    /**
     * 支持沉浸式状态栏
     */
    private boolean isImmersive;


    public TitleBarView(Context context) {
        this(context, null, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (isImmersive) {//支持沉浸式，获取状态栏高度
            mStatusHeight = getStatusHeight();
        }

        mScreenWidth = getScreenWidth();
        mPaddingValue = dip2px(context,10);
        mDividerHeight = dip2px(context,1);
        initView(context);
    }

    private void initView(Context context) {

        setBackgroundColor(DEFAULT_BG_COLOR);

        mLeftContainer = new LinearLayout(context);
        mCenterContainer = new LinearLayout(context);
        mRightContainer = new LinearLayout(context);
        mDividerView = new View(context);

        mDividerView.setBackgroundColor(Color.YELLOW);

        mLeftContainer.setGravity(Gravity.CENTER_VERTICAL);
        mCenterContainer.setGravity(Gravity.CENTER);
        mRightContainer.setGravity(Gravity.CENTER_VERTICAL);
        mLeftContainer.setPadding(mPaddingValue,0,0,0);
        mRightContainer.setPadding(0,0,mPaddingValue,0);

        mLeftTv = new TextView(context);
        mTitleTv = new TextView(context);
        mLeftTv.setTextSize(DEFAULT_TEXT_SIZE);
        mTitleTv.setTextSize(DEFAULT_TEXT_SIZE);
        mLeftTv.setTextColor(DEFAULT_TEXT_COLOR);
        mTitleTv.setTextColor(DEFAULT_TEXT_COLOR);
        mLeftTv.setGravity(Gravity.CENTER);
        mTitleTv.setGravity(Gravity.CENTER);
        mTitleTv.setText("标题");
        mLeftTv.setText("返回");

        mLeftContainer.addView(mLeftTv);
        mCenterContainer.addView(mTitleTv);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        LayoutParams dividerLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mDividerHeight);
        addView(mLeftContainer, params);
        addView(mCenterContainer, params);
        addView(mRightContainer, params);
        addView(mDividerView, dividerLayoutParams);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mLeftContainer.layout(0, mStatusHeight, mLeftContainer.getMeasuredWidth(),
                mLeftContainer.getMeasuredHeight());

        mRightContainer.layout(mScreenWidth- mRightContainer.getMeasuredWidth(),mStatusHeight,
                mScreenWidth, mRightContainer.getMeasuredHeight());

        if (mLeftContainer.getMeasuredWidth() > mRightContainer.getMeasuredWidth()) {
            mCenterContainer.layout(mLeftContainer.getMeasuredWidth(), mStatusHeight,
                    mScreenWidth - mLeftContainer.getMeasuredWidth(), mCenterContainer.getMeasuredHeight());
        } else {
            mCenterContainer.layout(mLeftContainer.getMeasuredWidth(), mStatusHeight,
                    mScreenWidth - mRightContainer.getMeasuredWidth(), mCenterContainer.getMeasuredHeight());
        }
        mDividerView.layout(0,getMeasuredHeight() - mDividerHeight,mScreenWidth,getMeasuredHeight());
    }

    /**
     * 左边文本添加图片
     *
     * @param resId 资源id
     */
    public void setLeftImageResource(int resId,int drawablePadding) {
        mLeftTv.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        mLeftTv.setCompoundDrawablePadding(dip2px(getContext(), drawablePadding));
    }

    public void setLeftTextClickListener(OnClickListener l) {
        mLeftTv.setOnClickListener(l);
    }

    public void setLeftText(CharSequence title) {
        mLeftTv.setText(title);
    }

    public void setLeftText(int resid) {
        mLeftTv.setText(resid);
    }

    public void setLeftTextSize(int spValue) {
        mLeftTv.setTextSize(sp2px(getContext(),spValue));
    }

    public void setLeftTextColor(ColorStateList color) {
        try {
            mLeftTv.setTextColor(color);
        } catch (Exception e) {
        }
    }

    public void setLeftVisible(boolean visible) {
        mLeftTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setLeftView(View view) {
        mLeftContainer.addView(view);
    }

    /**
     * 自定义中间部分布局
     *
     * @param view
     */
    public void setmCenterView(View view) {
        mCenterContainer.addView(view);
    }

    public void setCenterClickListener(OnClickListener l) {
        mCenterContainer.setOnClickListener(l);
    }

    public void setTitle(int resid) {
    }

    public void setTitleColor(int resid) {
        mTitleTv.setTextColor(resid);
    }

    public void setTitleBackground(int resid) {
        mTitleTv.setBackgroundResource(resid);
    }


    public void setTitleTextSize(float titleTextSpValue) {
        mTitleTv.setTextSize(sp2px(getContext(),titleTextSpValue));
    }

    public void setCustomTitle(View titleView) {
        mTitleTv.setVisibility(View.GONE);
        addView(titleView);
    }

    public void setDivider(Drawable drawable) {
        mDividerView.setBackgroundDrawable(drawable);
    }

    public void setDividerColor(int color) {
        mDividerView.setBackgroundColor(color);
    }

    public void setDividerHeight(int dividerHeight) {
        mDividerHeight = dividerHeight;
        mDividerView.getLayoutParams().height = dividerHeight;
    }

    public void setRightView(View view) {
        mRightContainer.addView(view);
    }

    /**
     * 设置沉浸式状态栏，4.4以上系统支持
     * @param isImmersive
     * @param activity
     */
    public void setImmersiveStatus(boolean isImmersive, Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.isImmersive = isImmersive;
            if (isImmersive) {
                mStatusHeight = getStatusHeight();
            }

            if (activity == null) {
                return;
            }
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 将dip或dp值转换为px值
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值
     *
     * @param context
     * @param spValue sp值
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
