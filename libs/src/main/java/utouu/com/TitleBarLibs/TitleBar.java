package utouu.com.TitleBarLibs;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 李良海 on 2016/10/10 11:25.
 * Function:标题栏控件
 * Desc:
 */

public class TitleBar extends LinearLayout {

    //背景颜色
    private int color = 0XFF2C2D31;

    //标题栏布局
    private View mTitleBarLayout;

    //控件左侧容器
    private LinearLayout mLeftContainer;
    //控件右侧容器
    private LinearLayout mRightContainer;

    //标题
    private TextView mTitleTv;
    //左边文字
    private TextView mLeftTv;

    //左边图片
    private ImageView mLeftIv;
    //右边图片
    private ImageView mRightIv;

    //布局加载器
    private LayoutInflater mInflater;

    //左侧点击监听接口
    private OnLeftClickListener mOnLeftClickListener;

    //左侧点击监听接口
    private OnRightClickListener mOnRightClickListener;

    public void setmOnLeftClickListener(OnLeftClickListener mOnLeftClickListener) {
        this.mOnLeftClickListener = mOnLeftClickListener;
    }

    public void setmOnRightClickListener(OnRightClickListener mOnRightClickListener) {
        this.mOnRightClickListener = mOnRightClickListener;
    }

    public enum TitleBarStyle {//标题样式
        STYLE_JUST_TITLE,//只显示标题
        STYLE_TITLE_LEFT,//显示标题和左边的图片和文本
        STYLE_TITLE__RIGHT,//显示标题和右边的图片
        STYLE_TITLE_ALL//全部显示
    }

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
     * 初始化，加载布局
     *
     * @param context
     */
    private void init(final Context context) {
        mInflater = LayoutInflater.from(context);
        mTitleBarLayout = mInflater.inflate(R.layout.layout_coutermost, null);
        addView(mTitleBarLayout);

        mLeftContainer = (LinearLayout) mTitleBarLayout.findViewById(R.id.llayout_leftContainer);
        mRightContainer = (LinearLayout) mTitleBarLayout.findViewById(R.id.llayout_rightContainer);
        mTitleTv = (TextView) mTitleBarLayout.findViewById(R.id.tv_title);

        //在activity加载完布局之后执行
        post(new Runnable() {
            @Override
            public void run() {
                setColor((Activity) context, color);
            }
        });

    }

    /**
     * 设置标题栏的样式
     *
     * @param titleBarStyle
     */
    public void setStyle(TitleBarStyle titleBarStyle) {
        switch (titleBarStyle) {
            case STYLE_JUST_TITLE:
                clearContainer();
                break;
            case STYLE_TITLE_LEFT:
                clearContainer();
                initLeftContainer();
                break;
            case STYLE_TITLE__RIGHT:
                clearContainer();
                initRightContainer();
                break;
            case STYLE_TITLE_ALL:
                clearContainer();
                initLeftContainer();
                initRightContainer();
                break;
        }

    }

    /**
     * 右侧容器初始化
     */
    private void initRightContainer() {
        View mRightContainerView = mInflater.inflate(R.layout.layout_right_titlebar, null);
        mRightContainer.addView(mRightContainerView);

        mRightIv = (ImageView) mRightContainer.findViewById(R.id.iv_right);

        mRightContainerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调
                if (mOnRightClickListener != null) {
                    mOnRightClickListener.onClick();
                }

            }
        });

    }

    /**
     * 左侧容器初始化
     */
    private void initLeftContainer() {
        View mLeftContainerView = mInflater.inflate(R.layout.layout_left_titlebar, null);
        mLeftContainer.addView(mLeftContainerView);

        mLeftIv = (ImageView) mLeftContainerView.findViewById(R.id.iv_left);
        mLeftTv = (TextView) mLeftContainerView.findViewById(R.id.tv_left);

        mLeftContainerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //回调
                if (mOnLeftClickListener != null) {
                    mOnLeftClickListener.onClick();
                }

            }
        });
    }

    /**
     * 清空两个容器
     */
    private void clearContainer() {
        mLeftContainer.removeAllViews();
        mRightContainer.removeAllViews();
    }

    /**
     * 设置标题显示内容
     *
     * @param title
     */
    public void setTitleText(CharSequence title) {
        if (title != null) {
            mTitleTv.setText(title);
        } else {
            mTitleTv.setVisibility(View.GONE);
        }

    }

    /**
     * 设置标题显字体大小
     *
     * @param size
     */
    public void setTitleTextSize(int size) {
        mTitleTv.setTextSize(size);
    }

    /**
     * 设置标题、左边图片和文字，监听接口实现类
     *
     * @param title    标题
     * @param id       图片id
     * @param listener 回调接口对象
     */
    public void setLeftResource(CharSequence title, CharSequence leftStr, int id, OnLeftClickListener listener) {
        setTitleText(title);
        if (mLeftTv != null && title != null) {
            mLeftTv.setText(leftStr);
        } else if (title == null) {
            mLeftTv.setVisibility(GONE);
        }
        if (mLeftIv != null && id > 0) {
            mLeftIv.setImageResource(id);
            setmOnLeftClickListener(listener);
        }
    }

    /**
     * 设置标题、右边图片和文字，监听接口实现类
     *
     * @param title    标题
     * @param id       图片id
     * @param listener 回调接口对象
     */
    public void setRightResource(CharSequence title, int id, OnRightClickListener listener) {
        setTitleText(title);
        if (mRightIv != null && id > 0) {
            mRightIv.setImageResource(id);
            setmOnRightClickListener(listener);
        }
    }

    /**
     * 设置左边图片的宽高
     *
     * @param width
     * @param height
     */
    public void setLeftImgSize(int width, int height) {
        LayoutParams layoutParams = (LayoutParams) mLeftIv.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        mLeftIv.setLayoutParams(layoutParams);
    }

    /**
     * 设置左边图片的Padding
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setLeftImgPadding(int left, int top, int right, int bottom) {
        mLeftIv.setPadding(left, top, right, bottom);
    }

    /**
     * 设置左边文本的Padding
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setLeftTextPadding(int left, int top, int right, int bottom) {
        mLeftTv.setPadding(left, top, right, bottom);
    }

    /**
     * 设置左边文本字体大小
     *
     * @param size
     */
    public void setLeftTextSize(int size) {
        mLeftTv.setTextSize(size);
    }


    /**
     * 设置左边图片的宽高
     *
     * @param width
     * @param height
     */
    public void setRighrImgSize(int width, int height) {
        LayoutParams layoutParams = (LayoutParams) mRightIv.getLayoutParams();
        layoutParams.height = height;
        layoutParams.width = width;
        mRightIv.setLayoutParams(layoutParams);
    }

    /**
     * 设置右边图片的Padding
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setRightImgPadding(int left, int top, int right, int bottom) {
        mRightIv.setPadding(left, top, right, bottom);
    }

    public void setTitleBackgroundColor(Activity activity, int color) {
        this.color = color;
        setColor(activity, color);
    }

    public interface OnLeftClickListener {
        void onClick();
    }

    public interface OnRightClickListener {
        void onClick();
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    private void setColor(Activity activity, int color) {

        mTitleBarLayout.setBackgroundColor(color);

        //5.0版本以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (activity instanceof AppCompatActivity) {
                AppCompatActivity aty = (AppCompatActivity) activity;
                if (aty.getSupportActionBar() != null) {//隐藏标题栏
                    aty.getSupportActionBar().hide();
                }
                aty.getWindow().setStatusBarColor(color);
                return;
            }
        }

        //4.4以上5.0以下
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }
}
