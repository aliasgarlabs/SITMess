package com.aliasgarlabs.sitmess;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    BroadcastReceiver broadcastReceiver;
    SharedPreferences value;
    SharedPreferences.Editor val;
    ViewPager mViewPager;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Object>() {
            @Override
            public Loader<Object> onCreateLoader(int id, Bundle args) {

                return null;
            }

            @Override
            public void onLoadFinished(Loader<Object> loader, Object data) {

            }

            @Override
            public void onLoaderReset(Loader<Object> loader) {

            }
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();
        Intent intent = new Intent(this,AlarmService.class);
        startService(intent);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                startService(intent);
            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_CHANGED));

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


            Log.d("New lunch",
                    "true");




            value = getSharedPreferences("value",0);
            val = value.edit();

            //Check for 1st launch
            if(value.getBoolean("firstRun", true))
            {

                populateFoodDB();

                val.putBoolean("firstRun", false);
                val.commit();
            }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return (super.onCreateOptionsMenu(menu));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {

            Intent intent = new Intent(this,Settings.class);
            startActivity(intent);

            return true;
        }

        if (id == R.id.about) {

            Intent intent = new Intent(this,About.class);
            startActivity(intent);

            return true;
        }



        return super.onOptionsItemSelected(item);
    }




    private void populateFoodDB() {

            ContentValues values[] = new ContentValues[53];
            int i;
            for(i=0;i<53;i++)
            {
               values[i] = new ContentValues();

            }

            values[0].put(FoodDB.KEY_NAME, "Paneer Kofta");
            values[1].put(FoodDB.KEY_NAME, "Paneer Tikka Masala");
            values[2].put(FoodDB.KEY_NAME, "Paneer Adraki");
            values[3].put(FoodDB.KEY_NAME, "Paneer Toofani");
            values[4].put(FoodDB.KEY_NAME, "Paneer Banjara");
            values[5].put(FoodDB.KEY_NAME, "Palak Paneer");
            values[6].put(FoodDB.KEY_NAME, "Paneer Masala");
            values[7].put(FoodDB.KEY_NAME, "Paneer Butter Masala");
            values[8].put(FoodDB.KEY_NAME, "Paneer Mutter Malai");
            values[9].put(FoodDB.KEY_NAME, "Paneer Aflatun");
            values[10].put(FoodDB.KEY_NAME, "Kadai Paneer");
            values[11].put(FoodDB.KEY_NAME, "Paneer Bhurji");
            values[12].put(FoodDB.KEY_NAME, "Mutter Paneer");
            values[13].put(FoodDB.KEY_NAME, "Chicken Adraki");
            values[14].put(FoodDB.KEY_NAME, "Chicken Hariyali");
            values[15].put(FoodDB.KEY_NAME, "Butter Chicken");
            values[16].put(FoodDB.KEY_NAME, "Butter Chicken Boneless");
            values[17].put(FoodDB.KEY_NAME, "Kadai Chicken");
            values[18].put(FoodDB.KEY_NAME, "Tandoori Chicken");
            values[19].put(FoodDB.KEY_NAME, "Chicken Hyderabadi");
            values[20].put(FoodDB.KEY_NAME, "Chicken Aflatoon");
            values[21].put(FoodDB.KEY_NAME, "Chicken Handi");
            values[22].put(FoodDB.KEY_NAME, "Veg Kolhapuri");
            values[23].put(FoodDB.KEY_NAME, "Veg Kofta");
            values[24].put(FoodDB.KEY_NAME, "Veg 65");
            values[25].put(FoodDB.KEY_NAME, "Mix Veg Paratha");
            values[26].put(FoodDB.KEY_NAME, "Aalu Paratha");
            values[27].put(FoodDB.KEY_NAME, "Stuffed Tomato");
            values[28].put(FoodDB.KEY_NAME, "Stuffed Capsicum");
            values[29].put(FoodDB.KEY_NAME, "Kashmiri Dum Aalu");
            values[30].put(FoodDB.KEY_NAME, "Bengali Dum Aalu");
            values[31].put(FoodDB.KEY_NAME, "Egg Cury");
            values[38].put(FoodDB.KEY_NAME, "Red Sauce Pasta");
            values[39].put(FoodDB.KEY_NAME, "Hakka Noodles");
            values[40].put(FoodDB.KEY_NAME, "Schezwan Noodles");
            values[41].put(FoodDB.KEY_NAME, "Pav Bhaji");
            values[42].put(FoodDB.KEY_NAME, "Papdi Chaat");
            values[43].put(FoodDB.KEY_NAME, "Dahi Chaat");
            values[44].put(FoodDB.KEY_NAME, "Chole Bhature");
            values[45].put(FoodDB.KEY_NAME, "Chole Puri");
            values[46].put(FoodDB.KEY_NAME, "Chhole");
            values[47].put(FoodDB.KEY_NAME, "Veg Manchurian");
            values[48].put(FoodDB.KEY_NAME, "Veg Biryani");
            values[49].put(FoodDB.KEY_NAME, "Hyderabadi Biryani");
            values[50].put(FoodDB.KEY_NAME, "Chicken Biryani");
            values[51].put(FoodDB.KEY_NAME, "Schezwan Fried Rice");
            values[52].put(FoodDB.KEY_NAME, "Supreme Pulav");




            values[0].put(FoodDB.KEY_ADDRESS, "paneer_tikka");
            values[1].put(FoodDB.KEY_ADDRESS, "paneer_tikka");
            values[2].put(FoodDB.KEY_ADDRESS, "paneer_tikka");
            values[3].put(FoodDB.KEY_ADDRESS, "paneer_tikka");
            values[4].put(FoodDB.KEY_ADDRESS, "paneer_tikka");
            values[5].put(FoodDB.KEY_ADDRESS, "paneer_tikka");
            values[6].put(FoodDB.KEY_ADDRESS, "paneer_masala");
            values[7].put(FoodDB.KEY_ADDRESS, "paneer_masala");
            values[8].put(FoodDB.KEY_ADDRESS, "paneer_masala");
            values[9].put(FoodDB.KEY_ADDRESS, "paneer_masala");
            values[10].put(FoodDB.KEY_ADDRESS, "paneer_masala");
            values[11].put(FoodDB.KEY_ADDRESS, "paneer_masala");
            values[12].put(FoodDB.KEY_ADDRESS, "paneer_masala");
            values[13].put(FoodDB.KEY_ADDRESS, "chicken");
            values[14].put(FoodDB.KEY_ADDRESS, "chicken");
            values[15].put(FoodDB.KEY_ADDRESS, "chicken");
            values[16].put(FoodDB.KEY_ADDRESS, "chicken_tikka");
            values[17].put(FoodDB.KEY_ADDRESS, "chicken_tikka");
            values[18].put(FoodDB.KEY_ADDRESS, "chicken_tikka");
            values[19].put(FoodDB.KEY_ADDRESS, "chicken_tikka");
            values[20].put(FoodDB.KEY_ADDRESS, "chicken_tikka");
            values[21].put(FoodDB.KEY_ADDRESS, "chicken_tikka");
            values[22].put(FoodDB.KEY_ADDRESS, "mixveg");
            values[23].put(FoodDB.KEY_ADDRESS, "mixveg");
            values[24].put(FoodDB.KEY_ADDRESS, "mixveg");
            values[25].put(FoodDB.KEY_ADDRESS, "paratha");
            values[26].put(FoodDB.KEY_ADDRESS, "paratha");
            values[27].put(FoodDB.KEY_ADDRESS, "tomato");
            values[28].put(FoodDB.KEY_ADDRESS, "cappsicum");
            values[29].put(FoodDB.KEY_ADDRESS, "dum_aalu");
            values[30].put(FoodDB.KEY_ADDRESS, "dum_aalu");
            values[31].put(FoodDB.KEY_ADDRESS, "egg_curry");
            values[38].put(FoodDB.KEY_ADDRESS, "pasta");
            values[39].put(FoodDB.KEY_ADDRESS, "hakka");
            values[40].put(FoodDB.KEY_ADDRESS, "hakka");
            values[41].put(FoodDB.KEY_ADDRESS, "pav_bhaji");
            values[42].put(FoodDB.KEY_ADDRESS, "chat");
            values[43].put(FoodDB.KEY_ADDRESS, "chat");
            values[44].put(FoodDB.KEY_ADDRESS, "chole");
            values[45].put(FoodDB.KEY_ADDRESS, "chole");
            values[46].put(FoodDB.KEY_ADDRESS, "chole");
            values[47].put(FoodDB.KEY_ADDRESS, "manchurian");
            values[48].put(FoodDB.KEY_ADDRESS, "biryani");
            values[49].put(FoodDB.KEY_ADDRESS, "biryani");
            values[50].put(FoodDB.KEY_ADDRESS, "biryani");
            values[51].put(FoodDB.KEY_ADDRESS, "friedrice");
            values[52].put(FoodDB.KEY_ADDRESS, "friedrice");


            // insert records
            for(i=0;i<53;i++)
            {
                getContentResolver().insert(MyContentProvider.CONTENT_URI, values[i]);

            }





    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);
    }



    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public int daysToSunday() {
        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);
        switch (today) {
            case 1:
                return 1;
            case 2:
                return 7;
            case 3:
                return 6;
            case 4:
                return 5;
            case 5:
                return 4;
            case 6:
                return 3;
            case 7:
                return 2;

        }
        return 7;
    }

    public String getWeekDay(int pos) {

        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_WEEK);

        switch (pos)
        {
            case 0:
                return "Today";

            case 1:
                return "Tomorrow";

        }


        switch (cal.get(Calendar.DAY_OF_WEEK) + pos) {

            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            case 8:
                return "Sunday";


        }

        return "LoopHole";
    }

    public static class PlaceholderFragment extends Fragment {


        private static final String ARG_SECTION_NUMBER = "section_number";
        ImageView food;
        Boolean goingonline = true;
        Calendar cal, c;
        TextView tv_date, tv_day, tv_bf, tv_lunch, tv_snack, tv_dinner, tv_share, tv_rsvp, tv_like, tv_snack_title;
        List<MessMenu> messMenu;
        String date;
        Boolean showAlertNoInternet = false;
        ParseQuery<MessMenu> query;
        int i = 0;
        int section;
        Boolean refresh = false;
        SharedPreferences value;
        SharedPreferences.Editor val;
        Animation fadeIn;
        AnimationSet animation;
        SwipeRefreshLayout swipeLayout;
        String stringMenuToday;
        Typeface tcondi, tcondl, tdate, tday;
        FloatingActionButton fab;

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
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


        }

        public void attendance()
        {

            // get prompts.xml view
            LayoutInflater li = LayoutInflater.from(getActivity());
            View promptsView = li.inflate(R.layout.new_item_attend, null);

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getActivity());

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final TextView notattend = (TextView) promptsView.findViewById(R.id.notattending);
            final TextView attend = (TextView) promptsView.findViewById(R.id.attending);
            final TextView tv_attend = (TextView) promptsView.findViewById(R.id.tv_attending);
            final TextView tv_notattend = (TextView) promptsView
                    .findViewById(R.id.tv_notattending);

            String newAttend = "";
            String newNotAttend = "";

            for (MessMenu oneMenu : messMenu) {
                if (oneMenu.getMenuDate().equals(date)) {
                    oneMenu.setAttendance(oneMenu.getAttendance());
                    newAttend = "GOING: " + (677 - oneMenu.getAttendance()) + " Students";
                    newNotAttend = "NOT GOING: " + oneMenu.getAttendance() + " Students";
                    oneMenu.saveEventually();


                }
            }
            tv_notattend.setText(newNotAttend);
            tv_attend.setText(newAttend);

            // set dialog message
            attend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String newAttend = "";
                    String newNotAttend = "";
                    c = Calendar.getInstance();
                    c.set(Calendar.DAY_OF_MONTH, (c.get(Calendar.DAY_OF_MONTH) + (section - 1)));


                    date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);

                    if (value.getBoolean("attend" + date, true)) {
                        val.putBoolean("attend" + date, false);
                        val.commit();


                        for (MessMenu oneMenu : messMenu) {
                            if (oneMenu.getMenuDate().equals(date)) {
                                oneMenu.setAttendance(oneMenu.getAttendance() + 1);
                                newAttend = "GOING: " + (677 - oneMenu.getAttendance()) + " Students";
                                newNotAttend = "NOT GOING: " + oneMenu.getAttendance() + " Students";
                                oneMenu.saveEventually();


                            }
                        }

                        tv_rsvp.setText("NOT GOING");
                        tv_notattend.setText(newNotAttend);
                        tv_attend.setText(newAttend);

                    }


                }

            });


            notattend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newAttend = "";
                    String newNotAttend = "";
                    c = Calendar.getInstance();
                    c.set(Calendar.DAY_OF_MONTH, (c.get(Calendar.DAY_OF_MONTH) + (section - 1)));


                    date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);
                    if (!value.getBoolean("attend" + date, true)) {
                        val.putBoolean("attend" + date, true);
                        val.commit();


                        for (MessMenu oneMenu : messMenu) {
                            if (oneMenu.getMenuDate().equals(date)) {
                                oneMenu.setAttendance(oneMenu.getAttendance() - 1);
                                newAttend = "GOING: " + (677 - oneMenu.getAttendance()) + " Students";
                                newNotAttend = "NOT GOING: " + oneMenu.getAttendance() + " Students";
                                oneMenu.saveEventually();


                            }
                        }


                        tv_rsvp.setText("GOING");
                        tv_notattend.setText(newNotAttend);
                        tv_attend.setText(newAttend);


                    }
                }

            });


            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        }

        @Override
        public void onResume() {
            super.onResume();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            query = ParseQuery.getQuery(MessMenu.class);

            setHasOptionsMenu(true);

            Bundle args = getArguments();
            section = args.getInt("section_number");


            tcondi = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/RobotoCondensed-Italic.ttf");

            tcondl = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/Roboto-Italic.ttf");
            tdate = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/RobotoCondensed-Light.ttf");

            tday = Typeface.createFromAsset(getActivity().getAssets(),
                    "fonts/RobotoCondensed-LightItalic.ttf");


            swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh = true;
                    getStuff get = new getStuff();
                    get.execute();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeLayout.setRefreshing(false);
                        }
                    }, 6000);
                }
            });


            swipeLayout.setColorScheme(R.color.accent);


            tv_bf = (TextView) rootView.findViewById(R.id.breakfast_desc);
            tv_lunch = (TextView) rootView.findViewById(R.id.lunch_desc);
            tv_snack = (TextView) rootView.findViewById(R.id.snack_desc);
            tv_dinner = (TextView) rootView.findViewById(R.id.dinner_desc);
            tv_share = (TextView) rootView.findViewById(R.id.share);
            tv_snack_title = (TextView) rootView.findViewById(R.id.snack_title);
            tv_like = (TextView) rootView.findViewById(R.id.likes);
            tv_day = (TextView) rootView.findViewById(R.id.day);
            tv_date = (TextView) rootView.findViewById(R.id.date);
            tv_rsvp = (TextView) rootView.findViewById(R.id.rsvp);
            fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
            fab.setColorNormal(getResources().getColor(R.color.white));
            fab.setColorPressed(getResources().getColor(R.color.accent_material_dark));
            food = (ImageView) rootView.findViewById(R.id.image);

            tv_day.setTypeface(tdate);
            tv_date.setTypeface(tday);
            tv_bf.setTypeface(tcondl);
            tv_dinner.setTypeface(tcondl);
            tv_snack.setTypeface(tcondl);
            tv_lunch.setTypeface(tcondl);
            tv_rsvp.setTypeface(tcondi);
            tv_share.setTypeface(tcondi);

            fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(1000);


            animation = new AnimationSet(false); //change to false
            animation.addAnimation(fadeIn);


            tv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(getActivity(), "Please Wait...", Toast.LENGTH_LONG).show();

                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, "MESS MENU\n\nDATE: " + date + "\n\nBREAKFAST\n" + tv_bf.getText() + "\n\nLUNCH\n" + tv_lunch.getText() + "\n\nDINNER\n" + tv_dinner.getText() + "\n\n-Shared by " + getString(R.string.app_name) + "\n\nDownload: bit.ly/symbiapp");
                    startActivity(Intent.createChooser(sharingIntent, "Share using"));

                }
            });

            c = Calendar.getInstance();
            value = getActivity()
                    .getSharedPreferences("value", 0);
            val = value.edit();

            //c.set(Calendar.DAY_OF_WEEK, 7);


            getStuff get = new getStuff();
            get.execute();

            tv_rsvp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (value.getBoolean("synced", false) || isNetworkOnline()) {
                        attendance();

                    } else
                        showAlert();
                }

            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (value.getBoolean("synced", false) || isNetworkOnline()) {


                        if (fab.getColorNormal() == getResources().getColor(R.color.white)) {
                            fab.setColorNormal(getResources().getColor(R.color.accent));
                            fab.setImageResource(R.drawable.ic_action_favorite);
                            Log.d("Status: ", "Liked it!");
                            int like = 0;
                            c = Calendar.getInstance();
                            c.set(Calendar.DAY_OF_MONTH, (c.get(Calendar.DAY_OF_MONTH) + (section - 1)));


                            date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);


                            val.putBoolean(date, true);
                            val.commit();


                            for (MessMenu oneMenu : messMenu) {
                                if (oneMenu.getMenuDate().equals(date)) {
                                    oneMenu.setLikes(oneMenu.getLikes() + 1);
                                    like = oneMenu.getLikes();
                                    oneMenu.saveEventually();


                                }
                            }

                            tv_like.setText("" + like + " likes");


                        } else {
                            fab.setColorNormal(getResources().getColor(R.color.white));
                            fab.setImageResource(R.drawable.ic_action_favorite_green);
                            Log.d("Status: ", "Not Liked it!");
                            int like = 0;
                            c = Calendar.getInstance();
                            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + (section - 1));

                            date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);
                            val.putBoolean(date, false);
                            val.commit();

                            for (MessMenu oneMenu : messMenu) {
                                if (oneMenu.getMenuDate().equals(date)) {
                                    oneMenu.setLikes(oneMenu.getLikes() - 1);
                                    like = oneMenu.getLikes();
                                    oneMenu.saveEventually();


                                }
                            }

                            tv_like.setText("" + like + " likes");

                        }
                    } else
                        showAlert();
                }

            });

            cal = Calendar.getInstance();
            String date = cal.get(Calendar.DAY_OF_MONTH) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.YEAR);
            int today = cal.get(Calendar.DAY_OF_WEEK);
            cal.set(Calendar.DAY_OF_WEEK, today + (section - 1));
            int day = cal.get(Calendar.DAY_OF_WEEK);

            if (day == today)
                tv_day.setText("Today");
            else if (section == 2)
                tv_day.setText("Tomorrow");
            else
                tv_day.setText(getWeekDay(day));


            String monthString = "";

            switch (cal.get(Calendar.MONTH) + 1) {
                case 1:
                    monthString = "January";
                    break;
                case 2:
                    monthString = "Febuary";
                    break;
                case 3:
                    monthString = "March";
                    break;
                case 4:
                    monthString = "April";
                    break;
                case 5:
                    monthString = "May";
                    break;
                case 6:
                    monthString = "June";
                    break;
                case 7:
                    monthString = "July";
                    break;
                case 8:
                    monthString = "August";
                    break;
                case 9:
                    monthString = "September";
                    break;
                case 10:
                    monthString = "October";
                    break;
                case 11:
                    monthString = "November";
                    break;
                case 12:
                    monthString = "December";
                    break;

            }

            tv_date.setText(cal.get(Calendar.DAY_OF_MONTH) + " " + monthString + " " + cal.get(Calendar.YEAR));
            return rootView;
        }

        public String[] getHighlightMenu(String menuToday) {
            ArrayList<String> arrayArgs = new ArrayList<String>();
            menuToday += " ";
            Log.d("Menu Today", "" + menuToday);
            StringTokenizer st = new StringTokenizer(menuToday, "\n");

            arrayArgs.add(st.nextToken());


            Collections.shuffle(arrayArgs);

            String[] selectArgs = new String[arrayArgs.size()];
            selectArgs = arrayArgs.toArray(selectArgs);
            for (String s : selectArgs)
                System.out.println(s);

            return selectArgs;
        }

        public void showAlert() {

            new AlertDialog.Builder(getActivity())

                    .setMessage("NO INTERNET CONNECTION\n\n\n" + "Please check your internet connection.")

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })

                    .show();
            showAlertNoInternet = false;

        }


        public boolean isNetworkOnline() {
            boolean status = false;
            try {

                ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getNetworkInfo(0);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                } else {
                    netInfo = cm.getNetworkInfo(1);
                    if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                        status = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return status;

        }

        public String getWeekDay(int day) {


            switch (day) {
                case 1:
                    return "Sunday";
                case 2:
                    return "Monday";
                case 3:
                    return "Tuesday";
                case 4:
                    return "Wednesday";
                case 5:
                    return "Thursday";
                case 6:
                    return "Friday";
                case 7:
                    return "Saturday";


            }

            return "Today";
        }

        public class getStuff extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected void onPostExecute(String menuToday) {
                super.onPostExecute(menuToday);
                if (showAlertNoInternet) {
                    showAlert();

                }


            }

            @Override
            protected String doInBackground(Void... params) {
                c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, (c.get(Calendar.DAY_OF_MONTH) + (section - 1)));


                date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);
                ParseQuery<MessMenu> query = ParseQuery.getQuery(MessMenu.class);
                Boolean goingOnline = false;
                Log.d("Validation", "" + value.getBoolean("synced", false) + " " + isNetworkOnline() + " " + refresh);

                if (!((value.getBoolean("synced", false) && isNetworkOnline() || refresh)) && (value.getBoolean("synced", false))) {
                    Log.d("Going Local", "" + true);
                    query.whereEqualTo("date", date);
                    query.fromLocalDatastore();

                } else {
                    Log.d("Going Local", "" + false);
                    goingOnline = true;

                }

                Log.d("Getting Before Parse", "" + true);

                if (!isNetworkOnline() && goingOnline) {
                    if (refresh) {
                    showAlertNoInternet = true;
                    swipeLayout.setRefreshing(false);
                        refresh = false;
                    }


                    if ((value.getBoolean("synced", false))) {
                        query.whereEqualTo("date", date);
                        query.fromLocalDatastore();
                    }
                }

                query.findInBackground(new FindCallback<MessMenu>() {
                    @Override
                    public void done(List<MessMenu> task, ParseException error) {
                        ParseObject.pinAllInBackground(task);

                        val.putBoolean("synced", true);
                        val.commit();

                            swipeLayout.setRefreshing(false);

                        if (task != null)
                                for (final MessMenu mess : task) {
                                    messMenu = task;
                                    Log.d("size: ", "" + task.size());
                                    Log.d("date back section", "" + section);
                                    c = Calendar.getInstance();
                                    date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH)) + " - " + c.get(Calendar.YEAR);
                                    Log.d("date back log 1: " + mess.getMenuDate(), date);
                                    c.set(Calendar.DAY_OF_MONTH, (c.get(Calendar.DAY_OF_MONTH) + (section - 1)));
                                    date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH)) + " - " + c.get(Calendar.YEAR);
                                    Log.d("date back log 2: " + mess.getMenuDate(), date);

                                    date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);
                                    Log.d("date back log 3: " + mess.getMenuDate(), date);

                                    if (mess.getMenuDate().equals(date)) {

                                        tv_bf.setText(mess.getBreakfast());
                                        tv_lunch.setText(mess.getLunch());
                                        if (!mess.getSnack().equals(""))
                                            tv_snack.setText(mess.getSnack());
                                        else {
                                            tv_snack.setVisibility(View.GONE);
                                            tv_snack_title.setVisibility(View.GONE);
                                        }
                                        tv_dinner.setText(mess.getDinner());
                                        tv_like.setText(mess.getLikes() + " likes");

                                        if (value.getBoolean(date, false)) {
                                            fab.setColorNormal(getResources().getColor(R.color.accent));
                                            fab.setImageResource(R.drawable.ic_action_favorite);
                                        } else {
                                            fab.setColorNormal(getResources().getColor(R.color.white));
                                            fab.setImageResource(R.drawable.ic_action_favorite_green);
                                        }


                                        stringMenuToday = mess.getDinner();

                                        final String[] selectArgs = getHighlightMenu(stringMenuToday);
                                        Log.d("String Select Arg ", "" + selectArgs[0]);
                                        getLoaderManager().restartLoader(0, null, new android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>() {
                                            @Override
                                            public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
                                                String[] projection = {
                                                        FoodDB.KEY_ID,
                                                        FoodDB.KEY_NAME,
                                                        FoodDB.KEY_ADDRESS};
                                                android.support.v4.content.CursorLoader cursorLoader = new android.support.v4.content.CursorLoader(getActivity(),
                                                        MyContentProvider.CONTENT_URI, projection, FoodDB.KEY_NAME + "=? ", selectArgs, null);

                                                return cursorLoader;
                                            }

                                            @Override
                                            public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
                                                cursor.moveToFirst();
                                                StringBuilder res = new StringBuilder();
                                                while (!cursor.isAfterLast()) {
                                                    res.append(cursor.getString(cursor.getColumnIndex(FoodDB.KEY_ADDRESS)));
                                                    cursor.moveToNext();
                                                }

                                                int resId = getResources().getIdentifier(res.toString(), "drawable", getActivity().getPackageName());
                                                food.setAnimation(animation);

                                                food.setImageResource(resId);


                                                if (food.getDrawable() == null) {

                                                    if (mess.getDinner().toLowerCase().contains("biryani"))
                                                        food.setImageResource(R.drawable.biryani);
                                                    else if (mess.getDinner().toLowerCase().contains("chicken"))
                                                        food.setImageResource(R.drawable.chicken_tikka);
                                                    else if (mess.getDinner().toLowerCase().contains("fried rice"))
                                                        food.setImageResource(R.drawable.friedrice);
                                                    else if (mess.getDinner().toLowerCase().contains("hakka"))
                                                        food.setImageResource(R.drawable.hakka);
                                                    else if (mess.getDinner().toLowerCase().contains("noodles"))
                                                        food.setImageResource(R.drawable.hakka);
                                                    else if (mess.getDinner().toLowerCase().contains("mendu"))
                                                        food.setImageResource(R.drawable.meduwada);
                                                    else if (mess.getDinner().toLowerCase().contains("paneer"))
                                                        food.setImageResource(R.drawable.paneer);
                                                    else if (mess.getDinner().toLowerCase().contains("paratha"))
                                                        food.setImageResource(R.drawable.paratha);
                                                    else if (mess.getDinner().toLowerCase().contains("chole"))
                                                        food.setImageResource(R.drawable.chole);
                                                    else if (mess.getDinner().toLowerCase().contains("egg curry"))
                                                        food.setImageResource(R.drawable.egg_curry);
                                                    else if (mess.getDinner().toLowerCase().contains("gulab"))
                                                        food.setImageResource(R.drawable.gulab_jamun);
                                                    else if (mess.getDinner().toLowerCase().contains("pav bhaji"))
                                                        food.setImageResource(R.drawable.pav_bhaji);
                                                    else if (mess.getBreakfast().toLowerCase().contains("sabudana"))
                                                        food.setImageResource(R.drawable.sabudana_khichdi);
                                                    else if (mess.getBreakfast().toLowerCase().contains("egg burji"))
                                                        food.setImageResource(R.drawable.egg_burji);
                                                    else if (mess.getBreakfast().toLowerCase().contains("mendu"))
                                                        food.setImageResource(R.drawable.meduwada);
                                                    else if (mess.getBreakfast().toLowerCase().contains("paratha"))
                                                        food.setImageResource(R.drawable.paratha);
                                                    else if (mess.getBreakfast().toLowerCase().contains("poha"))
                                                        food.setImageResource(R.drawable.poha);
                                                    else if (mess.getBreakfast().toLowerCase().contains("uttapam"))
                                                        food.setImageResource(R.drawable.uttapam);
                                                    else if (mess.getBreakfast().toLowerCase().contains("french"))
                                                        food.setImageResource(R.drawable.french_toast);
                                                    else if (mess.getBreakfast().toLowerCase().contains("omlette"))
                                                        food.setImageResource(R.drawable.omlette);
                                                    else if (mess.getBreakfast().toLowerCase().contains("samosa"))
                                                        food.setImageResource(R.drawable.samosa);
                                                }

                                            }

                                            @Override
                                            public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

                                            }
                                        });

                                    }
                                }
                        }
                    });

                Log.d("String Menu ", "" + stringMenuToday);
                return stringMenuToday;
            }


        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {


            return daysToSunday();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            return getWeekDay(position);

        }
    }

}
