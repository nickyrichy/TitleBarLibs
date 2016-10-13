package utouu.com.libs;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by 李良海 on 2016/10/13 08:48.
 * Function:
 * Desc:
 */

public class TitleBar extends ViewGroup implements View.OnClickListener {

    private static final int DEFAULT_MAIN_TEXT_SIZE = 18;//默认的文字大小
    private static final int DEFAULT_SUB_TEXT_SIZE = 14;//默认的小标题文字大小
    private static final int DEFAULT_ACTION_TEXT_SIZE = 14;//默认的新增文本文字大小

    private TextView mLeftTv;//左边文本
    private TextView mTitleTv;//标题
    private TextView mSubTitleTv;//小标题

    private LinearLayout mCenterContainer;//中间容器
    private LinearLayout mRightContainer;//右边容器

    private View mDividerView;//下方分割线

    private boolean isImmersive;//支持沉浸式状态栏

    private int mStatusBarHeight;//状态栏高度
    private int mScreenWidth;//屏幕宽度
    private int mOutPadding;//
    private int mActionPadding;
    private int mActionTextColor;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     */
    private void init(Context context) {
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        if (isImmersive) {
            mStatusBarHeight = getStatusBarHeight();
        }

        mActionPadding = dip2px(5);
        mOutPadding = dip2px(8);
        initView(context);
    }

    /**
     * 加载控件
     */
    private void initView(Context context) {

        setBackgroundColor(Color.GRAY);

        mLeftTv = new TextView(context);
        mCenterContainer = new LinearLayout(context);
        mRightContainer = new LinearLayout(context);
        mDividerView = new View(context);

        mLeftTv.setTextSize(DEFAULT_ACTION_TEXT_SIZE);
        mLeftTv.setSingleLine();
        mLeftTv.setTextColor(Color.WHITE);
        mLeftTv.setGravity(Gravity.CENTER_VERTICAL);
        mLeftTv.setPadding(mOutPadding, 0, mOutPadding, 0);

        mTitleTv = new TextView(context);
        mSubTitleTv = new TextView(context);
        mCenterContainer.addView(mTitleTv);
        mCenterContainer.addView(mSubTitleTv);

        mCenterContainer.setGravity(Gravity.CENTER);
        mTitleTv.setTextSize(DEFAULT_MAIN_TEXT_SIZE);
        mTitleTv.setSingleLine();
        mTitleTv.setTextColor(Color.WHITE);
        mTitleTv.setGravity(Gravity.CENTER);
        mTitleTv.setEllipsize(TextUtils.TruncateAt.END);

        mSubTitleTv.setTextSize(DEFAULT_SUB_TEXT_SIZE);
        mSubTitleTv.setSingleLine();
        mSubTitleTv.setGravity(Gravity.CENTER);
        mSubTitleTv.setTextColor(Color.WHITE);
        mSubTitleTv.setEllipsize(TextUtils.TruncateAt.END);

        mRightContainer.setPadding(mOutPadding, 0, mOutPadding, 0);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

        addView(mLeftTv, layoutParams);
        addView(mCenterContainer);
        addView(mRightContainer, layoutParams);
        addView(mDividerView, new LayoutParams(LayoutParams.MATCH_PARENT, 1));

    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int height = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId != 0) {
            height = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量左边文本大小
        measureChild(mLeftTv, widthMeasureSpec, heightMeasureSpec);
        //测量右边容器大小
        measureChild(mRightContainer, widthMeasureSpec, heightMeasureSpec);
        if (mLeftTv.getMeasuredWidth() > mCenterContainer.getMeasuredWidth()) {//测量中间容器大小
            mCenterContainer.measure(MeasureSpec.makeMeasureSpec(mScreenWidth - 2
                            * mLeftTv.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    heightMeasureSpec);
        } else {
            mCenterContainer.measure(MeasureSpec.makeMeasureSpec(mScreenWidth - 2
                            * mCenterContainer.getMeasuredWidth(), MeasureSpec.EXACTLY),
                    heightMeasureSpec);
        }
        measureChild(mDividerView, widthMeasureSpec, heightMeasureSpec);//测量分割线大小
        //设置测量结果
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec) + mStatusBarHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //左边文本位置
        mLeftTv.layout(0, mStatusBarHeight, mLeftTv.getMeasuredWidth(),
                mLeftTv.getMeasuredHeight() + mStatusBarHeight);
        //右边容器位置
        mRightContainer.layout(mScreenWidth - mRightContainer.getMeasuredWidth(),
                mStatusBarHeight, mScreenWidth,
                mRightContainer.getMeasuredHeight() + mStatusBarHeight);
        if (mLeftTv.getMeasuredWidth() > mRightContainer.getMeasuredWidth()) {
            //左边文本宽度大于右边容器，保证中间容器位置在屏幕中间
            mCenterContainer.layout(mLeftTv.getMeasuredWidth(),
                    mStatusBarHeight,
                    mScreenWidth - mLeftTv.getMeasuredWidth(),
                    getMeasuredHeight());
        } else {
            mCenterContainer.layout(mRightContainer.getMeasuredWidth(),
                    mStatusBarHeight,
                    mScreenWidth - mRightContainer.getMeasuredWidth(),
                    getMeasuredHeight());
        }
        //分割线位置
        mDividerView.layout(0,
                getMeasuredHeight() - mDividerView.getMeasuredHeight(),
                getMeasuredWidth(), getMeasuredHeight());

    }

