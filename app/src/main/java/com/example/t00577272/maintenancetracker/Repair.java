package com.example.t00577272.maintenancetracker;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "repair_table")
public class Repair{ // Main object class that represents the maintanences and work

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String name;

    private String maint;
    //@PrimaryKey
    //@NonNull
    //@ColumnInfo(name = "dayOne")
    private String dayOne;

    //@PrimaryKey
    //@NonNull
    //@ColumnInfo(name = "dayTwo")
    private String dayTwo;

    //@PrimaryKey
    //@NonNull
    //@ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "dif")
    private int dif;

    //protected Repair(Parcel in) {

     //   name = in.readString();
        //dayOne = in.readString();
        //dayTwo  = in.readString();
        //note = in.readString();

    //}
    /*private  int year1;
    private  int month1;
    private  int day1;
    private  int year2;
    private  int month2;
    private  int day2;*/

    public Repair(){
        name = "this";
        dayOne = "01/02/2003";
        dayTwo = "11/12/2004";
        note = "is default";
        dif = 0;
    }

    public Repair(@NonNull String title/*,@NonNull String one,@NonNull String two,@NonNull String thing*/){
        this.name = title;
        //this.dayOne = one;
        //this.dayTwo = two;
        //this.note = thing;
    }

    public Repair(@NonNull String title,@NonNull String one,@NonNull String two,@NonNull String thing){
        this.name = title;
        this.dayOne = one;
        this.dayTwo = two;
        this.note = thing;
        this.dif = this.getDif();
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");

            Date date1 = myFormat.parse(getDayOne());
            Date date2 = myFormat.parse(getDayTwo());
            Date today = new Date();

            double difup = Math.abs(date2.getTime() - today.getTime());

            this.dif = (int)Math.round( (difup/(1000 * 60 * 60 * 24))+1);


        }
        catch (Exception e){
            e.printStackTrace();
        }

        /*this.year1 = Integer.parseInt(dayOne.substring(0,4));
        this.month1 = Integer.parseInt(dayOne.substring(5,7));
        this.day1 = Integer.parseInt(dayOne.substring(7,9));
        this.year2 = Integer.parseInt(dayTwo.substring(0,4));
        this.month2 = Integer.parseInt(dayTwo.substring(5,7));
        this.day2 = Integer.parseInt(dayTwo.substring(7));*/
    }
/*
    public static final Creator<Repair> CREATOR = new Creator<Repair>() {
        @Override
        public Repair createFromParcel(Parcel in) {
            return new Repair(in);
        }

        @Override
        public Repair[] newArray(int size) {
            return new Repair[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
*/
    public String getName() {
        return this.name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getDayOne() {
        return this.dayOne;
    }

    public void setDayOne(String dayOne) {
        this.dayOne = dayOne;
    }

    public String getDayTwo() {
        return this.dayTwo;
    }

    public void setDayTwo(String dayTwo) {
        this.dayTwo = dayTwo;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMaint() {
        return this.maint;
    }

    public void setMaint(String maint) {
        this.maint = maint;
    }

    public void setDif(int dif) {
        this.dif = dif;
    }

    public int getDif(){
        int ans = 0;
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");

            Date date1 = myFormat.parse(getDayOne());
            Date date2 = myFormat.parse(getDayTwo());
            Date today = new Date();

            double total = Math.abs(date2.getTime() - date1.getTime());
            double dif = Math.abs(today.getTime() - date1.getTime());
            double difup = Math.abs(date2.getTime() - today.getTime());

            ans = (int)Math.round( difup/(1000 * 60 * 60 * 24)+1);


        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ans;
    }

    public int compareTo(Repair repair){
        return this.getDif() < repair.getDif() ? 1
                : repair.getDif() > this.getDif() ? -1
                : 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /*public int getDay1() {
        return this.day1;
    }

    public int getDay2() {
        return this.day2;
    }

    public int getMonth1() {
        return this.month1;
    }

    public int getMonth2() {
        return this.month2;
    }

    public int getYear1() {
        return this.year1;
    }

    public int getYear2() {
        return this.year2;
    }

    public void setDay1(int day1) {
        this.day1 = day1;
    }

    public void setDay2(int day2) {
        this.day2 = day2;
    }

    public void setMonth1(int month1) {
        this.month1 = month1;
    }

    public void setMonth2(int month2) {
        this.month2 = month2;
    }

    public void setYear1(int year1) {
        this.year1 = year1;
    }

    public void setYear2(int year2) {
        this.year2 = year2;
    }*/

    //public static Creator<Repair> getCREATOR() {
    //    return CREATOR;
    //}

/*
    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(title);
        parcel.writeString(dayOne);
        parcel.writeString(dayTwo);
        parcel.writeString(note);

    }

    public void convert(String input){
        String str = input;
        String[] array = str.split("||");
        title = array[0];
        dayOne = array[1];
        dayTwo = array[2];
        note = array[3];
    }

    //@Override
    //public String toString() {
    //    return title+"||"+dayOne+"||"+dayTwo+"||"+note;
    //}*/
}
