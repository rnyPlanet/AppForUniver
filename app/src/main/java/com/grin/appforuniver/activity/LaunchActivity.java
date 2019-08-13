package com.grin.appforuniver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

        String sharedUsername = PreferenceUtils.getUsername(this);
        String sharedUserPassword = PreferenceUtils.getPassword(this);

        if(sharedUsername != null && !sharedUsername.isEmpty() && sharedUserPassword != null && !sharedUserPassword.isEmpty()) {
            LoginUtils.loginUser(sharedUsername, sharedUserPassword, LaunchActivity.this);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
