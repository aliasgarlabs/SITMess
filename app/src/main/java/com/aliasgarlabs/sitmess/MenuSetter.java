package com.aliasgarlabs.sitmess;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

/**
 * Created by Aliasgar Murtaza on 21-Mar-15.
 */

public class MenuSetter extends ActionBarActivity implements View.OnClickListener {

    Spinner et_lunch,et_snack,et_dinner;
    final Context context = this;
    private Button button;
    private EditText result;
    Spinner et_breakfast;
    Button clear_breakfast,add_breakfast,clear_lunch,clear_snacks,add_lunch,clear_dinner,add_snacks,add_dinner;
    FloatingActionButton fab;
    int year ;
    int month;
    int day;
    String date;
    TextView tv_date, tv_bf,tv_lunch,tv_snack,tv_dinner;
    Calendar c;
    String bf="";
    RelativeLayout snack_layout;
    ArrayList<String> array;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tomorrow);

        et_breakfast = (Spinner) findViewById(R.id.et_breakfast);
        et_lunch = (Spinner) findViewById(R.id.et_lunch);
        et_snack = (Spinner) findViewById(R.id.et_snack);
        et_dinner = (Spinner) findViewById(R.id.et_dinner);
        tv_date = (TextView) findViewById(R.id.tv_date);
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        snack_layout = (RelativeLayout) findViewById(R.id.snack_layout);

        setDateLocal(year, month, day);


        add_breakfast = (Button) findViewById(R.id.add_bf);
        add_lunch = (Button) findViewById(R.id.add_lunch);
        add_snacks = (Button) findViewById(R.id.add_snack);
        add_dinner = (Button) findViewById(R.id.add_dinner);

        clear_breakfast = (Button) findViewById(R.id.clear_bf);
        clear_lunch = (Button) findViewById(R.id.clear_lunch);
        clear_snacks = (Button) findViewById(R.id.clear_snack);
        clear_dinner = (Button) findViewById(R.id.clear_dinner);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        tv_bf = (TextView) findViewById(R.id.tv_bf);
        tv_lunch = (TextView) findViewById(R.id.tv_lunch);
        tv_snack = (TextView) findViewById(R.id.tv_snack);
        tv_dinner = (TextView) findViewById(R.id.tv_dinner);

      tv_bf.setOnClickListener(this);
      tv_lunch.setOnClickListener(this);
      tv_dinner.setOnClickListener(this);
      tv_dinner.setOnClickListener(this);
      tv_date.setOnClickListener(this);
      fab.setOnClickListener(this);
      add_dinner.setOnClickListener(this);
      add_snacks.setOnClickListener(this);
      add_lunch.setOnClickListener(this);
      add_breakfast.setOnClickListener(this);
      clear_dinner.setOnClickListener(this);
      clear_snacks.setOnClickListener(this);
      clear_lunch.setOnClickListener(this);
      clear_breakfast.setOnClickListener(this);

        array  = new ArrayList<String>();
        intializeArray();
        ParseObject.registerSubclass(MessMenu.class);


        updateData();



        resetArray();







}
void resetArray()
{


    Collections.sort(array);

    ArrayAdapter<String> mAdapter;

    mAdapter = new ArrayAdapter<String>(this, R.layout.spinner, array);
    et_breakfast.setAdapter(mAdapter);
    et_lunch.setAdapter(mAdapter);
    et_snack.setAdapter(mAdapter);
    et_dinner.setAdapter(mAdapter);
}
    public void newItem()
    {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.new_item, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                array.size();
                                Log.d("Before : ",  ""+ array.size());
                                array.add(userInput.getText().toString());
                                Log.d("After ",  ""+ array.size());
                                resetArray();
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void updateData(){
        ParseQuery<MessMenu> query = ParseQuery.getQuery(MessMenu.class);
        query.findInBackground(new FindCallback<MessMenu>() {

            @Override
            public void done(List<MessMenu> tasks, ParseException error) {
                if (tasks != null) {

                    Toast.makeText(getApplicationContext(), "Menu Updated!", Toast.LENGTH_LONG).show();
                }
            }


        });



    }

    public void createTask(View v) {


            MessMenu t = new MessMenu();
            t.setBreakfast(tv_bf.getText().toString());
            t.setLunch(tv_lunch.getText().toString());
            t.setDinner(tv_dinner.getText().toString());
            t.setSnack(tv_snack.getText().toString());
            t.setMenuDate(date);
            t.saveEventually();
            tv_bf.setText("");
            tv_lunch.setText("");
            tv_snack.setText("");
            tv_dinner.setText("");
            Toast.makeText(getApplicationContext(),"Menu added successfully!", Toast.LENGTH_LONG).show();
        }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                // set date picker as current date

                return new DatePickerDialog(this, pickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {



        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

           setDateLocal(year, monthOfYear, dayOfMonth);


        }
    };


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

    @Override
    public void onClick(View v) {

        Log.d("Debug it bro", "Cool");
        switch(v.getId())
        {
            case R.id.fab:   createTask(v);
                break;

            case R.id.add_bf:
                if(tv_bf.getText().toString().equals(""))
                    tv_bf.setText(et_breakfast.getSelectedItem().toString());
                else
                    tv_bf.setText(tv_bf.getText().toString()+"\n"+ et_breakfast.getSelectedItem().toString());
                break;
            case R.id.add_lunch:
                if(tv_lunch.getText().toString().equals(""))
                    tv_lunch.setText(et_lunch.getSelectedItem().toString());
                else
                    tv_lunch.setText(tv_lunch.getText().toString()+"\n"+ et_lunch.getSelectedItem().toString());
                break;
            case R.id.add_snack:
                if(tv_snack.getText().toString().equals(""))
                tv_snack.setText(et_snack.getSelectedItem().toString());
            else
                tv_snack.setText(tv_snack.getText().toString()+"\n"+ et_snack.getSelectedItem().toString());
                break;
            case R.id.add_dinner:
                if(tv_dinner.getText().toString().equals(""))
                    tv_dinner.setText(et_dinner.getSelectedItem().toString());
                else
                    tv_dinner.setText(tv_dinner.getText().toString()+"\n"+ et_dinner.getSelectedItem().toString());
                break;

            case R.id.clear_bf:
                tv_bf.setText("");
                break;
            case R.id.clear_lunch:
                tv_lunch.setText("");
                break;
            case R.id.clear_snack:
                tv_snack.setText("");
                break;
            case R.id.clear_dinner:
                tv_dinner.setText("");
                break;
            case R.id.tv_date:  showDialog(1);
                break;



        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

            case R.id.add :
                 newItem();



        }
        return true;
    }
    public void setDateLocal(int year, int monthOfYear, int dayOfMonth)
    {
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.YEAR, year);

        date = ""+dayOfMonth+" - "+ (monthOfYear+1)+ " - " +year;
        tv_date.setText(""+getWeekDay(c.get(Calendar.DAY_OF_WEEK))+"\n"+ date);
        if(c.get(Calendar.DAY_OF_WEEK)==2||c.get(Calendar.DAY_OF_WEEK)==4||c.get(Calendar.DAY_OF_WEEK)==6) {
            snack_layout.setVisibility(View.VISIBLE);


        }
        else
        {   snack_layout.setVisibility(View.GONE);


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_setter_main, menu);

        return (super.onCreateOptionsMenu(menu));
    }


    public void intializeArray() {
        array.add("Paneer Tikka Masala");
        array.add("Paneer Kofta");
        array.add("Paneer Adraki");
        array.add("Paneer Toofani");
        array.add("Paneer Banjara");
        array.add("Palak Paneer");
        array.add("Aalu Paneer");
        array.add("Paneer Masala");
        array.add("Paneer Butter Masala");
        array.add("Paneer Mutter Malai");
        array.add("Paneer Aflatun");
        array.add("Kadai Paneer");
        array.add("Paneer Bhurji");
        array.add("Mutter Paneer");
        array.add("Chicken Adraki");
        array.add("Chicken Hariyali");
        array.add("Butter Chicken");
        array.add("Butter Chicken Boneless");
        array.add("Kadai Chicken");
        array.add("Tandoori Chicken");
        array.add("Chicken Hyderabadi");
        array.add("Chicken Aflatoon");
        array.add("Chicken Handi");
        array.add("Mutter Muthiya");
        array.add("Besan Gutta");
        array.add("Navratan Korma");
        array.add("Rajma Masala");
        array.add("Veg Kofta");
        array.add("Veg 65");
        array.add("Chhole");
        array.add("Aalu Gobhi Rassa");
        array.add("Tava Sabji");
        array.add("Veg Bhuna");
        array.add("Stuffed Tomato");
        array.add("Stuffed Capsicum");
        array.add("Veg Handi");
        array.add("Veg Jalfrazi");
        array.add("Aalu Jholwala");
        array.add("Veg Kolhapuri");
        array.add("Flavor Tomato");
        array.add("Kashmiri Dum Aalu");
        array.add("Bengali Dum Aalu");
        array.add("Methi Mutter Malai");
        array.add("Egg Cury");
        array.add("Kadhi Pakoda");
        array.add("Malai Kofta");
        array.add("Stuffed Baigan");
        array.add("Veg Makhanwala");
        array.add("Veg Maratha");
        array.add("Baby Corn Mushroom");
        array.add("Sindhi Kadhi");
        array.add("Baigan Chana");
        array.add("Bhindi Dry");
        array.add("Bhindi Crispy");
        array.add("Bhindi Masala");
        array.add("Cabbage Dry");
        array.add("Gajar Mutter");
        array.add("Chavli Dry");
        array.add("Aalu Gobhi");
        array.add("Sprout Chaat");
        array.add("Sprout Usal");
        array.add("Moong Dry");
        array.add("Dosa Bhaji");
        array.add("Corn Palak");
        array.add("Beans Dry");
        array.add("Jeera Aalu");
        array.add("Kadai Baby Corn");
        array.add("Green Peas Masala");
        array.add("Masoor Dry");
        array.add("Soyabean Keema");
        array.add("Mix Veg Dry");
        array.add("Aalu Parmal");
        array.add("Aalu Lahsunia");
        array.add("Aalu Poshto");
        array.add("Cabbage Mutter");
        array.add("Aalu Mastana");
        array.add("Aalu Chana");
        array.add("Flower Tomato Dry");
        array.add("Spring Onion");
        array.add("Baigan Bharta");
        array.add("Kala Chana");
        array.add("Aalu Tikka");
        array.add("Masala Gajar Tomato Dry");
        array.add("Aalu Methi Dry");
        array.add("Nutrila Dry");
        array.add("Besan Simla");
        array.add("Dal Makhani");
        array.add("Moong Dal");
        array.add("Chana Dal");
        array.add("Urad Dal");
        array.add("Masur Dal");
        array.add("Rajasthani Dal");
        array.add("Dal Tadka");
        array.add("Palak Dal");
        array.add("Sabudana Khichdi");
        array.add("Sabudana Vada");
        array.add("Poha");
        array.add("Tadka Idli");
        array.add("French Toast");
        array.add("Omlet");
        array.add("Veg Cutlet");
        array.add("Uttapam");
        array.add("Mung Silida");
        array.add("Khaman Dhokla");
        array.add("White Dhokla");
        array.add("Tiranga Dhokla");
        array.add("Cheese Sandwich");
        array.add("Kadhi");
        array.add("Dabeli");
        array.add("Egg Bhurji");
        array.add("Mendu Wada");
        array.add("Upma");
        array.add("Porridge");
        array.add("Mix Veg Paratha");
        array.add("Paneer Paratha");
        array.add("Gobhi Paratha");
        array.add("Mutter Paratha");
        array.add("Methi Paratha");
        array.add("Jeera Paratha");
        array.add("Reshmi Paratha");
        array.add("Coconut Chutney");
        array.add("Bread Butter/Jam");
        array.add("Fruit Custard");
        array.add("Gulab Jamun");
        array.add("Pineapple Halwa");
        array.add("Chocolate Coconut Burfi");
        array.add("Ice Cream");
        array.add("Suji Halwa");
        array.add("Gajar Halwa");
        array.add("Jalebi");
        array.add("Rasgulla");
        array.add("Mung Dal Halwa");
        array.add("Date(Khajur) Rolls");
        array.add("Rice Kheer");
        array.add("Sevai Kheer");
        array.add("Mango Kheer");
        array.add("Dudhi Halwa");
        array.add("Samosa");
        array.add("Surti Plaza");
        array.add("Susola");
        array.add("Murmura Bhel");
        array.add("Bread Poha");
        array.add("Bread Patties");
        array.add("Maggi");
        array.add("Pappdi Bhel");
        array.add("Cutlet");
        array.add("Veg Roll");
        array.add("Kotambar Wada");
        array.add("Batata Vada");
        array.add("Parle G");
        array.add("Chutney Sandwich");
        array.add("Corn Patties");
        array.add("Ragda Patties");
        array.add("Red Sauce Pasta");
        array.add("Garlic Bread");
        array.add("Hakka Noodles");
        array.add("Schezwan Noodles");
        array.add("Pav Bhaji");
        array.add("Papdi Chaat");
        array.add("Dahi Chaat");
        array.add("Sprout Chaat");
        array.add("Chole Bhature");
        array.add("Chole Puri");
        array.add("Veg Manchurian");
        array.add("Schezwan Sauce");
        array.add("Tomato Soup");
        array.add("Hot and Sour Soup");
        array.add("Sweet Corn Soup");
        array.add("Cream Vegetable Soup");
        array.add("Manchow Soup");
        array.add("Curd");
        array.add("Boondi Raita");
        array.add("Angoor Raita");
        array.add("Dahi Bhalla");
        array.add("Spiced Up Buttermilk");
        array.add("Milk Cornflakes");
        array.add("Nimbu Pani");
        array.add("Rasna");
        array.add("Jaljeera");
        array.add("Strawberry Lassi");
        array.add("Tandoori Roti");
        array.add("Chapati");
        array.add("Naan");
        array.add("Fulka");
        array.add("Lachha Paratha");
        array.add("Radhaballabhi");
        array.add("Brown Pulav");
        array.add("Jeera Rice");
        array.add("Pudina Rice");
        array.add("Supreme Pulav");
        array.add("Jafrani Pulav");
        array.add("Vegetable Pulav");
        array.add("Masala Pulav");
        array.add("Veg Biryani");
        array.add("Nutrella Biryani");
        array.add("Hyderabadi Biryani");
        array.add("Chicken Biryani");
        array.add("White Pulav");
        array.add("Schezwan Fried Rice");
        array.add("Tea/coffee");





    }

}

