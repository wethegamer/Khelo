package com.games.khelo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "LoginActivity";
    public static final int RC_SIGN_IN=2401;
    public static final String USER_NAME="username";
    public static final String EMAIL_ID="emailId";
    GoogleSignInAccount account;
    GoogleApiClient apiClient;
    ProgressDialog dialog;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.google_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignIn();

            }
        });


    }

    public void signIn()
    {
        Log.d(TAG, "signIn: ");
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        apiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        Intent signIntent= Auth.GoogleSignInApi.getSignInIntent(apiClient);
        showProgressDialog();
        startActivityForResult(signIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            handleSignInResult(data);
        }

    }

    public void handleSignInResult(Intent data)
    {
        GoogleSignInResult result=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if(result.isSuccess())
        {
            hideProgressDialog();
            account=result.getSignInAccount();
            Toast.makeText(LoginActivity.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor edit=MainActivity.details.edit();
            edit.putString(USER_NAME,account.getDisplayName());
            edit.putString(EMAIL_ID,account.getEmail());
            edit.commit();
            i=new Intent(this,PhoneActivity.class);
            startActivity(i);

        }
        hideProgressDialog();

    }

    public void showProgressDialog()
    {
        if(dialog==null)
        {
            dialog=new ProgressDialog(this);
            dialog.setMessage("Loading");
            dialog.setIndeterminate(true);
        }
        if(!dialog.isShowing())
            dialog.show();
    }
    public void hideProgressDialog()
    {
        if(dialog!=null)
        {
            if(dialog.isShowing())
                dialog.hide();
        }
    }

    public void mSignIn()
    {
        EditText name= (EditText) findViewById(R.id.user_name);
        EditText email=(EditText) findViewById(R.id.email_id);
        String s1=name.getText().toString();
        String s2=email.getText().toString();
        if(s1.isEmpty())
        {
            Toast.makeText(LoginActivity.this, "Enter name first!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(s2.isEmpty() || !s2.contains(".com"))
        {
            Toast.makeText(LoginActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(LoginActivity.this, "Signed in Succesfully!", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor edit=MainActivity.details.edit();
        edit.putString(USER_NAME,s1);
        edit.putString(EMAIL_ID,s2);
        edit.commit();
        i=new Intent(this,PhoneActivity.class);
        startActivity(i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        hideProgressDialog();
        Toast.makeText(LoginActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        Intent i2=new Intent(this,MainActivity.class);
        i2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i2.putExtra("EXIT", true);
        startActivity(i2);

    }
}
