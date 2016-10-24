# TitleBarView
--------------------------
#### 一、简介：
**1.1 Gradle集成**<br>
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
```
dependencies {
    compile 'com.github.sandalli:TitleBarLibs:1.0'
}

```

**1.2 功能说明**<br>
自定义标题栏，轻松实现左中右样式标题,并支持沉浸式状态栏。
 
#### 二、截图
![这里是一张截图](https://www.baidu.com/img/bd_logo1.png"截图")
#### 三、使用
**3.1 XML属性**<br>

**3.2 示例代码**<br>
支持沉浸式状态栏
````
        mTitleBarView1.setImmersiveStatus(true,activity);
```
设置左边文字、图片、点击监听以及通过actoin添加控件
```
        mTitleBarView.setLeftImageResource(R.drawable.ic_back, 0);
        mTitleBarView.setLeftText("返回");
        mTitleBarView.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTitleBarView1.addLeftAction(new TitleBarView.Action<String>() {
            @Override
            public String getContent() {
                return "返回";
            }

            @Override
            public void performance(View view) {

            }
        });
```
设置中间标题文字和通过action添加控件
```
        mTitleBarView.setTitleText("标题");
        mTitleBarView.addCenterAction(new TitleBarView.Action<Integer>() {
            @Override
            public Integer getContent() {
                return R.drawable.first_search;
            }

            @Override
            public void performance(View view) {

            }
        });
```
右边添加action
```
            mTitleBarView.addRightAction(new TitleBarView.Action<Integer>() {
            @Override
            public Integer getContent() {
                return R.drawable.first_search;
            }

            @Override
            public void performance(View view) {

            }
        });
```

 
#### 四、关于我 Marno
- 邮箱：13228118026@163.com
