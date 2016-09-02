package com.games.khelo;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactsListViewAdapter extends BaseAdapter {


    public class ContactsListViewHolder{
        TextView diplayName;
        TextView number;
    }
    Context c;

    public ContactsListViewAdapter(Context context) {
        c=context;
    }

    @Override
    public int getCount() {
        System.out.println(ContactsList.contacts.size());
        return ContactsList.contacts.size();
    }

    @Override
    public Bundle getItem(int i) {
        return ContactsList.contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=li.inflate(R.layout.layout_contactlist,null,false);
        TextView tv= (TextView) view.findViewById(R.id.display_name);
        TextView tv2= (TextView) view.findViewById(R.id.number);
        Bundle b=getItem(i);
        tv.setText(b.getString(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        tv2.setText(b.getString(ContactsContract.CommonDataKinds.Phone.NUMBER));
        return view;
    }
}