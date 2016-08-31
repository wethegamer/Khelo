package com.games.khelo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;


public class CountryCodes extends DialogFragment {

    static OnCodeGotListener og;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setOnCodeGotListener(OnCodeGotListener og)
    {
        this.og=og;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.layout_country_codes,null);
        String[] recourseList=this.getResources().getStringArray(R.array.CountryCodes);
        ListView listView= (ListView) v.findViewById(R.id.country_list);
        listView.setAdapter(new CountriesListAdapter(v.getContext(), recourseList));


        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class CountriesListAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public CountriesListAdapter(Context context, String[] values) {
            super(context, R.layout.country_list_item, values);
            this.context = context;
            this.values = values;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.country_list_item, null, false);
            TextView textView = (TextView) rowView.findViewById(R.id.txtViewCountryName);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.imgViewFlag);

            final String[] g=values[position].split(",");
            textView.setText(GetCountryZipCode(g[1]).trim());
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    og.onGettingCode(Integer.valueOf(g[0]));
                    dismiss();

                }
            });

            String pngName = g[1].trim().toLowerCase();
            imageView.setImageResource(context.getResources().getIdentifier("drawable/" + pngName, null, context.getPackageName()));
            return rowView;
        }


        }

        @NonNull
        private String GetCountryZipCode(String ssid){
            Locale loc = new Locale("", ssid);

            return loc.getDisplayCountry().trim();
        }

        public interface OnCodeGotListener{
            void onGettingCode(int code);
        }



}
