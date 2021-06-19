package com.example.sparks;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tranhistory);
        db = new DBHelper(this);
        final ListView l = findViewById(R.id.tlist);
        ArrayList<String> ns = new ArrayList<>();
        Cursor c1 = db.getDataT();
        String temp;
        while (c1.moveToNext()) {
            temp = String.valueOf(c1.getInt(0));
            temp = temp + " " + c1.getString(1);
            temp = temp + " " + c1.getString(2);
            temp = temp + " " + c1.getString(3);
            if (temp.length() != 0)
                ns.add(temp);
            System.out.println(temp);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(History.this, R.layout.listtext, ns);
        l.setAdapter(arrayAdapter);
    }
}
