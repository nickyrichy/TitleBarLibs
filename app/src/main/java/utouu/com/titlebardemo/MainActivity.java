package utouu.com.titlebardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import utouu.com.libs.TitleBar;

/**
 * Created by 李良海 on 2016/10/11 15:17.
 * Function:
 * Desc:
 */
public class MainActivity extends AppCompatActivity {

    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleBar = (TitleBar) findViewById(R.id.titleView);
        titleBar.setImmersive(this, true);
    }
}
