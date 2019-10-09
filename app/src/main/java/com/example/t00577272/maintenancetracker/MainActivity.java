package com.example.t00577272.maintenancetracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;

    public static final int NEW_REPAIR_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_REPAIR_ACTIVITY_REQUEST_CODE = 2;


    private static RepairViewModel mRepairViewModel;

    //private List<Repair> list = new ArrayList<>();

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main/*activity_main*/);


        RecyclerView recyclerView = findViewById(R.id.recycler);
        final RepairListAdapter adapter = new RepairListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        textView = findViewById(R.id.textView5);

        mRepairViewModel = ViewModelProviders.of(this).get(RepairViewModel.class);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        //recyclerView = (RecyclerView) findViewById(R.id.recycler);

        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager


        //Intent intent = getIntent();
        //Repair repair = intent.getParcelableExtra("Deta");
        /*
        Repair example = new Repair();

        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(this);
        list.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            example.convert(mSharedPreference1.getString("Status_" + i, null));
            list.add(example);
        }*/

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);


                startActivityForResult(intent, NEW_REPAIR_ACTIVITY_REQUEST_CODE);
                //intent.putExtra("Deta", repair);
                //startActivity(intent);
                //finish();
            }
        });

        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        //list.add(new Repair());
        //mAdapter = new MyAdapter(list);
        //recyclerView.setAdapter(mAdapter);
        mRepairViewModel.getAllRepairs().observe(this, new Observer<List<Repair>>() {
            @Override
            public void onChanged(@Nullable final List<Repair> repairs) {
                // Update the cached copy of the words in the adapter.
                adapter.setRepairs(repairs);
                if(repairs.size()==0)
                    textView.setText(getResources().getString(R.string.car));
                else {
                    textView.setText(getResources().getString(R.string.newtext));
                }
            }
        });


    }

    /*public void save() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();
        mEdit1.clear();
        /* sKey is an array */
    /*    mEdit1.putInt("Status_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i).toString());
        }


        mEdit1.apply();
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //save();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == NEW_REPAIR_ACTIVITY_REQUEST_CODE||requestCode==EDIT_REPAIR_ACTIVITY_REQUEST_CODE) && resultCode == RESULT_OK) {
            //String ret = data.getStringExtra(Main2Activity.EXTRA_REPLY);
            //String[] arr;
            //arr = ret.split("@@");
            //Repair repair = new Repair(arr[0],arr[1],arr[2],arr[3]);
            //Repair repair = new Repair(data.getStringExtra(Main2Activity.EXTRA_REPLY));
            //mRepairViewModel.insert(repair);




        }
        /*if (resultCode == EDIT_REPAIR_ACTIVITY_REQUEST_CODE){
            Repair notify = mRepairViewModel.getLowest();

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_add_circle_outline_black_24dp)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!");
            //.setContentIntent(pendingIntent);
        } */else {
            Toast.makeText(
                    getApplicationContext(),
                    /*R.string.empty_not_saved*/"Empty not saved.",
                    Toast.LENGTH_LONG).show();
        }
    }



    /*public int setAlarm() {
        Calendar Calendar_Object = Calendar.getInstance();

        Intent myIntent = new Intent(this, Notifier.);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long wkupTime = Calendar_Object.getTimeInMillis() + 36000;

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, wkupTime ,3600000, pendingIntent);

        return 0;
    }*/
    public static void delete(Repair repair){
        mRepairViewModel.delete(repair);
    }
}
