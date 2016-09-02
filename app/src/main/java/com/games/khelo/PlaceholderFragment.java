package com.games.khelo;

import android.app.LoaderManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    ContactsList cl;
    public PlaceholderFragment() {

    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, null, false);
        assert savedInstanceState!=null;
        if(getArguments().getInt(ARG_SECTION_NUMBER)==2)
        {
            cl=ContactsList.getInstanceNullable();
            assert cl!=null;
            cl.displayContacts(rootView);
        }

        return rootView;
    }


}
