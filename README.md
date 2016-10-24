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
#### 二、预览
![Demo](https://github.com/sandalli/TitleBarLibs/master/demo.gif"预览")
#### 三、使用
**3.1 XML属性**<br>
<table>
<tbody>
<tr><td><em>属性</em></td><td><em>说明</em></td></tr>
<tr><td>outPadding</td><td>标题栏左右控件的Padding</td></tr>
<tr><td>dividerHeight</td><td>分割线高度</td></tr>
<tr><td>actionPadding</td><td>通过action添加控件的间隔</td></tr>
<tr><td>isImmersive</td><td>支持沉浸式状态栏</td></tr>
<tr><td>leftText</td><td>左边文本内容</td></tr>
<tr><td>leftTextBackgroundResource</td><td>通过Resource设置左边文本背景</td></tr>
<tr><td>leftTextBackgroundColor</td><td>左边文本背景颜色</td></tr>
<tr><td>leftTextSize</td><td>左边文本字体大小</td></tr>
<tr><td>leftTextColor</td><td>左边文本字体颜色</td></tr>
<tr><td>leftTextDrawable</td><td>左边图片</td></tr>
<tr><td>leftTextDrawablePadding</td><td>左边图片和文本间隔</td></tr>
<tr><td>titleText</td><td>中间标题文本内容</td></tr>
<tr><td>titleTextSize</td><td>中间标题文本字体大小</td></tr>
<tr><td>titleTextColor</td><td>中间标题文本字体颜色</td></tr>
<tr><td>titleTextBackgroundColor</td><td>中间标题背景颜色</td></tr>
<tr><td>titleTextBackgroundResource</td><td>通过Resource设置中间标题文本背景</td></tr>
<tr><td>actionTextSize</td><td>通过action添加文本时字体大小</td></tr>
<tr><td>actionTextColor</td><td>通过action添加文本时字体颜色</td></tr>
<tr><td>actionTextBackgroundColor</td><td>通过action添加文本时背景颜色</td></tr>
<tr><td>actionTextBackgroundResource</td><td>通过action添加文本时字体并用Resource设置背景</td></tr>
</tbody>
</table>
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
设置下方分割线
```
        mTitleBarView.setDividerHeight(6);
        mTitleBarView.setDividerBackgroundColor(Color.GRAY);
```
#### 四、关于我 Sandal
- 邮箱：13228118026@163.com
