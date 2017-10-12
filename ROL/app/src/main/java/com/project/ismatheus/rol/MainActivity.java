package com.project.ismatheus.rol;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener{

    private ImageView mRolLogoImageView;
    private ImageView mRolCoverImageView;
    private EditText mEmailEditText;
    private EditText mPswEditText;
    private TextView mLangTextView;
    private TextView mForgotPswTextView;
    private Button mLoginButton;
    private Button mNewAccountButton;
    private ImageView mRolStaticLogoImageView;

    private boolean ANIMATION_ENDED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRolLogoImageView = (ImageView)findViewById(R.id.rolLogoImageView);
        mRolCoverImageView = (ImageView)findViewById(R.id.rolCoverImageView);
        mEmailEditText = (EditText)findViewById(R.id.emailEditText);
        mPswEditText = (EditText)findViewById(R.id.pswEditText);
        mLangTextView = (TextView)findViewById(R.id.langTextView);
        mForgotPswTextView = (TextView)findViewById(R.id.forgotPswTextView);
        mLoginButton = (Button)findViewById(R.id.loginButton);
        mNewAccountButton = (Button)findViewById(R.id.newAccountButton);
        mRolStaticLogoImageView = (ImageView)findViewById(R.id.rolLogoStaticImageView);

        mRolCoverImageView.setVisibility(View.GONE);
        mEmailEditText.setVisibility(View.GONE);
        mPswEditText.setVisibility(View.GONE);
        mLangTextView.setVisibility(View.GONE);
        mForgotPswTextView.setVisibility(View.GONE);
        mLoginButton.setVisibility(View.GONE);
        mNewAccountButton.setVisibility(View.GONE);
        mRolStaticLogoImageView.setVisibility(View.GONE);

        Animation moveRolLogoAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_rol_logo);
        moveRolLogoAnimation.setFillAfter(true);
        moveRolLogoAnimation.setAnimationListener(this);
        mRolLogoImageView.startAnimation(moveRolLogoAnimation);

        final View activityRootView = findViewById(R.id.mainConstraintLayout);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(ANIMATION_ENDED) {
                    int heigthDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                    if (heigthDiff > dpToPx(MainActivity.this, 200)) {
                        //tela com teclado
                        mRolCoverImageView.setVisibility(View.GONE);
                        mLangTextView.setVisibility(View.GONE);
                        mForgotPswTextView.setVisibility(View.GONE);
                    } else {
                        //sem teclado
                        mRolCoverImageView.setVisibility(View.VISIBLE);
                        mLangTextView.setVisibility(View.VISIBLE);
                        mForgotPswTextView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mRolStaticLogoImageView.setVisibility(View.VISIBLE);
        mRolLogoImageView.clearAnimation();
        mRolLogoImageView.setVisibility(View.GONE);

        mEmailEditText.setAlpha(0f);
        mEmailEditText.setVisibility(View.VISIBLE);

        mPswEditText.setAlpha(0f);
        mPswEditText.setVisibility(View.VISIBLE);

        mLangTextView.setAlpha(0f);
        mLangTextView.setVisibility(View.VISIBLE);

        mForgotPswTextView.setAlpha(0f);
        mForgotPswTextView.setVisibility(View.VISIBLE);

        mLoginButton.setAlpha(0f);
        mLoginButton.setVisibility(View.VISIBLE);

        mNewAccountButton.setAlpha(0f);
        mNewAccountButton.setVisibility(View.VISIBLE);

        mRolCoverImageView.setAlpha(0f);
        mRolCoverImageView.setVisibility(View.VISIBLE);

        int mediumAnimationTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        mEmailEditText.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

        mPswEditText.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

        mLangTextView.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

        mForgotPswTextView.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

        mLoginButton.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

        mNewAccountButton.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

        mRolCoverImageView.animate()
                .alpha(1f)
                .setDuration(mediumAnimationTime)
                .setListener(null);

        ANIMATION_ENDED = true;
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public static float dpToPx(Context context, float valueInDp){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
}
