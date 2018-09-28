package xyz.manzodev.themanzocoffee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import xyz.manzodev.themanzocoffee.Models.User;
import xyz.manzodev.themanzocoffee.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    CallbackManager callbackManager;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 6789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor et = sp.edit();
        et.clear();
        et.commit();

        //FacebookLogin
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        activityLoginBinding.btnFbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
            }
        });

        //GoogleLogin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        activityLoginBinding.btnGLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                handleGoogleAccess(account);

            } catch (ApiException e) {
                //Todo : Handle Google sign in fail
                //Sign in Fail
                e.printStackTrace();
            }
        }

    }

    private void handleGoogleAccess(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Boolean b = task.getResult().getAdditionalUserInfo().isNewUser();
                    checkUser(user,b);
                }
                else{
                    //Todo : Handle sign in failed
                }
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {

        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Boolean b = task.getResult().getAdditionalUserInfo().isNewUser();
                    checkUser(user,b);
                }
                else{
                    //Todo : Handle Facebook sign in Fail
                    //Login Fail
                }
            }
        });

    }

    private void login(String userID) {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra(getString(R.string.userID),userID);
        startActivity(intent);
    }

    private void checkUser(FirebaseUser user,Boolean isNewUser){
        String uid = user.getUid();
        if (isNewUser){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

            String name = user.getDisplayName();
            String img = user.getPhotoUrl().toString();
            User u = new User(name,img,null,null,null);
            databaseReference.child(getString(R.string.user)).child(uid).setValue(u);

            login(uid);

        }
        else  login(uid);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser!=null){
            login(currentUser.getUid());}
    }
}
