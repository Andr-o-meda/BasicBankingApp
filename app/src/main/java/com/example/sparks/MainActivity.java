package com.example.sparks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import static java.lang.Boolean.getBoolean;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    Intent rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rec = getIntent();
        Boolean spl = rec.getBooleanExtra("splash" , true);
        if(spl) {
            Intent intent = new Intent(this, Splash_screen.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        Cursor c1 = db.getData(-1);
        if (c1.getCount() == 0)
            createNew();
        Button b1 = findViewById(R.id.b1);
        Button b2 = findViewById(R.id.b2);
        b1.setOnClickListener(v-> {
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(i);
        });
        b2.setOnClickListener(v-> {
            Intent i = new Intent(MainActivity.this, History.class);
            startActivity(i);
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"BACKS DON'T WORK",Toast.LENGTH_SHORT).show();
    }
    void createNew() {
        db.insert("VonNeumann", (float) 12000, "9099212342");
        db.insert("Kristy", (float) 15000, "9027300931");
        db.insert("Stephen", (float) 18080, "9027300933");
        db.insert("Samon", (float) 10930, "9027300936");
        db.insert("Sumit", (float) 18930, "9027300921");
        db.insert("Sunil", (float) 10980.08, "9027300911");
        db.insert("Shruti", (float) 18000.13, "9027304531");
        db.insert("Amanda", (float) 11300, "9027300631");
        db.insert("Sran", (float) 10000, "9027300915");
        db.insert("Virat", (float) 7000, "9027300951");
    }
}