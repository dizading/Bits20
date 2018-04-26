package com.example.android.bits20;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.drm.DrmStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

public class IntroductionToNumberSystemActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private String mUsername;
    private boolean mUserIsMale;
    private boolean mUserIsFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_to_number_system);

        SharedPreferences sharedPreferences = getSharedPreferences(SecondActivity.EXTRA_SHARED, MODE_PRIVATE);
        mUsername = sharedPreferences.getString(SecondActivity.EXTRA_NAME, "");
        mUserIsMale = sharedPreferences.getBoolean(SecondActivity.EXTRA_IS_MALE, false);
        mUserIsFemale = sharedPreferences.getBoolean(SecondActivity.EXTRA_IS_FEMALE, false);

        mDrawerLayout = findViewById(R.id.introduction_to_number_system_activity_drawer_layout);
        mNavigationView = findViewById(R.id.introduction_to_number_system_activity_navigation_view);
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

        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.introduction_to_number_system_activity_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);

                        switch (item.getItemId()){
                            case R.id.menu_conversion_tutorial:
                                Intent intent1 = new Intent(getApplicationContext(), NumberConversionTutorialActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.menu_quiz:
                                Intent intent2 = new Intent(getApplicationContext(), QuizActivity.class);
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
        switch (item.getItemId()){
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

    public void onTextViewClicked(View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        switch (view.getId()){
            case R.id.what_are_numbers_textview:
                TextView textView = findViewById(R.id.what_are_numbers_textview);
                textView.startAnimation(animation);
                Intent intent = new Intent(this, WhatAreNumbersActivity.class);
                startActivity(intent);
                break;
            case R.id.decimal_number_textview:
                TextView textView1 = findViewById(R.id.decimal_number_textview);
                textView1.startAnimation(animation);
                Intent intent2 = new Intent(this, DecimalActivity.class);
                startActivity(intent2);
                break;
            case R.id.binary_number_textview:
                TextView textView2 = findViewById(R.id.binary_number_textview);
                textView2.startAnimation(animation);
                Intent intent3 = new Intent(this, BinaryActivity.class);
                startActivity(intent3);
                break;
            case R.id.octal_number_textview:
                TextView textView3 = findViewById(R.id.octal_number_textview);
                textView3.startAnimation(animation);
                Intent intent4 = new Intent(this, OctalActivity.class);
                startActivity(intent4);
                break;
            case R.id.hexadecimal_number_textview:
                TextView textView4 = findViewById(R.id.hexadecimal_number_textview);
                textView4.startAnimation(animation);
                Intent intent5 = new Intent(this, HexadecimalActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