    /**
     * 设置沉浸式状态栏
     *
     * @param activity
     * @param immersive
     */
    public void setImmersive(Activity activity, boolean immersive) {
        isImmersive = immersive;

        if (isImmersive && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mStatusBarHeight = getStatusBarHeight();
        }
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            window.addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 左边文本添加图片
     *
     * @param resId 资源id
     */
    public void setLeftImageResource(int resId) {
        mLeftTv.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
    }

    public void setLeftClickListener(OnClickListener l) {
        mLeftTv.setOnClickListener(l);
    }

    public void setLeftText(CharSequence title) {
        mLeftTv.setText(title);
    }

    public void setLeftText(int resid) {
        mLeftTv.setText(resid);
    }

    public void setLeftTextSize(int spValue) {
        mLeftTv.setTextSize(sp2px(spValue));
    }

    public void setLeftTextColor(int color) {
        mLeftTv.setTextColor(color);
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

    /**
     * 设置标题和小标题内容，换行隔开的小标题在标题下面，Tab键隔开的小标题在标题的后面
     *
     * @param title
     */
    public void setTitle(CharSequence title) {
        int index = title.toString().indexOf("\n");
        if (index > 0) {
            setTitle(title.subSequence(0, index),
                    title.subSequence(index + 1, title.length()),
                    LinearLayout.VERTICAL);
        } else {
            index = title.toString().indexOf("\t");
            if (index > 0) {
                setTitle(title.subSequence(0, index),
                        " " + title.subSequence(index + 1, title.length()),
                        LinearLayout.HORIZONTAL);
            } else {
                mTitleTv.setText(title);
                mSubTitleTv.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置标题和小标题的内容和方向
     *
     * @param title       标题内容
     * @param subTitle    小标题内容
     * @param orientation 标题和小标题的排列方向
     */
    private void setTitle(CharSequence title, CharSequence subTitle,
                          int orientation) {
        mCenterContainer.setOrientation(orientation);
        mTitleTv.setText(title);

        mSubTitleTv.setText(subTitle);
        mSubTitleTv.setVisibility(View.VISIBLE);
    }

    /**
     * 自定义中间部分布局
     *
     * @param view
     * @param orientation
     */
    public void setmCenterView(View view, int orientation) {
        mCenterContainer.setOrientation(orientation);
        mCenterContainer.addView(view);
    }

    public void setCenterClickListener(OnClickListener l) {
        mCenterContainer.setOnClickListener(l);
    }

    public void setTitle(int resid) {
        setTitle(getResources().getString(resid));
    }

    public void setTitleColor(int resid) {
        mTitleTv.setTextColor(resid);
    }

    public void setTitleBackground(int resid) {
        mTitleTv.setBackgroundResource(resid);
    }

    public void setSubTitleColor(int resid) {
        mSubTitleTv.setTextColor(resid);
    }

    public void setTitleTextSize(int titleTextSpValue, int subTextSpValue) {
        mTitleTv.setTextSize(sp2px(titleTextSpValue));
        mSubTitleTv.setTextSize(sp2px(subTextSpValue));
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
        mDividerView.getLayoutParams().height = dividerHeight;
    }

    /**
     * 批量添加动作
     *
     * @param actionList
     */
    public void addActions(ActionList actionList) {
        int actions = actionList.size();
        for (int i = 0; i < actions; i++) {
            addAction(actionList.get(i));
        }
    }

    /**
     * 在容器添加控件动作
     *
     * @param action
     * @return
     */
    public View addAction(Action action) {
        final int index = mRightContainer.getChildCount();
        return addAction(action, index);
    }

    /**
     * @param action 动作
     * @param index  添加View的的位置
     * @return
     */
    public View addAction(Action action, int index) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        View view = inflateAction(action);
        mRightContainer.addView(view, index, params);
        return view;
    }

    /**
     * 获取要添加的View以及绑定动作
     *
     * @param action
     * @return
     */
    private View inflateAction(Action action) {
        View view = null;
        if (TextUtils.isEmpty(action.getText())) {//添加图片
            ImageView img = new ImageView(getContext());
            img.setImageResource(action.getDrawable());
            view = img;
        } else {//添加文本
            TextView text = new TextView(getContext());
            text.setGravity(Gravity.CENTER);
            text.setText(action.getText());
            text.setTextSize(DEFAULT_ACTION_TEXT_SIZE);
            text.setTextColor(Color.WHITE);
            if (mActionTextColor != 0) {
                text.setTextColor(mActionTextColor);
            }
            view = text;
        }
        view.setPadding(mActionPadding, 0, mActionPadding, 0);
        view.setTag(action);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        final Object tag = v.getTag();
        if (tag instanceof Action) {
            final Action action = (Action) tag;
            action.performAction(v);
        }
    }

    /**
     * 批量移除动作
     */
    public void removeAllActions() {
        mRightContainer.removeAllViews();
    }

    /**
     * 移除制定位置的动作
     *
     * @param index position of action to remove
     */
    public void removeActionAt(int index) {
        mRightContainer.removeViewAt(index);
    }

    /**
     * 移除一个动作
     *
     * @param action The action to remove
     */
    public void removeAction(Action action) {
        int childCount = mRightContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mRightContainer.getChildAt(i);
            if (view != null) {
                final Object tag = view.getTag();
                if (tag instanceof Action && tag.equals(action)) {
                    mRightContainer.removeView(view);
                }
            }
        }
    }

    public void setActionTextColor(int colorResId) {
        mActionTextColor = colorResId;
    }

    /**
     * 返回右边动作个数
     *
     * @return
     */
    public int getActionCount() {
        return mRightContainer.getChildCount();
    }

    /**
     * 用来添加图片或者文本以及相应动作的接口
     */
    public interface Action {
        String getText();

        int getDrawable();

        void performAction(View view);
    }

    public static abstract class ImageAction implements Action {
        private int mDrawable;

        public ImageAction(int drawable) {
            mDrawable = drawable;
        }

        @Override
        public int getDrawable() {
            return mDrawable;
        }

        @Override
        public String getText() {
            return null;
        }
    }

    public static abstract class TextAction implements Action {
        final private String mText;

        public TextAction(String text) {
            mText = text;
        }

        @Override
        public int getDrawable() {
            return 0;
        }

        @Override
        public String getText() {
            return mText;
        }
    }

    /**
     * 动作集合类
     */
    public static class ActionList extends LinkedList<Action> {
    }

    /**
     * dip转像素
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dip2px(int dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
