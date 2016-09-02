package com.games.khelo;

import android.*;
import android.Manifest;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static SharedPreferences details;
    public static final String UID="user_id";
    public static final String USER_NAME="username";
    public static final String PHONE="phone";
    ContactsList cl;

    String[] reqPerms=new String[]{
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS
    };

    static int REQUEST_CODE_STORAGE_PERM=445;

    private ViewPager mViewPager;

    @Override
    protected void onStart() {

        Intent i2=getIntent();

        if (i2 != null) {
            if (i2.getBooleanExtra("EXIT", false)) {
                finish();
            }
        }

        details=getPreferences(MODE_PRIVATE);
        assert i2 != null;
        if(!details.contains(USER_NAME) && !i2.getBooleanExtra("EXIT", false))
        {
            Intent i=new Intent(this,LoginActivity.class);
            startActivity(i);
        }
        else if(!details.contains(PHONE) && !i2.getBooleanExtra("EXIT", false))
        {
            Intent i=new Intent(this,PhoneActivity.class);
            startActivity(i);
        }
        int perStatus= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS);
        if(perStatus== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,reqPerms,REQUEST_CODE_STORAGE_PERM);
        }

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        cl=ContactsList.getInstance(this,getLoaderManager());


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewPager.getCurrentItem()==0)
                Snackbar.make(view, "CHATS", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                else
                    Snackbar.make(view, "CONTACTS", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE_STORAGE_PERM)
        {
            if(grantResults.length>0)
            {

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
