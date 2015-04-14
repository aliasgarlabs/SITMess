package com.aliasgarlabs.sitmess;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Aliasgar Murtaza on 14-Apr-15.
 */
public class MenuGetter extends IntentService {

    public static final String SECTION = "section";
    public static final String REFRESH = "refresh";

    public static final String BREAKFAST = "breakfast";
    public static final String LUNCH = "lunch";
    public static final String SNACKS = "snacks";
    public static final String DINNER = "dinner";
    public static final String LIKES = "likes";
    public static final String LIKED = "liked";
    public static final String ATTEND = "attend";
    public static final String MENU_DATE = "menu_date";
    public static final String SHOW_ALERT = "alert";
    public static final String USER_ATTEND = "user_attend";

    public MenuGetter() {
        super("MenuGetter");
    }

    public MenuGetter(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final int section = intent.getIntExtra(SECTION, 0);

        Boolean refresh = intent.getBooleanExtra(REFRESH, false);
        final Intent broadcastIntent = new Intent();
        final SharedPreferences value = getSharedPreferences("value", 0);
        final SharedPreferences.Editor val = value.edit();

        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_MONTH, (c.get(Calendar.DAY_OF_MONTH) + (section - 1)));

        final String date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);

        ParseQuery<MessMenu> query = ParseQuery.getQuery(MessMenu.class);
        Boolean goingOnline = true;
        Log.d("Validation", "" + " " + Utilty.isNetworkOnline(getApplicationContext()) + " " + refresh + " " + value.getInt("nextUpdate", -1) + " " + c.get(Calendar.WEEK_OF_YEAR));

        if (!refresh && value.getInt("nextUpdate", -1) > c.get(Calendar.WEEK_OF_YEAR)) {
            Log.d("Going Local", "" + true);
            goingOnline = false;
            query.whereEqualTo("date", date);
            query.fromLocalDatastore();

        } else {

            goingOnline = true;
            Log.d("Going Local", "" + false);
            if (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                val.putInt("nextUpdate", (c.get(Calendar.WEEK_OF_YEAR) + 1));
                val.commit();
            }

        }

        if ((Utilty.isNetworkOnline(getApplicationContext()) && goingOnline) || !(goingOnline && !Utilty.isNetworkOnline(getApplicationContext()))) {


            goingOnline = false;


            Log.d("Whatsup", "" + "in query");
            query.findInBackground(new FindCallback<MessMenu>() {
                @Override
                public void done(List<MessMenu> task, ParseException error) {
                    ParseObject.pinAllInBackground(task);


                    if (task != null)
                        for (final MessMenu mess : task) {

                            Log.d("size: ", "" + task.size());
                            Log.d("date back section", "" + section);
                            Log.d("date", "" + date);


                            if (mess.getMenuDate().equals(date)) {

                                broadcastIntent.setAction(MainActivity.PlaceholderFragment.ResponseReceiver.ACTION_RESP);
                                broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                broadcastIntent.putExtra(BREAKFAST, mess.getBreakfast());
                                broadcastIntent.putExtra(LUNCH, mess.getLunch());
                                broadcastIntent.putExtra(SNACKS, mess.getSnack());
                                broadcastIntent.putExtra(DINNER, mess.getDinner());
                                broadcastIntent.putExtra(LIKES, mess.getLikes());
                                broadcastIntent.putExtra(ATTEND, mess.getAttendance());
                                broadcastIntent.putExtra(MENU_DATE, date);
                                broadcastIntent.putExtra(MainActivity.PlaceholderFragment.ResponseReceiver.ACTION_TYPE, "G");


                                if (value.getBoolean(date, false)) {
                                    broadcastIntent.putExtra(LIKED, true);
                                } else {
                                    broadcastIntent.putExtra(LIKED, false);
                                }

                                broadcastIntent.putExtra(SHOW_ALERT, false);

                                sendBroadcast(broadcastIntent);

                            }
                        }
                }
            });
        } else {
            //show alert dialog
            Log.d("Whatsup", "" + "in alert");
            broadcastIntent.setAction(MainActivity.PlaceholderFragment.ResponseReceiver.ACTION_RESP);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(SHOW_ALERT, true);
            broadcastIntent.putExtra(MainActivity.PlaceholderFragment.ResponseReceiver.ACTION_TYPE, "G");
            sendBroadcast(broadcastIntent);

        }


    }
}
