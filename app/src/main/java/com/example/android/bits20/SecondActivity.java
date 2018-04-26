package com.example.android.bits20;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "userName" ;
    public static final String EXTRA_IS_FEMALE = "userIsFemale";
    public static final String EXTRA_IS_MALE = "userIsMale";
    public static final String EXTRA_SHARED = "mySharedPreference";
    private EditText mUsernameEditText;
    private EditText mUserEmailEditText;
    private TextView mTitleTextView;
    private Button mButton;

    private String mUsername;
    private String mUserEmail;
    private boolean mUserIsMale;
    private boolean mUserIsFemale;
    private boolean mUsernameIsEmpty;
    private boolean mUserEmailIsEmpty;
    private RadioGroup mRadioGroup;
    private TextView mCautionTextView;
    private LinearLayout mCautionLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mUsernameEditText = findViewById(R.id.name_edit_text);
        mUserEmailEditText = findViewById(R.id.email_edit_text);
        mTitleTextView = findViewById(R.id.title_second_activity);
        mButton = findViewById(R.id.main_button_one);

        mCautionTextView = findViewById(R.id.caution_message_text_view);
        mCautionLinearLayout = findViewById(R.id.caution_linear_layout);

        TextInputLayout textInputLayout = findViewById(R.id.name_text_input_layout);
        TextInputLayout textInputLayout1 = findViewById(R.id.email_text_input_layout);

        mRadioGroup = findViewById(R.id.second_activity_radio_group);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.male_radio_button:
                        mUserIsMale = true;
                        break;
                    case R.id.female_radio_button:
                        mUserIsFemale = true;
                        break;
                }
            }
        });

        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        Animation moveLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_left);
        Animation slideRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right);

        mTitleTextView.startAnimation(slideDown);
        textInputLayout.startAnimation(slideRight);
        textInputLayout1.startAnimation(moveLeft);
        mRadioGroup.setAnimation(slideRight);
        mButton.setAnimation(moveLeft);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public void toThirdActivity(View view) {

        mUsername = mUsernameEditText.getText().toString();
        mUserEmail = mUserEmailEditText.getText().toString();

        mUsernameIsEmpty = mUsername.isEmpty();
        mUserEmailIsEmpty = mUserEmail.isEmpty();

        SharedPreferences sharedPreferences = getSharedPreferences(EXTRA_SHARED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EXTRA_NAME, mUsername);
        editor.putBoolean(EXTRA_IS_MALE, mUserIsMale);
        editor.putBoolean(EXTRA_IS_FEMALE, mUserIsFemale);
        editor.apply();


        if ((mUserEmailIsEmpty&& mUsernameIsEmpty) && ((!mUserIsMale) && (!mUserIsFemale))){
            mCautionLinearLayout.setVisibility(View.VISIBLE);
            mCautionTextView.setText(R.string.all_reqd);
        }
        else if (mUsernameIsEmpty){
            mCautionLinearLayout.setVisibility(View.VISIBLE);
            mCautionTextView.setText(R.string.name_reqd);
        }
        else if (mUserEmailIsEmpty){
            mCautionLinearLayout.setVisibility(View.VISIBLE);
            mCautionTextView.setText(R.string.email_reqd);
        }
        else if (mUsernameIsEmpty && mUserEmailIsEmpty){
            mCautionLinearLayout.setVisibility(View.VISIBLE);
            mCautionTextView.setText(R.string.name_email_reqd);
        }
        else if ((!mUserIsMale) && (!mUserIsFemale)){
            mCautionLinearLayout.setVisibility(View.VISIBLE);
            mCautionTextView.setText(R.string.sex_reqd);
        }
        else{
            mCautionLinearLayout.setVisibility(View.INVISIBLE);
            mUsername = mUsernameEditText.getText().toString();
            Intent intent = new Intent(this, ThirdActivity.class);

            Boolean isValidEmail = isValidEmail(mUserEmail);
            if (isValidEmail){
                startActivity(intent);
                mUserIsMale = false;
                mUserIsFemale = false;
            }
            else {
                mUserEmailEditText.setError("Invalid email!");
            }
        }
    }

    public final static boolean isValidEmail(String string){
        if (string == null)
            return false;
        else
            return Patterns.EMAIL_ADDRESS.matcher(string).matches();
    }
}
