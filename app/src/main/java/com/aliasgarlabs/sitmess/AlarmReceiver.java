package com.aliasgarlabs.sitmess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by Aliasgar Murtaza on 1/6/2015.
 */
public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

       Intent intent1 = new Intent(context,AlarmService.class);
       context.startService(intent1);

    }
}
