package googleplay.kimda.com.androidbar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ActionBar mActionBar;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.btn);

        //ActionBar actionBar = getActionBar();
        //使用v7兼容包,兼容低版本
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("我是标题");// 设置主title部分
        mActionBar.setSubtitle("我是子标题");// 设置子title部分
        mActionBar.setIcon(R.mipmap.ic_launcher);// 设置应用图标
        mActionBar.setDisplayShowTitleEnabled(true);// 设置菜单 标题是否可见
        mActionBar.setDisplayShowHomeEnabled(true);// 设置应用图标是否
        mActionBar.setDisplayUseLogoEnabled(false);// 设置是否显示Logo优先
        mActionBar.setDisplayHomeAsUpEnabled(true);// 设置back按钮是否可见

        //[1]标准模式。默认情况下就是标准模式

        //[2]List模式
       //barListMode();

        //[3]Tab模式
        barTabMode();
        mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //呼出Action Mode功能
                startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        getMenuInflater().inflate(R.menu.main,menu);
                        return true;// 返回true说明有action mode
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return true;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        //点击后关闭
                        mode.finish();
                        return false;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {

                    }
                });

                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this, item.getTitle()+"  click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return super.onOptionsItemSelected(item);
    }

    private void barTabMode() {
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //存储Tab条目的数据,以后操作提供方便
        final List mTabs = new ArrayList<ActionBar.Tab>();
        for (int i = 0; i < 3; i++) {
            //添加3个Tab条目
            ActionBar.Tab tab = mActionBar.newTab();
            tab.setText("tab" + (i + 1));
            tab.setIcon(R.mipmap.ic_launcher);
            mTabs.add(tab);
            //必须设置Tab的监听,不设置监听报错
            tab.setTabListener(new ActionBar.TabListener() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                    Toast.makeText(MainActivity.this, "Select:"+tab.getText(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                    Toast.makeText(MainActivity.this, "unSelect:"+tab.getText(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                    Toast.makeText(MainActivity.this, "Reselected:"+tab.getText(), Toast.LENGTH_SHORT).show();

                }
            });

//将tab添加到actionBar中
            mActionBar.addTab(tab);
        }

    }

    private void barListMode() {
        // actionBar的导航模式
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        //列表数据
        final List objects = new ArrayList();
        objects.add("主页");
        objects.add("新闻");
        objects.add("娱乐");

        //列表数据的适配器
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, objects);

        //设置列表回调: 参数1:数据适配器,参数2:点击监听回调
        mActionBar.setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                //第一次会默认点击了第一个
                Toast.makeText(MainActivity.this, "点击了" + objects.get(itemPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
