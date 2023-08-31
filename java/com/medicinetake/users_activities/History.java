package com.medicinetake.users_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.medicinetake.R;
import com.medicinetake.database.HistoryDb;
import com.medicinetake.db_tables.Histories;

public class History extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private TabItem tabTook, tabNotYet;

    private int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_history);

        StatusBar();
        /***call methods**/
        initialisation();

        selectItemTab();

    }

    private void StatusBar() {

        if (android.os.Build.VERSION.SDK_INT >= 21) {

            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorStatusHistory));

        }
    }

    public int getIdUser() {
        return id_user;

    }


    /***initialisation******/

    private void initialisation() {

        Intent intent = getIntent();
        id_user = intent.getIntExtra("History Id", -1);

        toolbar = findViewById(R.id.toolbar_history);
        tabLayout = findViewById(R.id.tabLayout);
        tabTook = findViewById(R.id.tab_took);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(toolbar);
        toolbar.setTitle("History");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(fragmentAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.clear_history:

                HistoryDb historyDb = new HistoryDb(this);

                historyDb.OpenDb();
                boolean r = historyDb.clearHistory(id_user);
                if (r) {
                    Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    finish();
                }
                historyDb.CloseDb();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * select item in tabLayout
     **/
    private void selectItemTab() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


}
