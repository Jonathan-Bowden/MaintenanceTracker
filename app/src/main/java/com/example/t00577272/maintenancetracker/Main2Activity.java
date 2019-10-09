package com.example.t00577272.maintenancetracker;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.t00577272.maintenancetracker.MainActivity.EDIT_REPAIR_ACTIVITY_REQUEST_CODE;

public class Main2Activity extends AppCompatActivity {

    ArrayList<Repair> list = new ArrayList<Repair>();


    Repair repair;

    static EditText name,date1,date2,note;

    Button save,delete;

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private RepairViewModel mRepairViewModel;

    private List<Repair> mRepairs;

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar my2Calendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    DatePickerDialog.OnDateSetListener otherDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            my2Calendar.set(Calendar.YEAR, year);
            my2Calendar.set(Calendar.MONTH, monthOfYear);
            my2Calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        name = findViewById(R.id.editText3);
        date1 = findViewById(R.id.editText);
        date2 = findViewById(R.id.editText5);
        note = findViewById(R.id.editText2);

        save = findViewById(R.id.button);
        delete = findViewById(R.id.button2);

        mRepairViewModel = ViewModelProviders.of(this).get(RepairViewModel.class);



        int newInt;



        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Main2Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Main2Activity.this, otherDate, my2Calendar
                        .get(Calendar.YEAR), my2Calendar.get(Calendar.MONTH),
                        my2Calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            newInt = 0;
        } else {

            name.setText(extras.getString("TITLE"));
            date1.setText(extras.getString("DAY_1"));
            date2.setText(extras.getString("DAY_2"));
            note.setText(extras.getString("NOTE"));
        }


        final String date2old = date2.getText().toString();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);


                startActivity(intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(date1.getText()) || TextUtils.isEmpty(date2.getText()))){
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(note.getText())) {
                    String nom = name.getText().toString();
                    String day = date1.getText().toString();
                    String day2 = date2.getText().toString();
                    String thing = "";

                    repair = new Repair(nom, day, day2, thing);
                    mRepairViewModel.insert(repair);

                    //String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, repair.getName());
                    setResult(RESULT_OK, replyIntent);

                } else if (date2.getText().toString().compareTo(date2old) == 0) {
                    String nom = name.getText().toString();
                    String day = date1.getText().toString();
                    String day2 = date2.getText().toString();
                    String thing = note.getText().toString();

                    repair = new Repair(nom, day, day2, thing);
                    mRepairViewModel.insert(repair);

                    //String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, repair.getName());
                    setResult(RESULT_OK, replyIntent);

                } else {
                    String nom = name.getText().toString();
                    String day = date1.getText().toString();
                    String day2 = date2.getText().toString();
                    String thing = note.getText().toString();

                    repair = new Repair(nom, day, day2, thing);
                    mRepairViewModel.insert(repair);

                    //String word = mEditWordView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, repair.getName());
                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }    else{
                    Toast.makeText(
                            getApplicationContext(),
                            /*R.string.empty_not_saved*/"Enter name and dates.",
                            Toast.LENGTH_LONG).show();
                }
        }});

}

    public void save() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.clear();
        /* sKey is an array */
        mEdit1.putInt("Status_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i).toString());
        }


        mEdit1.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date now = new Date();
        if((now.getTime()-(myCalendar.getTimeInMillis()))>0) {
            date1.setText(sdf.format(myCalendar.getTime()));
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Date must be today or earlier.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void updateLabel2() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date now = new Date();
        if((now.getTime()-(my2Calendar.getTimeInMillis()))<0) {
            date2.setText(sdf.format(my2Calendar.getTime()));
        }
        else {
            Toast.makeText(
                    getApplicationContext(),
                    "Date must be in the future.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
