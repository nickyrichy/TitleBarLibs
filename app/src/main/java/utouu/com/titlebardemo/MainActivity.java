package utouu.com.titlebardemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
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

        mTitleBarView1.setOnLeftTextClickListener(this);


        mTitleBarView2.setLeftText("签到");
        mTitleBarView2.addLeftAction(new TitleBarView.Action<View>() {
            @Override
            public View getContent() {
                return View.inflate(MainActivity.this, R.layout.back_layout, null);
            }

            @Override
            public void performance(View view) {
                Toast.makeText(MainActivity.this, "签到", Toast.LENGTH_SHORT).show();
            }
        }, 0);
        mTitleBarView2.addLeftAction(new TitleBarView.Action<View>() {
            @Override
            public View getContent() {
                View view = new View(MainActivity.this);
                view.setBackgroundColor(Color.GRAY);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(3, LinearLayout.LayoutParams.MATCH_PARENT);
                params.topMargin = 15;
                params.bottomMargin = 15;
                params.rightMargin = 24;
                view.setLayoutParams(params);
                return view;
            }

            @Override
            public void performance(View view) {

            }
        }, 1);

        mTitleBarView2.addRightAction(new TitleBarView.Action<Integer>() {
            @Override
            public Integer getContent() {
                return R.drawable.ic_setting;
            }

            @Override
            public void performance(View view) {
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
            }
        }).setPadding(0,0,24,0);

        mTitleBarView3.addCenterAction(new TitleBarView.Action<View>() {
            @Override
            public View getContent() {
                View view = View.inflate(MainActivity.this, R.layout.search_layout, null);
                return view;
            }

            @Override
            public void performance(View view) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "OnClick", Toast.LENGTH_SHORT).show();
    }

}
