package com.games.khelo;

import android.*;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class ContactsList implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "ContactsList";
    public static final int DETAILS_QUERY_ID = 0;
    private static final String SELECTION = ContactsContract.Data.LOOKUP_KEY + " = ?";
    private String[] mSelectionArgs = { "" };
    private static final String SORT_ORDER = ContactsContract.Contacts.Data.MIMETYPE;
    private String mLookupKey;
    static ContactsListViewAdapter adapter;
    static ArrayList<Bundle>contacts;
    LoaderManager l;
    Context c;
    static ContactsList cl=null;

    private static final String[] PROJECTION =
            new String[]{
                    ContactsContract.CommonDataKinds.Phone._ID,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            };

    public static ContactsList getInstance(Context c,LoaderManager loaderManager){
        contacts=new ArrayList<>();
        if(cl==null)
            cl=new ContactsList(c,loaderManager);
        return cl;
    }
    public static ContactsList getInstanceNullable(){
        return cl;
    }

    private ContactsList(Context c,LoaderManager loaderManager) {
        this.c = c;
        l=loaderManager;
    }

    public void displayContacts(View v)
    {
        ListView lv= (ListView) v.findViewById(R.id.chatlist);
        adapter=new ContactsListViewAdapter(c);
        lv.setAdapter(adapter);
        l.initLoader(DETAILS_QUERY_ID, null,this);
        adapter.notifyDataSetChanged();
    }


    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {


        return new CursorLoader(
                                c,
                                ContactsContract.Data.CONTENT_URI,
                                PROJECTION,
                                SELECTION,
                                mSelectionArgs,
                                SORT_ORDER
                        );
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

        Log.d(TAG, "onLoadFinished:Last "+cursor.isAfterLast());
        while (cursor.moveToNext())
        {
            Bundle b=new Bundle();
            b.putString(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            b.putString(ContactsContract.CommonDataKinds.Phone.NUMBER,cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            contacts.add(b);
            adapter.notifyDataSetChanged();
        }
        cursor.close();

    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
    }

}
