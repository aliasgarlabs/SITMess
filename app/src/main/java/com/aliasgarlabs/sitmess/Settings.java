package com.aliasgarlabs.sitmess;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by Aliasgar Murtaza on 07-Mar-15.
 */
public class Settings extends PreferenceActivity  {


    int count =0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences value = getSharedPreferences("value", 0);
        final SharedPreferences.Editor val = value.edit();
        final SharedPreferences.Editor valPref = preferences.edit();
        final ListPreference lpbf = (ListPreference)findPreference("prebf");
        final ListPreference lplunch = (ListPreference)findPreference("prelunch");
        final ListPreference lpsnack = (ListPreference)findPreference("presnack");
        final ListPreference lpdinner = (ListPreference)findPreference("predinner");


        SwitchPreference sbf = (SwitchPreference) findPreference("sbf");
        SwitchPreference slunch = (SwitchPreference) findPreference("slunch");
        SwitchPreference ssnack = (SwitchPreference) findPreference("ssnack");
        SwitchPreference sdinner = (SwitchPreference) findPreference("sdinner");



        String bf = preferences.getString("prebf", "8:30");
        String lunch = preferences.getString("prelunch", "12:30");
        String snacks = preferences.getString("presnack", "17:00");
        String dinner = preferences.getString("predinner", "20:30");

        Boolean b_bf = preferences.getBoolean("sbf", true);
        Boolean b_lunch = preferences.getBoolean("slunch", true);
        Boolean b_snacks = preferences.getBoolean("ssnack", true);
        Boolean b_dinner = preferences.getBoolean("sdinner", true);

        Log.d(""+b_bf+b_lunch+b_snacks+b_dinner,"Yah");
        Log.d(""+bf+lunch+snacks+dinner,"Yah");

        lpbf.setSummary(preferences.getString("prebf", "8:30"));
        lplunch.setSummary(preferences.getString("prelunch", "12:30"));
        lpsnack.setSummary(preferences.getString("presnack", "17:00"));
        lpdinner.setSummary(preferences.getString("predinner", "20:30"));


        lpbf.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                lpbf.setSummary(newValue.toString());
                val.putString("bf", newValue.toString());
                val.commit();

                return true;
            }
        });




        lplunch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                lplunch.setSummary(newValue.toString());
                val.putString("lunch",newValue.toString());
                val.commit();

                return true;
            }
        });





    lpsnack.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                lpsnack.setSummary(newValue.toString());
                val.putString("snack",newValue.toString());
                val.commit();

                return true;
            }
        });


        lpdinner.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                count++;

                if(count==5)
                {
                    Intent intent = new Intent(getApplicationContext(),MenuSetter.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Entering GOD Mode", Toast.LENGTH_LONG).show();
                    count=0;
                }
                return false;
            }
        });


    lpdinner.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            lpdinner.setSummary(newValue.toString());
            val.putString("dinner",newValue.toString());
            val.commit();




            return true;
        }
    });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar bar;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
            bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);
            root.addView(bar, 0); // insert at top
        } else {
            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
            ListView content = (ListView) root.getChildAt(0);

            root.removeAllViews();

            bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.settings_toolbar, root, false);

            int height;
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            } else {
                height = bar.getHeight();
            }

            content.setPadding(0, height, 0, 0);

            root.addView(content);
            root.addView(bar);
        }

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}

