package com.example.android.capstone;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.android.androidlibrary.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String USER_KEY = "userkey";
    private static final String USER_SIGN_CLIENT = "userSignInClient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUIGoogle(account);

    }

    private void signIn(){
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                updateUIGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
    }

    @OnClick (R.id.btn_signin_google)
    public void onClick(View view) {
        signIn();
    }

    private void updateUIGoogle(GoogleSignInAccount account){
        if (account != null){
            User user = new User();
            user.setUsername(account.getGivenName());
            user.setEmail(account.getEmail());
            user.setPhotoURL(String.valueOf(account.getPhotoUrl()));

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(USER_KEY, account);
            startActivity(intent);
        }
    }

    public static GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }
}
