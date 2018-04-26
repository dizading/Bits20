package com.example.android.bits20;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String mUsername;
    private boolean mUserIsMale;
    private boolean mUserIsFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        SharedPreferences sharedPreferences = getSharedPreferences(SecondActivity.EXTRA_SHARED, MODE_PRIVATE);
        mUsername = sharedPreferences.getString(SecondActivity.EXTRA_NAME, "");
        mUserIsMale = sharedPreferences.getBoolean(SecondActivity.EXTRA_IS_MALE, false);
        mUserIsFemale = sharedPreferences.getBoolean(SecondActivity.EXTRA_IS_FEMALE, false);

        mDrawerLayout = findViewById(R.id.quiz_activity_drawer_layout);
        mNavigationView = findViewById(R.id.quiz_activity_navigation_view);
        View headerView = mNavigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.drawer_header_text_view);
        textView.setText(mUsername);
        ImageView imageView = headerView.findViewById(R.id.gender_image_view);
        if (mUserIsMale){
            imageView.setImageResource(R.drawable.male);
        }
        else if (mUserIsFemale){
            imageView.setImageResource(R.drawable.student);
        }

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyOwnPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.quiz_activity_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);

                        switch (item.getItemId()) {
                            case R.id.menu_conversion_tutorial:
                                Intent intent1 = new Intent(getApplicationContext(), NumberConversionTutorialActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.menu_introduction_to_number_system:
                                Intent intent2 = new Intent(getApplicationContext(), IntroductionToNumberSystemActivity.class);
                                startActivity(intent2);
                                break;
                            case R.id.menu_conversion_tab:
                                Intent intent3 = new Intent(getApplicationContext(), ConversionTabActivity.class);
                                startActivity(intent3);
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.options_menu_exit:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("EXIT");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent closeApp = new Intent(Intent.ACTION_MAIN);
                        closeApp.addCategory(Intent.CATEGORY_HOME);
                        closeApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(closeApp);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit_options_menu, menu);
        return true;
    }

    class MyOwnPagerAdapter extends FragmentPagerAdapter {

        public MyOwnPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new EasyFragment();
            }
            if (position == 1) {
                return new ModerateFragment();
            }
            if (position == 2) {
                return new AdvancedFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return (getResources().getStringArray(R.array.tab_title).length);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tab_title)[position];
        }
    }
}
