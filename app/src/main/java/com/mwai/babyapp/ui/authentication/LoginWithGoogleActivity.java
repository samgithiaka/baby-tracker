package com.mwai.babyapp.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.mwai.babyapp.R;
import com.mwai.babyapp.app.BaseActivity;
import com.mwai.babyapp.databinding.ActivityLoginWithGoogleBinding;
import com.mwai.babyapp.ui.main.MainActivity;

import java.util.Objects;

public class LoginWithGoogleActivity extends BaseActivity {
    ActivityLoginWithGoogleBinding binding;
    public FirebaseAuth mAuth;
    public FirebaseUser mFirebaseUser;
    private static final String TAG = "LoginWithGoogleActivity";
    private GoogleSignInClient mGoogleSignInClient;

    public static final int REQUEST_SIGN_IN         = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding = DataBindingUtil.setContentView(activityContext,
              R.layout.activity_login_with_google);
        mAuth = FirebaseAuth.getInstance();


      setOnClickListeners();
    }

    public void setDefaultWebClientId() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(activityContext, gso);
    }

    private void setOnClickListeners() {
        binding.btnGoogle.setOnClickListener(v -> proceedToAuthenticate());
    }

    private void proceedToAuthenticate() {
        firebaseGoogleSignIn();
        setDefaultWebClientId();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == REQUEST_SIGN_IN) {
                firebaseSignInActivityResult(data);
            }
        }catch (Exception e){
            Log.e(TAG, "onActivityResult: " ,e );
        }
    }

    /**
     * Sign using Firebase Google
     */
    public void firebaseGoogleSignIn() {
        try {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            activityContext.startActivityForResult(signInIntent, REQUEST_SIGN_IN);
            //Constants.callDialog(activityContext, "Please wait...");
        }catch (Exception e){
            Log.e(TAG, "firebaseGoogleSignIn: ", e);
        }
    }

    /**
     * Sign using Firebase Google
     * @param data Data received from the intent after sign in
     */
    public void firebaseSignInActivityResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);

            firebaseAuthWithGoogle(Objects.requireNonNull(account));
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.e(TAG, "Google sign in failed", e.fillInStackTrace());
            Toast.makeText(activityContext, "Authentication failed.", Toast.LENGTH_SHORT).show();
            //Constants.cancelDialog();
        }
    }

    /**
     * Authenticate with Google
     * @param acct Google account to be used
     */
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        try {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(activityContext, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            mFirebaseUser = mAuth.getCurrentUser();
                            Toast.makeText(activityContext, "Signed in as " +
                                    mFirebaseUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activityContext, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            //Constants.showToast(activityContext, "Authentication Failed. ");
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    });
        }catch (Exception e){
            Log.e(TAG, "firebaseAuthWithGoogle: ",e );
        }
    }
}