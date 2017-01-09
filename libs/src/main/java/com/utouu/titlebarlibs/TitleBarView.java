package com.utouu.titlebarlibs;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 李良海 on 2016/10/14 13:02.
 * Function:定制标题栏
 * Desc:
 */

public class TitleBarView extends LinearLayout implements View.OnClickListener {

    private static final int DEFAULT_TEXT_COLOR = 0XFFFFFFFF;
    private static final int DEFAULT_TEXT_BG_COLOR = 0X00000000;
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
    private int mActionPadding;

    private int mLeftTextSize;
    private int mLeftTextColor;
    private int mLeftTextBackgroundColor;
    private int mLeftDrawable;
    private int mLeftDrawablePadding;
    private int mLeftTextBackgroundResource;

    private int mTitleTextSize;
    private int mTitleTextColor;
    private int mTitleTextBackgroundColor;
    private int mTitleTextBackgroundResource;

    private int mActionTextSize;
    private int mActionTextColor;
    private int mActionTextBackgroundColor;
    private int mActionextBackgroundResource;

    /**
     * 支持沉浸式状态栏
     */
    private boolean isImmersive;

    private boolean isTitleFakeBold;


    private String mTitleText;
    private String mLeftText;


    public TitleBarView(Context context) {
        this(context, null, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttributes(context, attrs);
        init(context);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
        mPaddingValue = ta.getDimensionPixelSize(R.styleable.TitleBarView_llh_outPadding, 0);
        mDividerHeight = ta.getDimensionPixelSize(R.styleable.TitleBarView_llh_dividerHeight, 0);
        mActionPadding = ta.getDimensionPixelSize(R.styleable.TitleBarView_llh_actionPadding, 0);
        isImmersive = ta.getBoolean(R.styleable.TitleBarView_llh_isImmersive, false);
        isTitleFakeBold = ta.getBoolean(R.styleable.TitleBarView_llh_isImmersive, false);

        mLeftText = ta.getString(R.styleable.TitleBarView_llh_leftText);
        mLeftTextSize = ta.getDimensionPixelSize(R.styleable.TitleBarView_llh_leftTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
        mLeftTextColor = ta.getColor(R.styleable.TitleBarView_llh_leftTextColor, DEFAULT_TEXT_COLOR);
        mLeftTextBackgroundResource = ta.getResourceId(R.styleable.TitleBarView_llh_leftTextBackgroundResource, -1);
        mLeftTextBackgroundColor = ta.getColor(R.styleable.TitleBarView_llh_leftTextBackgroundColor, DEFAULT_TEXT_BG_COLOR);
        mLeftDrawable = ta.getResourceId(R.styleable.TitleBarView_llh_leftTextDrawable, -1);
        mLeftDrawablePadding = ta.getDimensionPixelSize(R.styleable.TitleBarView_llh_leftTextDrawablePadding, 0);

        mTitleText = ta.getString(R.styleable.TitleBarView_llh_titleText);
        mTitleTextSize = ta.getDimensionPixelSize(R.styleable.TitleBarView_llh_titleTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
        mTitleTextColor = ta.getColor(R.styleable.TitleBarView_llh_titleTextColor, DEFAULT_TEXT_COLOR);
        mTitleTextBackgroundColor = ta.getColor(R.styleable.TitleBarView_llh_titleTextBackgroundColor, DEFAULT_TEXT_BG_COLOR);
        mTitleTextBackgroundResource = ta.getResourceId(R.styleable.TitleBarView_llh_titleTextBackgroundResource, -1);

        mActionTextSize = ta.getDimensionPixelSize(R.styleable.TitleBarView_llh_actionTextSize, sp2px(context, DEFAULT_TEXT_SIZE));
        mActionTextColor = ta.getColor(R.styleable.TitleBarView_llh_actionTextColor, DEFAULT_TEXT_COLOR);
        mActionTextBackgroundColor = ta.getColor(R.styleable.TitleBarView_llh_actionTextBackgroundColor, DEFAULT_TEXT_BG_COLOR);
        mActionextBackgroundResource = ta.getResourceId(R.styleable.TitleBarView_llh_actionTextBackgroundResource, -1);
    }

    private void init(Context context) {
        if (context instanceof Activity) {
            setImmersiveStatus(isImmersive, (Activity) context);
        }
        mScreenWidth = getScreenWidth();
        initView(context);

    }


    private void initView(Context context) {

        mLeftContainer = new LinearLayout(context);
        mCenterContainer = new LinearLayout(context);
        mRightContainer = new LinearLayout(context);
        mDividerView = new View(context);

        mLeftContainer.setGravity(Gravity.CENTER_VERTICAL);
        mCenterContainer.setGravity(Gravity.CENTER);
        mRightContainer.setGravity(Gravity.CENTER_VERTICAL);
        mLeftContainer.setPadding(mPaddingValue, 0, 0, 0);
        mRightContainer.setPadding(0, 0, mPaddingValue, 0);

        mLeftTv = new TextView(context);
        mLeftTv.setGravity(Gravity.CENTER);
        mLeftTv.setText(mLeftText);
        mLeftTv.setTextSize(px2sp(context, mLeftTextSize));
        mLeftTv.setTextColor(mLeftTextColor);
        mLeftTv.setBackgroundColor(mLeftTextBackgroundColor);
        if (mLeftTextBackgroundResource != -1) {
            mLeftTv.setBackgroundResource(mLeftTextBackgroundResource);
        }
        if (mLeftDrawable != -1) {
            setLeftImageResource(mLeftDrawable, mLeftDrawablePadding);
        }

        mTitleTv = new TextView(context);
        mTitleTv.setGravity(Gravity.CENTER);
        mTitleTv.setText(mTitleText);
        mTitleTv.getPaint().setFakeBoldText(isTitleFakeBold);
        mTitleTv.setTextSize(px2sp(context, mTitleTextSize));
        mTitleTv.setTextColor(mTitleTextColor);
        mTitleTv.setBackgroundColor(mTitleTextBackgroundColor);
        if (mTitleTextBackgroundResource != -1) {
            mTitleTv.setBackgroundResource(mTitleTextBackgroundResource);
        }

        mCenterContainer.addView(mTitleTv);
        mLeftContainer.addView(mLeftTv);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

        LayoutParams dividerParams = new LayoutParams(LayoutParams.MATCH_PARENT, mDividerHeight);

        addView(mLeftContainer, params);//添加左边容器
        addView(mCenterContainer, params);//添加中间容器
        addView(mRightContainer, params);//添加右边容器
        addView(mDividerView, dividerParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //控制子View的位置
        mLeftContainer.layout(0, mStatusHeight, mLeftContainer.getMeasuredWidth(),
                mLeftContainer.getMeasuredHeight());

        mRightContainer.layout(mScreenWidth - mRightContainer.getMeasuredWidth(), mStatusHeight,
                mScreenWidth, mRightContainer.getMeasuredHeight());

        if (mLeftContainer.getMeasuredWidth() > mRightContainer.getMeasuredWidth()) {
            mCenterContainer.layout(mLeftContainer.getMeasuredWidth(), mStatusHeight,
                    mScreenWidth - mLeftContainer.getMeasuredWidth(), mCenterContainer.getMeasuredHeight());
        } else {
            mCenterContainer.layout(mRightContainer.getMeasuredWidth(), mStatusHeight,
                    mScreenWidth - mRightContainer.getMeasuredWidth(), mCenterContainer.getMeasuredHeight());
        }
        mDividerView.layout(0, getMeasuredHeight() - mDividerHeight, mScreenWidth, getMeasuredHeight());
    }

    /**
     * 左边文本添加图片
     *
     * @param resId 资源id
     */
    public void setLeftImageResource(int resId, int drawablePadding) {
        mLeftTv.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        mLeftTv.setCompoundDrawablePadding(dip2px(getContext(), drawablePadding));
    }

    public void setOnLeftTextClickListener(OnClickListener l) {
        mLeftTv.setOnClickListener(l);
    }

    public void setLeftText(CharSequence title) {
        mLeftTv.setText(title);
    }

    public void setLeftText(int resid) {
        mLeftTv.setText(resid);
    }

    public void setLeftTextSize(float spValue) {
        mLeftTv.setTextSize(spValue);
    }

    public void setLeftTextColor(ColorStateList color) {
        try {
            mLeftTv.setTextColor(color);
        } catch (Exception e) {
        }
    }

    public void setLeftTextPadding(int left, int top, int right, int bottom) {
        mLeftTv.setPadding(left, top, right, bottom);
    }

    public void setLeftVisible(boolean visible) {
        mLeftTv.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void addLeftAction(Action action, int position) {
        View view = inflateAction(action);
        mLeftContainer.addView(view, position);
    }

    public View addLeftAction(Action action) {
        View view = inflateAction(action);
        mLeftContainer.addView(view);
        return view;
    }

    /**
     * 自定义中间部分布局
     */
    public void addCenterAction(Action action, int position) {
        View view = inflateAction(action);
        mCenterContainer.addView(view, position);
    }

    /**
     * 自定义中间部分布局
     */
    public void addCenterAction(Action action) {
        View view = inflateAction(action);
        if (view != null) {
            mCenterContainer.addView(view);
        }
    }

    public void setOnCenterClickListener(OnClickListener l) {
        mCenterContainer.setOnClickListener(l);
    }

    public void setTitleText(int resid) {
        mTitleTv.setText(resid);
    }


    public void setTitleText(CharSequence charSequence) {
        mTitleTv.setText(charSequence);
    }

    public void setTitleColor(int resid) {
        mTitleTv.setTextColor(resid);
    }

    public void setTitleBackground(int resid) {
        mTitleTv.setBackgroundResource(resid);
    }

    public void setTitleTextPadding(int left, int top, int right, int bottom) {
        mTitleTv.setPadding(left, top, right, bottom);
    }


    public void setTitleTextSize(float titleTextSpValue) {
        mTitleTv.setTextSize(titleTextSpValue);
    }


    /**
     * 设置粗体标题
     * @param isFakeBold
     */
    public void setTitleFakeBold(boolean isFakeBold) {
        this.isTitleFakeBold = isFakeBold;
    }
    /**
     * 设置下方分割线
     *
     * @param drawable
     */
    public void setDivider(Drawable drawable) {
        mDividerView.setBackgroundDrawable(drawable);
    }

    public void setDividerBackgroundColor(int color) {
        mDividerView.setBackgroundColor(color);
    }

    public void setDividerBackgroundResource(int resourceId) {
        mDividerView.setBackgroundResource(resourceId);
    }

    public void setDividerHeight(int dividerHeight) {
        mDividerHeight = dividerHeight;
        mDividerView.getLayoutParams().height = dividerHeight;
    }

    public void setOutPadding(int paddingValue) {
        mPaddingValue = paddingValue;
        mLeftContainer.setPadding(mPaddingValue, 0, 0, 0);
        mRightContainer.setPadding(0, 0, mPaddingValue, 0);
    }

    /**
     * 通过action加载一个View
     *
     * @param action
     * @return
     */
    private View inflateAction(Action action) {
        View view = null;
        Object obj = action.getContent();
        if (obj == null)
            return null;
        if (obj instanceof View) {
            view = (View) obj;
        } else if (obj instanceof String) {
            TextView text = new TextView(getContext());
            text.setGravity(Gravity.CENTER);
            text.setText((String) obj);
            text.setTextSize(px2sp(getContext(), mActionTextSize));
            text.setTextColor(mActionTextColor);
            text.setBackgroundColor(mActionTextBackgroundColor);
            if (mActionextBackgroundResource != -1) {
                text.setBackgroundResource(mActionextBackgroundResource);
            }
            view = text;
        } else if (obj instanceof Integer) {
            ImageView img = new ImageView(getContext());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setImageResource((Integer) obj);
            view = img;
        }
        view.setPadding(mActionPadding, 0, mActionPadding, 0);
        view.setTag(action);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * 在标题栏右边添加action
     *
     * @param action
     * @param position 添加的位置
     */
    public View addRightAction(Action action, int position) {
        View view = inflateAction(action);
        mRightContainer.addView(view, position);
        return view;
    }

    public View addRightAction(Action action) {
        View view = inflateAction(action);
        mRightContainer.addView(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Action) {
            Action action = (Action) v.getTag();
            action.performance(v);
        }

    }


    /**
     * 添加View以及相应的动作接口
     */
    public interface Action<T> {

        T getContent();

        void performance(View view);
    }


    public static abstract class DefaultAction implements Action {
        @Override
        public void performance(View view) {

        }
    }


    public abstract class ImageAction implements Action<Integer> {

        private int mDrawable;

        public ImageAction(int mDrawable) {
            this.mDrawable = mDrawable;
        }

        @Override
        public Integer getContent() {
            return mDrawable;
        }

    }

    public abstract class TextAction implements Action<String> {

        private String string;

        public TextAction(String string) {
            this.string = string;
        }

        @Override
        public String getContent() {
            return string;
        }
    }


    /**
     * 设置沉浸式状态栏，4.4以上系统支持
     *
     * @param isImmersive
     * @param activity
     */
    public void setImmersiveStatus(boolean isImmersive, Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.isImmersive = isImmersive;
            if (!isImmersive)
                return;
            mStatusHeight = getStatusHeight();

            if (activity == null) {
                return;
            }
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //导航栏透明
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                // 部分机型的statusbar会有半透明的黑色背景
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);// SDK21
            }

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
     *
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
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
