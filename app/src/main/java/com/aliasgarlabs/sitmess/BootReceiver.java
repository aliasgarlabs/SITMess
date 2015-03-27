package com.aliasgarlabs.sitmess;

/**
 * Created by Aliasgar Murtaza on 18-Feb-15.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

      Intent service = new Intent(context, AlarmService.class);
       context.startService(service);

    }
}
