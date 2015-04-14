package com.aliasgarlabs.sitmess;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Aliasgar Murtaza on 14-Apr-15.
 */
public class MenuManipulater extends IntentService {

    public static final String SECTION = "section";
    public static final String LIKE = "like";
    public static final String UNLIKE = "unlike";
    public static final String GOING = "going";
    public static final String NOT_GOING = "not_going";
    public static final String GOING_TEXT = "going_text";
    public static final String NOT_GOING_TEXT = "not_going_text";
    public static final String LIKE_TEXT = "like_text";
    public static final String MENU_DATE = "menu_date";
    public static final String UPDATE_ALL = "update";


    public MenuManipulater() {
        super("MenuManipulater");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        final int section = intent.getIntExtra(SECTION, 0);
        final Intent broadcastIntent = new Intent();
        final SharedPreferences value = getSharedPreferences("value", 0);
        final SharedPreferences.Editor val = value.edit();

        Calendar c = Calendar.getInstance();
        Log.d("In manipulator", "Yes, indeed!");
        c.set(Calendar.DAY_OF_MONTH, (c.get(Calendar.DAY_OF_MONTH) + (section - 1)));

        final String date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);

        ParseQuery<MessMenu> query = ParseQuery.getQuery(MessMenu.class);

//        if(!Utilty.isNetworkOnline(getApplicationContext()))
//            query.fromLocalDatastore();
//
        query.findInBackground(new FindCallback<MessMenu>() {
            @Override
            public void done(List<MessMenu> task, ParseException error) {

                if (task != null)
                    for (final MessMenu mess : task) {

                        Log.d("size: ", "" + task.size());
                        Log.d("date back section", "" + section);
                        Log.d("date", "" + date);


                        if (mess.getMenuDate().equals(date)) {
                            boolean flag = false;
                            if (intent.getBooleanExtra(LIKE, false)) {
                                flag = true;
                                mess.setLikes(mess.getLikes() + 1);
                                broadcastIntent.putExtra(UPDATE_ALL, false);
                            } else if (intent.getBooleanExtra(UNLIKE, false)) {
                                flag = true;
                                mess.setLikes(mess.getLikes() - 1);
                                broadcastIntent.putExtra(UPDATE_ALL, false);
                            }


                            if (intent.getBooleanExtra(GOING, false)) {
                                flag = true;
                                mess.setAttendance(mess.getAttendance() + 1);
                                broadcastIntent.putExtra(UPDATE_ALL, true);
                            } else if (intent.getBooleanExtra(NOT_GOING, false)) {
                                flag = true;
                                mess.setAttendance(mess.getAttendance() - 1);
                                broadcastIntent.putExtra(UPDATE_ALL, true);
                            }

                            if (flag)
                                mess.saveEventually();
                            else
                                broadcastIntent.putExtra(UPDATE_ALL, true);

                            String newAttend = "GOING: " + (677 - mess.getAttendance()) + " Students";
                            String newNotAttend = "NOT GOING: " + mess.getAttendance() + " Students";


                            broadcastIntent.setAction(MainActivity.PlaceholderFragment.ResponseReceiver.ACTION_RESP);
                            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                            broadcastIntent.putExtra(GOING_TEXT, newAttend);
                            broadcastIntent.putExtra(NOT_GOING_TEXT, newNotAttend);
                            broadcastIntent.putExtra(LIKE_TEXT, mess.getLikes() + " likes");
                            broadcastIntent.putExtra(MENU_DATE, date);
                            broadcastIntent.putExtra(MainActivity.PlaceholderFragment.ResponseReceiver.ACTION_TYPE, "M");

                            sendBroadcast(broadcastIntent);

                        }
                    }
            }
        });
    }

}

