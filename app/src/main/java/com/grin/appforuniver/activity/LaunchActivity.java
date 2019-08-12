package com.grin.appforuniver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.grin.appforuniver.R;
import com.grin.appforuniver.data.utils.LoginUtils;
import com.grin.appforuniver.data.utils.PreferenceUtils;


/**
 * boot activity
 */
public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_launch);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.colorWhiteStatusBar));
        }

        String sharedUsername = PreferenceUtils.getUsername(this);
        String sharedUserPassword = PreferenceUtils.getPassword(this);

        if(sharedUsername != null && !sharedUsername.isEmpty() && sharedUserPassword != null && !sharedUserPassword.isEmpty()) {
            LoginUtils.loginUser(sharedUsername, sharedUserPassword, LaunchActivity.this);
            LoginUtils.getMe(LaunchActivity.this);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
