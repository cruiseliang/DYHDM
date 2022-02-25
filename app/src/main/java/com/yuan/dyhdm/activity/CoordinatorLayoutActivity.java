package com.yuan.dyhdm.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;

import com.google.android.material.tabs.TabLayout;
import com.yuan.dyhdm.MyApplication;
import com.yuan.dyhdm.R;
import com.yuan.dyhdm.activity.fragment.PartClassifyFragment;
import com.yuan.dyhdm.adapter.FmPagerAdapter;
import com.yuan.dyhdm.base.BaseActivity;
import com.yuan.dyhdm.utils.StringUtils;
import com.yuan.dyhdm.utils.UtilsLog;
import com.yuan.dyhdm.view.BlurImageView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * created by liangxuedong on 2022/2/23
 *
 * https://www.jianshu.com/p/cd93da2b7a24
 */
public  class CoordinatorLayoutActivity extends BaseActivity {


    TabLayout tablayout;

    ViewPager viewpager;

    Toolbar toolbar;

    ImageView ivBg;

    AppBarLayout appbar;

    ImageView ivUseravator;

    TextView tv1;

    TextView tv2;

    private MagicIndicator mi_tab;
    CommonNavigator commonNavigator;
    private String[] titles = new String[]{"热门", "最新","图片","新闻"};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    CommonNavigatorAdapter commonNavigatorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_coordination);


    }
    @Override
    public void initView() {

        tablayout=findViewById(R.id.tablayout);
         viewpager=findViewById(R.id.viewpager);
         toolbar=findViewById(R.id.toolbar);
         ivBg=findViewById(R.id.iv_bg);
         appbar=findViewById(R.id.appbar);
         ivUseravator=findViewById(R.id.iv_useravator);
         tv1=findViewById(R.id.tv1);

         tv2=findViewById(R.id.tv2);
         mi_tab=findViewById(R.id.mi_tab);
    }

    @Override
    public void registerListener() {
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mi_tab.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mi_tab.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mi_tab.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    public void initData() {
        for (int i = 0; i < titles.length; i++) {
            fragments.add(new PartClassifyFragment());
            tablayout.addTab(tablayout.newTab());
        }


        commonNavigator = new CommonNavigator(mContext);
        commonNavigatorAdapter = new CommonNavigatorAdapter3(mContext, Arrays.asList(titles.clone()));
        commonNavigator.setAdapter(commonNavigatorAdapter);
        mi_tab.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mi_tab, viewpager);


        viewpager.setAdapter(new FmPagerAdapter(fragments, getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewpager);

        for (int j = 0; j < titles.length; j++) {
            tablayout.getTabAt(j).setText(titles[j]);
        }
        tablayout.getTabAt(0);
        tablayout.setSelectedTabIndicatorHeight(StringUtils.dip2px(2));
        tablayout.setTabMode(TabLayout.MODE_FIXED);


        tablayout.setTabTextColors(ContextCompat.getColor(this, R.color.blue_04), ContextCompat.getColor(this, R.color.color_222222));
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.color_222222));
        ViewCompat.setElevation(tablayout, 10);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.internet_star);
        ivBg.setImageBitmap(BlurImageView.BlurImages(bitmap));

        setAvatorChange();
        //监听事件
        //设置字体大小 参考 https://www.jianshu.com/p/b36ced62ea57
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                UtilsLog.e("tab","======我选中了===="+tab.getTag());

                TextView textView = new TextView(MyApplication.mContext);
                float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 18, getResources().getDisplayMetrics());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                textView.setTextColor(getResources().getColor(R.color.color_ff3e60));
                textView.setGravity(Gravity.CENTER);
                textView.setText(tab.getText());
                tab.setCustomView(textView);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
                UtilsLog.i("======我未被选中===="+tab.getTag());
                tab.setCustomView(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
                UtilsLog.i("======我再次被选中===="+tab.getTag());
            }
        });
    }

    @Override
    public void widgetClick(View v) {

    }

    /**
     * 渐变toolbar背景
     */
    private void setAvatorChange() {
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset始终为0以下的负数
                float percent = (Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());

                toolbar.setBackgroundColor(changeAlpha(Color.WHITE,percent));
            }
        });
    }

    /** 根据百分比改变颜色透明度 */
    public int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

