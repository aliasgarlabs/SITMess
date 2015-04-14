package com.aliasgarlabs.sitmess;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

/**
* Created by Aliasgar Murtaza on 1/7/2015.
*/
public class AlarmService extends Service {
    Calendar c = null;
    String nextMeal;
    int times[]= new int[4];
    int timesRemaining[]= new int[4];
    SharedPreferences value;
    SharedPreferences.Editor val;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        c = Calendar.getInstance();
       value = getApplicationContext()
                .getSharedPreferences("value", 0);
        val = value.edit();
        boolean alreadyNotified = value.getString("notificationTimeStamp","").equals("" + c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.HOUR_OF_DAY) + "-" + c.get(Calendar.MINUTE));
        boolean isTimeBoolean = isTime();
        int next = nextMeal();
          if(isTimeBoolean)
          {
              Log.d("Are you culprit", "IDK");
              buildNotification(next);
          }

        Log.d("isTime",""+isTime());


        setAlarm(next);

        if(!isTimeBoolean)
            getMenu(next);


        return START_NOT_STICKY;
    }

    private boolean isTime() {
        int hour = (c.get(Calendar.HOUR_OF_DAY));
        int minute = (c.get(Calendar.MINUTE));
        int i=0;
        int timeNow = hour*60 + minute;

        Log.d("Timenow: " ,""+ timeNow);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int premagrib =Integer.parseInt(preferences.getString("premagrib","0"));

        boolean isItActually=false;

        String bf = preferences.getString("prebf", "8:30");
        String lunch = preferences.getString("prelunch", "12:30");
        String snacks = preferences.getString("presnack", "5:00");
        String dinner = preferences.getString("predinner", "8:30");

        Boolean b_bf = preferences.getBoolean("sbf", true);
        Boolean b_lunch = preferences.getBoolean("slunch", true);
        Boolean b_snacks = preferences.getBoolean("ssnack", true);
        Boolean b_dinner = preferences.getBoolean("sdinner", true);

        times = getTimes(bf+":"+lunch+":"+snacks+":"+dinner);

        int next=0;
        for(i=0;i<4;i++) {

            if (timeNow == times[i]) {
               isItActually = true;
                Log.d("I am the culprit", "ARghhh");
            }
        }

        switch(next)
        {
            case 0: //breakfast
                return b_bf&&isItActually;
            case 1: //lunch
                return b_lunch&&isItActually;
            case 2: //snacks
                return b_snacks&&isItActually;
            case 3: //dinner
                return b_dinner&&isItActually;
        }

        Log.d(""+b_bf+b_lunch+b_snacks+b_dinner,"Yah");
        Log.d(""+bf+lunch+snacks+dinner,"Yah");


        return isItActually;
    }



    public int nextMeal()
    {
        Calendar c = Calendar.getInstance();
        int hour = (c.get(Calendar.HOUR_OF_DAY));
        int minute = (c.get(Calendar.MINUTE));
        int i=0,j;
        int timeNow = hour*60 + minute;

        Log.d("Timenow: " ,""+ timeNow);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        int premagrib =Integer.parseInt(preferences.getString("premagrib","0"));
        boolean isItActually=false;

        String bf = preferences.getString("prebf", "8:30");
        String lunch = preferences.getString("prelunch", "12:30");
        String snacks = preferences.getString("presnack", "17:00");
        String dinner = preferences.getString("predinner", "20:30");

        Log.d("Alarm times ", bf+":"+lunch+":"+snacks+":"+dinner);

        int times[] = getTimes(bf+":"+lunch+":"+snacks+":"+dinner);

        int next=0;

        for(i=0;i<4;i++) {
            Log.d("Its in time now loop","Nope");
            if (i == 3)
                j = 0;
            else
                j = i + 1;

            if (timeNow > times[i] && timeNow < times[j]) {
                //next is j
                next = j;

                Log.d("Its in time now loop","Yeah");
                Log.d("Time now: "+ timeNow, "times[i]"+times[i]);
                break;
            }

            else if(timeNow == times[i])
            {
                Log.d("Its in time now loop","And its finally equal");
                next = j;
            }



        }

        Log.d("NEXT", ""+next);

        return next;
    }

    private int[] getTimes(String timeString) {
        Calendar cal = Calendar.getInstance();
        int hour = (cal.get(Calendar.HOUR_OF_DAY));
        int minute = (cal.get(Calendar.MINUTE));
        int timeNow = hour*60 + minute;
        int time[]=new int[4], i = 0;

        StringTokenizer st = new StringTokenizer(timeString, ":");
        while (st.hasMoreTokens()) {
            time[i] = (Integer.parseInt(st.nextToken())*60)+Integer.parseInt(st.nextToken());
            Log.d("Loterry", ""+time[i]);
            Log.d("Time now", ""+timeNow);
            if(i==3)
            {
                val.putInt("dinnerMOD", times[i]);
                val.commit();
            }
           timesRemaining[i] =  (times[i]) - (timeNow);
           Log.d("Time remaining ", ""+timesRemaining[i]);
            i++;
        }

        return time;
    }
    public void getMenu(final int next)
    {


        ParseQuery<MessMenu> query;
        query = ParseQuery.getQuery(MessMenu.class);
        query.fromLocalDatastore();

        query.findInBackground(new FindCallback<MessMenu>() {
            @Override
            public void done(List<MessMenu> task, ParseException error) {


                if (task != null)
                    for (final MessMenu mess : task) {

                        Log.d("size: ", "" + task.size());
                        c = Calendar.getInstance();
                        int hour = (c.get(Calendar.HOUR_OF_DAY));
                        int minute = (c.get(Calendar.MINUTE));
                        int timeNow = hour*60 + minute;
                        int extraDay =0;
                        int isWrongSnack =0;

                        if(timeNow>=value.getInt("dinnerMOD",2200))
                            extraDay=1;

                        Log.d("Next is " + next,""+c.get(Calendar.DAY_OF_WEEK));
                        if(next==3 && (c.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY))
                        {
                            isWrongSnack=1;
                            Log.d("You're in a wrong snack","sorry");
                        }
                        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + extraDay);


                        String date;

                        date = "" + (c.get(Calendar.DAY_OF_MONTH)) + " - " + (c.get(Calendar.MONTH) + 1) + " - " + c.get(Calendar.YEAR);
                        Log.d("date: " + mess.getMenuDate(), date);

                        if (mess.getMenuDate().equals(date)) {

                            switch (next+isWrongSnack)
                            {
                                case 0: nextMeal = mess.getBreakfast();
                                    break;
                                case 1: nextMeal = mess.getLunch();
                                    break;
                                case 2: nextMeal = mess.getSnack();
                                    break;
                                case 3: nextMeal = mess.getDinner();
                                    break;

                            }
                            Log.d("Yeah", ""+nextMeal);
                            val.putString("nextMeal", ""+nextMeal);
                            val.commit();




                        }
                    }
            }
        });

    }
    public void setAlarm(int next)
    {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    int premagrib =Integer.parseInt(preferences.getString("premagrib","0"));


            Calendar c = Calendar.getInstance();
            if(timesRemaining[next]>=0)
            {
                c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + timesRemaining[next]);
                c.set(Calendar.SECOND, 0);


                Log.d("Alarm next " + next + " set for", "" + c.get(Calendar.DAY_OF_MONTH) + " - " + c.get(Calendar.MONTH) + " - " + c.get(Calendar.YEAR) + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));

                Intent intent3 = new Intent(this, AlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(this, 1, intent3, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
            }



    }

    public String[] getMealName(int id)
    {
        String meal[] = new String[3];

        String bk_time ="Serving from 7:30 AM to 10:00 AM";
        String lunch_time ="Serving from 12:30 PM to 2:30 PM";
        String snack_time ="Serving from 5:00 PM to 6:00 PM";
        String dinner_time ="Serving from 7:30 PM to 10:00 PM";
        c = Calendar.getInstance();
        if(c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
        {
            bk_time ="Serving from 8:00 AM to 10:30 AM";
            lunch_time ="Serving from 1:00 PM to 3:00 PM";
            dinner_time ="Serving from 8:00 PM to 10:30 PM";
        }

        switch (id)
        {
            case 1: meal[0] = "Breakfast";
                    meal[1] = bk_time;
                break;
            case 2: meal[0] = "Lunch";
                    meal[1] = lunch_time;
                break;
            case 3: meal[0] = "Snacks";
                    meal[1] = snack_time;
                break;
            case 0: meal[0] = "Dinner";
                    meal[1] = dinner_time;
                break;

        }



        return meal;
    }

    public void buildNotification(int mealId)
    {

        // Invoking the default notification service

        Calendar cal = Calendar.getInstance();
        String notificationTimeStamp = ""+cal.get(Calendar.DAY_OF_MONTH)+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.HOUR_OF_DAY)+"-"+cal.get(Calendar.MINUTE);
        val.putString("notificationTimeStamp",notificationTimeStamp);
        val.commit();

        long vibrate[] = {100,1000,100,1000};

        NotificationCompat.Builder  mBuilder =
              new NotificationCompat.Builder(this);
        Intent intent2 = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent2,0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());



        String meal[] = getMealName(mealId);
        String menu= value.getString("nextMeal","");

        Log.d("Next Meal", ""+menu);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        mBuilder.setAutoCancel(true).setContentIntent(pendingIntent).setLights(50, 50, 50).setVibrate(vibrate).setSmallIcon(R.drawable.ic_stat_name);
        mBuilder.setContentTitle(meal[0]).setContentText(meal[1]).setColor(getResources().getColor(R.color.primary));


        if(!menu.equals(""))
            mBuilder.setStyle(
                new NotificationCompat.BigTextStyle()
                        .bigText(menu));
        Log.d("Meal id is", ""+mealId);
        if(!(mealId==3 && (c.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||c.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)))
        {
            notificationManager.notify(1, mBuilder.build());
            getMenu(mealId);
        }


    }


}
