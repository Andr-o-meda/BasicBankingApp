package com.example.sparks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context ) {
        super(context,   "USER_DET.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Users(Uid Integer primary key autoincrement, name Text , balance Real , contactNumber Text)");
        db.execSQL("create Table Transactions(Tid Integer primary key autoincrement, Sender Text , Receiver Text , Amount Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists Users");
        db.execSQL("drop Table if exists Transactions");
    }

    public Boolean insert( String name , Float Balance, String CN) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        //c.put("Uid", id);
        c.put("name", name);
        c.put("balance", Balance);
        c.put("contactNumber", CN);
        if(db.insert("Users", null, c) == -1)
            return false;
        return true;
    }

    public Boolean insertT(String name1, String name2 , String amt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("Sender",name1);
        c.put("Receiver",name2);
        c.put("Amount",amt);
        if(db.insert("Transactions", null, c) == -1)
            return false;
        return true;
    }

    public Boolean update(Integer id,Float Balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("balance", Balance);
        Cursor cr = db.rawQuery("Select balance from Users where Uid= ?" , new String[]{id.toString()});
        if(cr.getCount() > 0 ) {
            if (db.update("Users",  c , "Uid=?",new String[]{id.toString()}) == -1)
                return false;
            return true;
        }
        else{
            System.out.println("No such entry in DB");
            return false;
        }
    }




    public Cursor getData(Integer id){
        Cursor cr;
        SQLiteDatabase db = this.getWritableDatabase();
        if(id!=-1){
            cr = db.rawQuery("Select * from Users where Uid<>?" , new String[]{id.toString()} );
        }
        else{
            cr = db.rawQuery("Select * from Users",null);
        }
        return cr;
    }

    public Cursor getDataT(){
        Cursor cr;
        SQLiteDatabase db = this.getWritableDatabase();
        cr = db.rawQuery("Select * from Transactions",null);
        return cr;
    }

}
