package com.libers.unnc.liber.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.libers.unnc.liber.R;
import com.libers.unnc.liber.fragments.MyBookFragment;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.libers.unnc.liber.R;
import com.libers.unnc.liber.view.ViewPagerIndicator;
//import com.libers.unnc.liber.fragments.FavoriteFragment;  //(BookGrid -> Favorite)
import com.libers.unnc.liber.fragments.BookGridFragment;
import com.libers.unnc.liber.fragments.GuidelistFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // ViewPager
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    // ViewPagerIndicator
    private ViewPagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("GuideList", "My Book", "Favorites");

    // Fragment
    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 顶部ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 右下角浮动菜单
        final FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fabmenu);
        fabMenu.setClosedOnTouchOutside(true);

        // 右下角浮动按钮 - 扫一扫
        final FloatingActionButton fabBtnScanner = (FloatingActionButton) findViewById(R.id.fab_scanner);
        fabBtnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });

        // 右下角浮动按钮 - 添加
        FloatingActionButton fabBtnAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // 左上角 Menu 按钮
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // 菜单
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Bottun -- Login activty
        final Button login_out = (Button) findViewById(R.id.btn_log);
        login_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login_out.getText().toString().equals("Login")){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // ViewPagerIndicator
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        viewPagerIndicator.setTabItemTitles(titles);

        // Fragment
        fragments.add(GuidelistFragment.newInstance(GuidelistFragment.TYPE_ALL));
        fragments.add(MyBookFragment.newInstance("1", "2"));
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE));

        // PagerAdapter
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };

        // 设置数据适配器
        viewPager.setAdapter(pagerAdapter);
        viewPagerIndicator.setViewPager(viewPager, 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_scanner) {
            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("search_type", SearchActivity.SEARCH_NET);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("search_type", SearchActivity.SEARCH_LOCAL);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
