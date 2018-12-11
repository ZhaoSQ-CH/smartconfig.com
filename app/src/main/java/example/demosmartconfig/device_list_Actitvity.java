package example.demosmartconfig;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import example.demosmartconfig.package2.device_Fragment;
import example.demosmartconfig.package2.setup_Fragment;
import example.demosmartconfig.package_tools_javaClass.sj_tabBar_Adapter;

public class device_list_Actitvity extends AppCompatActivity {

    Drawable[] drawables = new Drawable[4];
    Fragment[] pages = new Fragment[2];
    sj_tabBar_Adapter sjTabBarAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list__actitvity);

        ViewPager viewPager = (ViewPager)findViewById(R.id.id_viewPager);
        pages[0] = new device_Fragment();
        pages[1] = new setup_Fragment();
        final MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myViewPagerAdapter);

        drawables[0] = getDrawable(R.drawable.divice_active);
        drawables[1] = getDrawable(R.drawable.device_normal);
        drawables[2] = getDrawable(R.drawable.setup_active);
        drawables[3] = getDrawable(R.drawable.setup_normal);

        GridView gridView = (GridView)findViewById(R.id.id_gridView);
        sjTabBarAdapter = new sj_tabBar_Adapter(device_list_Actitvity.this);
        gridView.setAdapter(sjTabBarAdapter);

        sjTabBarAdapter.addTabBar(addTabBar());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sjTabBarAdapter.setActive(i);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sjTabBarAdapter.setActive(position );
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        sjTabBarAdapter.setActive(0);
        gridView.setNumColumns(2);
    }

    private ArrayList<sj_tabBar_Adapter.Item> addTabBar()
    {
        ArrayList<sj_tabBar_Adapter.Item> tabBars = new ArrayList<>();
        tabBars.add(new sj_tabBar_Adapter.Item("设备", "device",drawables[0],drawables[1],false));
        tabBars.add(new sj_tabBar_Adapter.Item("设置","setup",drawables[2],drawables[3],false));

        return tabBars ;
    }



    public class MyViewPagerAdapter extends FragmentPagerAdapter
    {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pages[position];
        }

        @Override
        public int getCount() {
            return pages.length;
        }
    }
}
