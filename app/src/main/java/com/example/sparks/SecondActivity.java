package com.example.sparks;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static android.widget.Toast.LENGTH_SHORT;

public class SecondActivity extends AppCompatActivity {
    DBHelper db;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listcust);
        db = new DBHelper(this);
        final ListView l = findViewById(R.id.entry);
        AtomicReference<TextView> heading = new AtomicReference<>(findViewById(R.id.t1));
        ArrayList<String> ns = new ArrayList<>();
        ArrayList<String> ns1 = new ArrayList<>();
        Cursor c1 = db.getData(-1);
        Button b1 = findViewById(R.id.canshel);
        Toast.makeText(this,"Long Press to View Details", LENGTH_SHORT).show();
        b1.setOnClickListener(v->{
            Toast.makeText(this, "ABORTING!!" , LENGTH_SHORT).show();
            go_back();
        });
        if (c1.getCount() == 0) {
            Toast.makeText(this, "Creating Database, TRY AGAIN", LENGTH_SHORT).show();
        } else {
            String temp = "";
            while (c1.moveToNext()) {
                temp = String.valueOf(c1.getInt(0));
                temp = temp + " " + c1.getString(1);
                temp = temp + " " + c1.getFloat(2);
                temp = temp + " " + c1.getString(3);
                if (temp.length() != 0)
                    ns.add(temp);
                //System.out.println(temp);
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SecondActivity.this, R.layout.listtext, ns);
            l.setAdapter(arrayAdapter);
            l.setOnItemClickListener((parent, view, position, id) -> {
                heading.get().setText("SELECT USER2");
                String clickedItem = (String) l.getItemAtPosition(position);
                System.out.println(clickedItem);
                l.setAdapter(null);
                Integer i = Integer.parseInt(clickedItem.substring(0, clickedItem.indexOf(' ')));
                Cursor c2 = db.getData(i);
                if (c2.getCount() == 0) {
                    Toast.makeText(this, "No Entry Exists ", LENGTH_SHORT).show();
                } else {
                    String temp1;
                    while (c2.moveToNext()) {
                        temp1 = String.valueOf(c2.getInt(0));
                        temp1 = temp1 + " " + c2.getString(1);
                        temp1 = temp1 + " " + c2.getFloat(2);
                        temp1 = temp1 + " " + c2.getString(3);
                        if (temp1.length() != 0)
                            ns1.add(temp1);
                        //System.out.println(temp1);
                    }
                    ArrayAdapter<String> anar = new ArrayAdapter<>(SecondActivity.this, R.layout.listtext, ns1);
                    l.setAdapter(anar);
                    l.setOnItemClickListener((p, V, pos, Id) -> {
                        String clickedItem2 = (String) l.getItemAtPosition(pos);
                        System.out.println(clickedItem2);
                        createnewcontactDialog(clickedItem,clickedItem2);
                    });
                    l.setOnItemLongClickListener((arg0, arg1, pos, id1) -> {
                        String longclicked = (String) l.getItemAtPosition(pos);
                        dialogue(longclicked);
                        return true;
                    });
                }
            });
            l.setOnItemLongClickListener((arg0, arg1, pos, id1) -> {
                String longclicked3 = (String) l.getItemAtPosition(pos);
                dialogue(longclicked3);
                return true;
            });

        }
    }



    @Override
    public void onBackPressed() {
        Toast.makeText(this, "PRESS X to abort", LENGTH_SHORT).show();
    }

    public void createnewcontactDialog(String s1 , String s2) {
        String[] u1,u2;
        AtomicBoolean suc = new AtomicBoolean(false);
        u1=s1.split(" ");
        u2=s2.split(" ");
        System.out.println(u1[2]+" "+u2[2]);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        final View tPopup = getLayoutInflater().inflate(R.layout.transfer, null);
        EditText mon = tPopup.findViewById(R.id.amt);
        Button submit = tPopup.findViewById(R.id.send);
        TextView na1 = tPopup.findViewById(R.id.n1);
        TextView na2 = tPopup.findViewById(R.id.n2);
        dialogbuilder.setView(tPopup);
        dialog = dialogbuilder.create();
        dialog.show();
        na1.setText(u1[1]);
        na2.setText(u2[1]);
        //System.out.println("here i am");
        submit.setOnClickListener(v3-> {
            try{
                String tempo = mon.getText().toString();
                if(tempo.length() != 0){
                    Float amount = Float.parseFloat(tempo.trim());
                    System.out.println(amount);
                    if(amount <= Float.parseFloat(u1[2]) ){
                        db.update(Integer.parseInt(u1[0]) , Float.parseFloat(u1[2])-amount   );
                        db.update(Integer.parseInt(u2[0]) , Float.parseFloat(u2[2])+amount   );
                        dialog.dismiss();
                        db.insertT(u1[1],u2[1],amount.toString());
                        go_back();
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            dialog.dismiss();
        });
    }


    void go_back(){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("splash",false);
        startActivity(i);
    }

    public void dialogue(String s1) {
        String[] u1;
        AtomicBoolean suc = new AtomicBoolean(false);
        u1=s1.split(" ");
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        final View tPopup = getLayoutInflater().inflate(R.layout.cust_det, null);
        TextView na1 = tPopup.findViewById(R.id.UName);
        TextView na2 = tPopup.findViewById(R.id.Balance);
        TextView na3 = tPopup.findViewById(R.id.Pno);
        TextView na4 = tPopup.findViewById(R.id.eid);
        dialogbuilder.setView(tPopup);
        dialog = dialogbuilder.create();
        dialog.show();
        na1.setText(" "+u1[1]);
        na2.setText(" "+u1[2]);
        na3.setText(" "+u1[3]);
        na4.setText("User "+u1[0]+":");
        //System.out.println("here i am");
    }
}
