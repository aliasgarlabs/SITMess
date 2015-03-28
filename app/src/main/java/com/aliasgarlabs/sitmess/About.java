package com.aliasgarlabs.sitmess;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Aliasgar Murtaza on 27-Mar-15.
 */
public class About extends ActionBarActivity implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        FloatingActionButton fab;
        TextView email = (TextView) findViewById(R.id.tv_email);
        TextView github = (TextView) findViewById(R.id.tv_github);
        TextView twitter = (TextView) findViewById(R.id.tv_twitter);
        TextView fab_github = (TextView) findViewById(R.id.tv_lib1link);
        TextView fab_github_title = (TextView) findViewById(R.id.tv_lib1);
        TextView playstore = (TextView) findViewById(R.id.tv_play);
        TextView about = (TextView) findViewById(R.id.aboutVersion);
        try {
            about.setText("SYMBI MESS " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        ImageView iv_email = (ImageView) findViewById(R.id.logo_email);
        ImageView iv_github = (ImageView) findViewById(R.id.logo_github);
        ImageView iv_twitter = (ImageView) findViewById(R.id.logo_twitter);
        ImageView iv_play = (ImageView) findViewById(R.id.logo_play);
        ImageView iv_github_fab = (ImageView) findViewById(R.id.logo_lib1);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setColorNormal(getResources().getColor(R.color.white));

        fab.setOnClickListener(this);
        email.setOnClickListener(this);
        github.setOnClickListener(this);
        twitter.setOnClickListener(this);
        playstore.setOnClickListener(this);
        fab_github_title.setOnClickListener(this);
        fab_github.setOnClickListener(this);

        iv_email.setOnClickListener(this);
        iv_github.setOnClickListener(this);
        iv_github_fab.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_twitter.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_about, menu);

        return (super.onCreateOptionsMenu(menu));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.share:

                Toast.makeText(this, "Please Wait...", Toast.LENGTH_LONG).show();


                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Tired of wondering what's in the mess and make bad guesses every time? Now you can have Mess Menu right in your pocket. \n\nBrowse through menu, give meal reviews, and much more. \n\n\nDownload the Symbi Mess App " + "\n\nbit.ly/symbimessapp");
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_email:
                try {
                    email();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.logo_email:
                try {
                    email();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_github:
                openURL("https://github.com/aliasgarlabs");
                break;
            case R.id.logo_github:
                openURL("https://github.com/aliasgarlabs");
                break;
            case R.id.tv_twitter:
                openURL("https://twitter.com/aliasgarmurtaza");
                break;
            case R.id.logo_twitter:
                openURL("https://twitter.com/aliasgarmurtaza");
                break;
            case R.id.logo_play:
                openURL("https://bitly.com/AliasgarApps");
                break;
            case R.id.tv_play:
                openURL("https://bitly.com/AliasgarApps");
                break;
            case R.id.fab:
                openURL("https://bitly.com/symbiapp");
                break;
            case R.id.logo_lib1:
                openURL("https://github.com/makovkastar/FloatingActionButton");
                break;
            case R.id.tv_lib1:
                openURL("https://github.com/makovkastar/FloatingActionButton");
                break;
            case R.id.tv_lib1link:
                openURL("https://github.com/makovkastar/FloatingActionButton");
                break;

        }
    }

    private void openURL(String url) {

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void email() throws PackageManager.NameNotFoundException {

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setType("plain/text");
        sendIntent.setData(Uri.parse("aliasgarlabs@gmail.com"));
        sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"aliasgarlabs@gmail.com"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "SIT Mess App " + getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode);

        startActivity(sendIntent);
    }
}
