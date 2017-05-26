package googleplay.kimda.com.googleplay.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.fragments.BaseFragment;
import googleplay.kimda.com.googleplay.fragments.FragmentFactory;
import googleplay.kimda.com.googleplay.utils.UiUtils;

import static googleplay.kimda.com.googleplay.R.id.drawer;


public class MainActivity extends ActionBarActivity {

    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.vp)
    ViewPager mViewPager;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private String[] mStringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDrawer();
        initViewPager();

    }


    /**
     * 主页面和ViewPager
     * 指示器design控件的TabLayout
     * tabMode  tab的模式，可滚动
     */
    private void initViewPager() {
        //指示器数据
        mStringArray = UiUtils.getStringArray(R.array.pagers);
        //横向滚动
        mTablayout.setTabMode(TabLayout.SCROLL_AXIS_NONE);
        mViewPager.setAdapter(mAdapter);

        //指示器关联Viewpager
        mTablayout.setupWithViewPager(mViewPager);
    }

    private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return mStringArray.length;
        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public TextView instantiateItem(ViewGroup container, int position) {
//            TextView textView = new TextView(UiUtils.getContext());
//            textView.setGravity(Gravity.CENTER);//居中
//            textView.setText(mStringArray[position]);
//            textView.setTextColor(Color.BLACK);
//            container.addView(textView);
//            return textView;
//        }

        @Override
        public Fragment getItem(int position) {
            //使用FragmentFactory  Fragment工厂加载Fragment
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        //让指示器获取到ViewPager标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mStringArray[position];
        }
    };

    /**
     * ToolBar和抽屉
     */
    private void initDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(mToolbar);
        //抽屉的代码
        mDrawer = (DrawerLayout) findViewById(drawer);
        //1.初始化开关(连接actionbar和抽屉)//DrawerLayout控件和抽屉控件要一起关联
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //2.给抽屉设置监听（开关状态）
        mDrawer.setDrawerListener(toggle);
        //3.让开关同步状态
        toggle.syncState();
    }
}
