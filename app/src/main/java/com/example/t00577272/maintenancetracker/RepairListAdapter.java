package com.example.t00577272.maintenancetracker;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static android.support.v4.content.ContextCompat.createDeviceProtectedStorageContext;
import static android.support.v4.content.ContextCompat.startActivity;


public class RepairListAdapter extends RecyclerView.Adapter<RepairListAdapter.RepairViewHolder> {

    class RepairViewHolder extends RecyclerView.ViewHolder { // Main adaptor that uses a list of Repair objects and displays them in the RecyclerView
        private final TextView wordItemView;
        private final TextView date;
        private final ProgressBar progressBar;
        private final TextView otherdate;
        private final ConstraintLayout constraintLayout;

        private RepairViewHolder(View itemView) {

            super(itemView);

            wordItemView = itemView.findViewById(R.id.textView);
            date = itemView.findViewById(R.id.dateView);
            progressBar = itemView.findViewById(R.id.progressBar);
            otherdate = itemView.findViewById(R.id.textView6);
            constraintLayout = itemView.findViewById(R.id.constraintlayout);

        }
    }

    private final LayoutInflater mInflater;
    private List<Repair> mRepairs; // Cached copy of words
    private static final long ONE_DAY = 1000 * 60 * 60 * 24;

    RepairListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public RepairViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);


        return new RepairViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RepairViewHolder holder, final int position) {
        Collections.sort(mRepairs, new Comparator<Repair>() {

            public int compare(Repair o1, Repair o2) {
                // compare two instance of `Score` and return `int` as result.
                return o2.compareTo(o1);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRepairs != null) {
                    Repair sendRepair = mRepairs.get(position);
                    Intent intent = new Intent(holder.itemView.getContext(), Main2Activity.class);
                    intent.putExtra("TITLE", sendRepair.getName());
                    intent.putExtra("DAY_1", sendRepair.getDayOne());
                    intent.putExtra("DAY_2", sendRepair.getDayTwo());
                    intent.putExtra("NOTE", sendRepair.getNote());
                    v.getContext().startActivity(intent);

                    //Main2Activity.openRepair(mRepairs.get(position));
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    MainActivity.delete(mRepairs.get(position));
                                } catch (Exception ioe) {
                                    // TODO let the user know the file couldn't be deleted
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setMessage("Delete "+mRepairs.get(position).getName()+" record?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return false;
            }
        });

        if (mRepairs != null) {
            Repair current = mRepairs.get(position);
            holder.wordItemView.setText(current.getName());
            holder.date.setText(current.getDayTwo());



            SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date1 = myFormat.parse(current.getDayOne());
                Date date2 = myFormat.parse(current.getDayTwo());
                Date today = new Date();

                double total = Math.abs(date2.getTime()-date1.getTime());
                double difup = Math.abs(date2.getTime()-today.getTime());
                int daysleft = (int)Math.round(difup/ONE_DAY)+1;
                double totaldays = Math.round((total/ONE_DAY)+1);


                holder.progressBar.setProgress((int)Math.abs((totaldays-daysleft)/totaldays*100));
                holder.date.setText(daysleft+" days left ");
                holder.otherdate.setText("Last done: "+current.getDayOne());
                if(daysleft>2){
                    holder.wordItemView.setBackgroundColor(ContextCompat.getColor(holder.wordItemView.getContext(), R.color.whitely));
                    holder.date.setBackgroundColor(ContextCompat.getColor(holder.date.getContext(), R.color.whitely));
                    holder.progressBar.setBackgroundColor(ContextCompat.getColor(holder.progressBar.getContext(), R.color.whitely));
                    holder.otherdate.setBackgroundColor(ContextCompat.getColor(holder.otherdate.getContext(), R.color.whitely));
                    holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(holder.constraintLayout.getContext(),R.color.whitely));

                }
                else{
                    holder.wordItemView.setBackgroundColor(ContextCompat.getColor(holder.wordItemView.getContext(), R.color.redly));
                    holder.date.setBackgroundColor(ContextCompat.getColor(holder.date.getContext(), R.color.redly));
                    holder.date.setText("Less than 2 days left ");
                    holder.progressBar.setBackgroundColor(ContextCompat.getColor(holder.progressBar.getContext(), R.color.redly));
                    holder.otherdate.setBackgroundColor(ContextCompat.getColor(holder.otherdate.getContext(), R.color.redly));
                    holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(holder.constraintLayout.getContext(),R.color.redly));
                    if(daysleft==1) {
                        holder.date.setText("Less than 1 day left");
                        holder.wordItemView.setBackgroundColor(ContextCompat.getColor(holder.wordItemView.getContext(), R.color.redlier));
                        holder.date.setBackgroundColor(ContextCompat.getColor(holder.date.getContext(), R.color.redlier));
                        holder.progressBar.setBackgroundColor(ContextCompat.getColor(holder.progressBar.getContext(), R.color.redlier));
                        holder.otherdate.setBackgroundColor(ContextCompat.getColor(holder.otherdate.getContext(), R.color.redlier));
                        holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(holder.constraintLayout.getContext(),R.color.redlier));
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }


    }

    void setRepairs(List<Repair> repairs){ // Sorting list
        mRepairs = repairs;
        Collections.sort(mRepairs, new Comparator<Repair>() {
            public int compare(Repair o1, Repair o2) {
                return o2.compareTo(o1);
            }
        });
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mRepairs != null)
            return mRepairs.size();
        else return 0;
    }

    public Repair getRepair(String name){
        notifyDataSetChanged();
        return new Repair();
    }




}
