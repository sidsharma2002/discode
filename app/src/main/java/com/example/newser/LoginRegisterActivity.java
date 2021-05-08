package com.example.newser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LoginRegisterActivity extends AppCompatActivity {

        int AuthUI_requestcode =123;
        TextView welcome;
        Button loginregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcome = findViewById(R.id.welcome_textview);

        if (!(FirebaseAuth.getInstance().getCurrentUser() == null)){
            startActivity(new Intent(this , InboxActivity.class));
            this.finish();
        }

        loginregister = findViewById(R.id.loginregister_button);

        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .playOn(welcome);

        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(loginregister);

        loginregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandleLoginRegister();
            }
        });
    }

    public void HandleLoginRegister(){
        List<AuthUI.IdpConfig> provider = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
        );
        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setTheme(R.style.AppTheme)
                .build();
        startActivityForResult(intent,AuthUI_requestcode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AuthUI_requestcode){
             if(resultCode == RESULT_OK) {
                            //   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Intent intent = new Intent(this ,  InboxActivity.class);
                            startActivity(intent);
                            this.finish();
                }
            }
        }

    }
