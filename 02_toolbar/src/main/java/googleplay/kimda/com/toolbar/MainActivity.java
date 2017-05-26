package googleplay.kimda.com.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //设置toolbar代替ActionBar
        setSupportActionBar(toolbar);


        //菜单填充
//        toolbar.inflateMenu(R.menu.menu_main);
        //菜单点击监听
//        toolbar.setOnMenuItemClickListener( callBack);
    }
}
