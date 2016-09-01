package com.games.khelo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneActivity extends AppCompatActivity {

    ServerRequests sr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        sr=ServerRequests.getInstance();
        final TextView tv=(TextView) findViewById(R.id.code);
        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et= (EditText) findViewById(R.id.phone);
                sr.signIn(PhoneActivity.this,et.getText().toString(),tv.getText().toString());
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountryCodes c=new CountryCodes();
                c.setOnCodeGotListener(new CountryCodes.OnCodeGotListener() {
                    @Override
                    public void onGettingCode(int code) {
                        tv.setText("+"+String.valueOf(code));
                    }
                });
                c.show(getSupportFragmentManager(),"CountryCodes");
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

}
