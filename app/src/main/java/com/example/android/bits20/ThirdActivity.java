package com.example.android.bits20;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private String mUsername;
    private boolean mUserIsMale;
    private boolean mUserIsFemale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        SharedPreferences sharedPreferences = getSharedPreferences(SecondActivity.EXTRA_SHARED, MODE_PRIVATE);
        mUsername = sharedPreferences.getString(SecondActivity.EXTRA_NAME, "");
        mUserIsMale = sharedPreferences.getBoolean(SecondActivity.EXTRA_IS_MALE, false);
        mUserIsFemale = sharedPreferences.getBoolean(SecondActivity.EXTRA_IS_FEMALE, false);

        /*TextView textViewOne = findViewById(R.id.welcome_text_view);
        textViewOne.setTextColor(getResources().getColor(R.color.white));
        textViewOne.setText("Greetings "+mUsername +"!");*/

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.alert, null);
        TextView welcomeTextView = findViewById(R.id.welcome);
        alert.setView(view);
        alert.setPositiveButton("Let's do it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.setCancelable(false);
        alert.show();


        mDrawerLayout = findViewById(R.id.third_activity_drawer_layout);
        mNavigationView = findViewById(R.id.third_activity_navigation_view);
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

        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.third_activity_toolbar);
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
                            case R.id.menu_introduction_to_number_system:
                                Intent intent4 = new Intent(getApplicationContext(), IntroductionToNumberSystemActivity.class);
                                startActivity(intent4);
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


    public void onCardViewClicked(View view){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink);
        switch (view.getId()){
            case R.id.first_card_view:
                CardView cardView = findViewById(R.id.first_card_view);
                cardView.startAnimation(animation);
                Intent intent = new Intent(this, IntroductionToNumberSystemActivity.class);
                startActivity(intent);
                break;
            case R.id.second_card_view:
                CardView cardView1 = findViewById(R.id.second_card_view);
                cardView1.startAnimation(animation);
                Intent intent2 = new Intent(this, NumberConversionTutorialActivity.class);
                startActivity(intent2);
                break;
            case R.id.third_card_view:
                CardView cardView2 = findViewById(R.id.third_card_view);
                cardView2.startAnimation(animation);
                Intent intent3 = new Intent(this, QuizActivity.class);
                startActivity(intent3);
                break;
            case R.id.fourth_card_view:
                CardView cardView3 = findViewById(R.id.fourth_card_view);
                cardView3.startAnimation(animation);
                Intent intent4 = new Intent(this, ConversionTabActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
