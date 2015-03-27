package com.aliasgarlabs.sitmess;

import android.app.Application;


import com.parse.Parse;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;



/**
 * Created by Aliasgar Murtaza on 28-Mar-15.
 */
public class MessApplication extends Application {



        @Override
        public void onCreate() {
            super.onCreate();

            // add Todo subclass
            ParseObject.registerSubclass(MessMenu.class);

            // enable the Local Datastore
            Parse.enableLocalDatastore(getApplicationContext());
                 Parse.initialize(this, "DIqz0Nr67JGm5eJGP9X1AnqcMA5nt7OSCKp2wnjz", "lhPLBRI3a6Psle3yWKhLE9WKzU8gBa64KudpSP4y");






    }
}
