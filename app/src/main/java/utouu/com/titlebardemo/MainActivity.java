package utouu.com.titlebardemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import utouu.com.libs.TitleBarView;

/**
 * Created by 李良海 on 2016/10/11 15:17.
 * Function:
 * Desc:
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TitleBarView mTitleBarView1;
    private TitleBarView mTitleBarView2;
    private TitleBarView mTitleBarView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        mTitleBarView1 = (TitleBarView) findViewById(R.id.titleBarView1);
        mTitleBarView2 = (TitleBarView) findViewById(R.id.titleBarView2);
        mTitleBarView3 = (TitleBarView) findViewById(R.id.titleBarView3);

        mTitleBarView1 = (TitleBarView) findViewById(R.id.titleBarView1);
        mTitleBarView1.setImmersiveStatus(true, this);
        mTitleBarView1.setBackgroundColor(Color.parseColor("#ff2c2d31"));
        mTitleBarView1.setTitleText("标题1");
        mTitleBarView1.setLeftTextSize(16);
        mTitleBarView1.setTitleTextSize(16);
        mTitleBarView1.setLeftText("设置");
        mTitleBarView1.setLeftImageResource(R.drawable.ic_setting, 0);
        mTitleBarView1.setLeftTextClickListener(this);

        View view = View.inflate(this, R.layout.search_layout,null);
        mTitleBarView2.setCenterView(view);
        mTitleBarView2.setBackgroundColor(Color.GRAY);

        mTitleBarView3.setTitleText("标题3");
        mTitleBarView3.setLeftImageResource(R.drawable.ic_back,2);
        mTitleBarView3.setLeftText("返回");
        mTitleBarView3.setLeftTextClickListener(this);
        mTitleBarView3.addAction(new TitleBarView.Action() {
            @Override
            public String getText() {
                return null;
            }

            @Override
            public int getImageRecourse() {
                return R.drawable.ic_setting;
            }

            @Override
            public void performance(View view) {

                Toast.makeText(MainActivity.this, "OnClick", Toast.LENGTH_SHORT).show();

            }
        }, 0);
        mTitleBarView3.setBackgroundColor(Color.parseColor("#ff3366"));
        mTitleBarView3.setDividerHeight(6);
        mTitleBarView3.setDividerColor(Color.GRAY);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "OnClick", Toast.LENGTH_SHORT).show();
    }
}
