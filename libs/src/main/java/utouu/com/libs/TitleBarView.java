package utouu.com.libs;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
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
    private int mActionPadding;
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
        mPaddingValue = dip2px(context, 10);
        mActionPadding = dip2px(context, 6);
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
        mLeftContainer.setPadding(mPaddingValue, 0, 0, 0);
        mRightContainer.setPadding(0, 0, mPaddingValue, 0);

        mLeftTv = new TextView(context);
        mTitleTv = new TextView(context);
        mLeftTv.setTextSize(DEFAULT_TEXT_SIZE);
        mTitleTv.setTextSize(DEFAULT_TEXT_SIZE);
        mLeftTv.setTextColor(DEFAULT_TEXT_COLOR);
        mTitleTv.setTextColor(DEFAULT_TEXT_COLOR);
        mLeftTv.setGravity(Gravity.CENTER);
        mTitleTv.setGravity(Gravity.CENTER);

        mLeftContainer.addView(mLeftTv);
        mCenterContainer.addView(mTitleTv);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        LayoutParams dividerLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);

        addView(mLeftContainer, params);//添加左边容器
        addView(mCenterContainer, params);//添加中间容器
        addView(mRightContainer, params);//添加右边容器
        addView(mDividerView, dividerLayoutParams);

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

    public void setLeftTextClickListener(OnClickListener l) {
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
    public void setCenterView(View view) {
        mCenterContainer.addView(view);
    }

    public void setCenterClickListener(OnClickListener l) {
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


    public void setTitleTextSize(float titleTextSpValue) {
        mTitleTv.setTextSize(titleTextSpValue);
    }

    /**
     * 设置下方分割线
     *
     * @param drawable
     */
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

    /**
     * 在标题栏右边添加action
     * @param action
     * @param position 添加的位置
     */
    public void addAction(Action action, int position) {
        View view = inflateAction(action);
        mRightContainer.addView(view, position);
    }

    /**
     * 通过action加载一个View
     * @param action
     * @return
     */
    private View inflateAction(Action action) {
        View view = null;
        if (TextUtils.isEmpty(action.getText())) {
            ImageView img = new ImageView(getContext());
            img.setImageResource(action.getImageRecourse());
            view = img;
        } else {
            TextView text = new TextView(getContext());
            text.setGravity(Gravity.CENTER);
            text.setText(action.getText());
            text.setTextSize(DEFAULT_TEXT_SIZE);
            text.setTextColor(DEFAULT_TEXT_COLOR);
            view = text;
        }

        view.setPadding(mActionPadding, 0, mActionPadding, 0);
        view.setTag(action);
        view.setOnClickListener(this);
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
     * 添加TextView或者ImageView以及相应的动作接口
     */
    public interface Action {
        String getText();

        int getImageRecourse();

        void performance(View view);

    }

    public abstract class ImageAction implements Action {

        private int mDrawable;

        public ImageAction(int mDrawable) {
            this.mDrawable = mDrawable;
        }

        @Override
        public String getText() {
            return null;
        }

        @Override
        public int getImageRecourse() {
            return mDrawable;
        }

    }

    public abstract class TextAction implements Action {

        private String string;

        public TextAction(String string) {
            this.string = string;
        }

        @Override
        public String getText() {
            return string;
        }

        @Override
        public int getImageRecourse() {
            return 0;
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
}
