package com.sujan.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sujan.myapplication.home.CustomerDashbordFragment;
import com.sujan.myapplication.home.SalesDashboardFragment;
import com.sujan.myapplication.home.UserDashboardFragment;
import com.sujan.myapplication.home.ViewPagerAdapter;

public class DashboardMainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_main);
        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setupViewPager(viewPager);
        eventHandling();
    }

    private void eventHandling() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.call:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.contacts:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.favorite:
                        viewPager.setCurrentItem(0);
                        break;
                }
                return false;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SalesDashboardFragment(), "Sales");
        adapter.addFragment(new UserDashboardFragment(), "User");
        adapter.addFragment(new CustomerDashbordFragment(), "Customer");
        viewPager.setAdapter(adapter);
    }
}