//基础布局
    private class CommonNavigatorAdapter2 extends CommonNavigatorAdapter {
        Context mContext2;
        List<String> pageTitles2;

        public CommonNavigatorAdapter2(Context mContext2, List<String> pageTitles2) {
            this.mContext2 = mContext2;
            this.pageTitles2 = pageTitles2;
        }

        @Override
        public int getCount() {
            if (null == pageTitles2) {
                return 0;
            }
            return pageTitles2.size();
        }

        @Override
        public IPagerTitleView getTitleView(Context context,final int i) {
            SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(mContext);
            simplePagerTitleView.setText(pageTitles2.get(i));
            simplePagerTitleView.setNormalColor(mContext.getResources().getColor(R.color.color_222222));
            simplePagerTitleView.setSelectedColor(mContext.getResources().getColor(R.color.color_222222));

//            simplePagerTitleView.setmSelectedTextSize(19);
//            simplePagerTitleView.setmNormalTextSize(16);
//            simplePagerTitleView.setmSelectBlodStyle(true);
//            simplePagerTitleView.setmNormalBlodStyle(false);
            simplePagerTitleView.setIncludeFontPadding(false);

            simplePagerTitleView.setPadding(StringUtils.dip2px(7), 0, StringUtils.dip2px(mContext, 7), 0);


            simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                      viewpager.setCurrentItem(i);


                }
            });


            return simplePagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(mContext);
            indicator.setColors(Color.parseColor("#fff84440"));
            indicator.setLineWidth(StringUtils.dip2px(mContext, 20));
            indicator.setLineHeight(StringUtils.dip2px(mContext, 3));
            indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
            indicator.setRoundRadius(StringUtils.dip2px(mContext, 1.5f));
            return indicator;
        }

        @Override
        public float getTitleWeight(Context context, int index) {
            return 1.0f;
        }
    }

//自定义布局
    private class CommonNavigatorAdapter3 extends CommonNavigatorAdapter {
        Context mContext2;
        List<String> pageTitles2;

        public CommonNavigatorAdapter3(Context mContext2, List<String> pageTitles2) {
            this.mContext2 = mContext2;
            this.pageTitles2 = pageTitles2;
        }

        @Override
        public int getCount() {
            if (null == pageTitles2) {
                return 0;
            }
            return pageTitles2.size();
        }

        @Override
        public IPagerTitleView getTitleView(Context context, final int position) {
            CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
            View customLayout = LayoutInflater.from(context).inflate(R.layout.home_search_pager_title_layout, null);
            final TextView titleText = customLayout.findViewById(R.id.title_text);
            titleText.setText(pageTitles2.get(position));
            titleText.setTextColor(mContext.getResources().getColor(R.color.color_222222));
            titleText.setTextSize(16);
            titleText.setIncludeFontPadding(false);
            commonPagerTitleView.setContentView(customLayout);
            final int MARGIN = 10;//左右边距一般是10,首尾特殊处理
            int left = StringUtils.dip2px(mContext, MARGIN);
            int right = StringUtils.dip2px(mContext, MARGIN);
            //"https://imgwcs1.soufunimg.com/album/2020_03/05/3bfef85b-3102-4346-bbf8-7481a1bf1cd4.gif"
            commonPagerTitleView.setPadding(left, 0, right, 0);
            commonPagerTitleView.setOnClickListener(view -> {
                    viewpager.setCurrentItem(position);
                String eventName="顶部tab-点击"+"-";

            });
            commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                @Override
                public void onSelected(int index, int totalCount) {
                    //UtilsLog.d("chendy", "onSelected index " + index + " totalCount " + totalCount);
                    titleText.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                }

                @Override
                public void onDeselected(int index, int totalCount) {
                    //UtilsLog.d("chendy", "onDeselected index " + index + " totalCount " + totalCount);
                    titleText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                }

                @Override
                public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                    //UtilsLog.d("chendy", "onLeave index " + index + " totalCount " + totalCount + "  leavePercent " + leavePercent + " leftToRight " + leftToRight);
                }

                @Override
                public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                    //UtilsLog.d("chendy", "onEnter index " + index + " totalCount " + totalCount + "  enterPercent " + enterPercent + " leftToRight " + leftToRight);
                }
            });
            return commonPagerTitleView;
        }

        @Override
        public IPagerIndicator getIndicator(Context context) {
            LinePagerIndicator indicator = new LinePagerIndicator(mContext);
            indicator.setColors(Color.parseColor("#fff84440"));
            indicator.setLineWidth(StringUtils.dip2px(mContext, 20));
            indicator.setLineHeight(StringUtils.dip2px(mContext, 3));
            indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
            indicator.setRoundRadius(StringUtils.dip2px(mContext, 1.5f));
            return indicator;
        }

        @Override
        public float getTitleWeight(Context context, int index) {

            if (pageTitles2.get(index).length() > 2) {
                return 1.3f;
            } else {
                return 1.0f;
            }
        }
    }
}
