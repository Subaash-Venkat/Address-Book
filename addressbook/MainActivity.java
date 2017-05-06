package com.example.hp.labdbs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    EditText e1,e2,e3;
    Button b1,b2,b3,b4,b5;
    Spinner s1,s2,s3,s4,s5;
    String city,state,street,country,service,name,phone;
    SQLiteDatabase db = null;
    Cursor c;


    String a1,a2,a3,a4,a5,a6,a7,a8,data;
    Integer id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        e1=(EditText) findViewById(R.id.editText);
        e2=(EditText) findViewById(R.id.editText2);
        e3=(EditText) findViewById(R.id.editText3);
      //  e4=(EditText) findViewById(R.id.editText4);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button5);
        b5 = (Button) findViewById(R.id.button6);
        s1 = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        s4 = (Spinner) findViewById(R.id.spinner4);
        s5 = (Spinner) findViewById(R.id.spinner5);


        db=openOrCreateDatabase("tele", Context.MODE_PRIVATE,null);
        String query1="CREATE TABLE IF NOT EXISTS DETAM(cid INTEGER PRIMARY KEY AUTOINCREMENT,cname varchar,mobile varchar,street varchar,city varchar,ser varchar,state varchar,country varchar);";
        db.execSQL(query1);



        //add

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //id=Integer.parseInt(e1.getText().toString());
                name = e2.getText().toString();
                phone=e3.getText().toString();
                street=s1.getSelectedItem().toString();
                city=s2.getSelectedItem().toString();
                service=s3.getSelectedItem().toString();
                state=s4.getSelectedItem().toString();
                country=s5.getSelectedItem().toString();


                if(city.equals("none")||country.equals("none")||street.equals("none")||state.equals("none")||service.equals("none")){
                    Toast.makeText(getApplicationContext(), "Enter all the fields", Toast.LENGTH_LONG).show();
                }
                else{

                String query2="INSERT INTO DETAM (cname,mobile,street,city,ser,state,country)VALUES ( '"+name+"','"+phone+"','"+street+"','"+city+"','"+service+"','"+state+"','"+country+"' );";
               // String query2="INSERT INTO DETAM VALUES('"+name+"','"+phone+"','"+street+"','"+city+"','"+service+"','"+state+"','"+country+"');";
                db.execSQL(query2);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                c=db.query("DETAM",null,"cname=?",new String[]{name},null,null,null,null);
              //String query3="SELECT * FROM DETAM;";
              //c=db.rawQuery(query3,null);
                c.moveToFirst();
                do{
                    a1=c.getString(c.getColumnIndex("cid"));
                    a2=c.getString(c.getColumnIndex("cname"));
                    a3=c.getString(c.getColumnIndex("mobile"));
                    a4=c.getString(c.getColumnIndex("street"));
                    a5=c.getString(c.getColumnIndex("city"));
                    a6=c.getString(c.getColumnIndex("ser"));
                    a7=c.getString(c.getColumnIndex("state"));
                    a8=c.getString(c.getColumnIndex("country"));
                }while (c.moveToNext());
                Toast.makeText(getApplicationContext(), "Success" + a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8, Toast.LENGTH_LONG).show();
                e1.setText("");
                e2.setText("");
                e3.setText("");

            }}
        });


        //clear

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e1.setText("");
                e2.setText("");
                e3.setText("");
                //s1.setAdapter(null);
            }
        });

        //delete

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.search);
                final EditText e4=(EditText)dialog.findViewById(R.id.editText4);
                Button close = (Button) dialog.findViewById(R.id.button4);

                close.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        data=e4.getText().toString();
                        show("delete",data);
                        e1.setText("");
                        e2.setText("");
                        e3.setText("");
                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();

                        dialog.cancel();
                    }
                });
                dialog.show();
            }});

//search

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.search);
                final EditText e4=(EditText)dialog.findViewById(R.id.editText4);
                Button close = (Button) dialog.findViewById(R.id.button4);

                close.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                       data=e4.getText().toString();
                      show("search",data);
                       dialog.cancel();
                    }

                });
                dialog.show();
            }

        });

        //view all

        b5.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.viewal);
                final TextView view =(TextView)dialog.findViewById(R.id.textView10);
                view.setMovementMethod(new ScrollingMovementMethod());
                //final ListView list = (ListView) dialog.findViewById(R.id.listView);
                Button clo = (Button) dialog.findViewById(R.id.button7);

                c = db.query("DETAM",
                        null, null, null, null, null, null);
                c.moveToFirst();
                while (c.isAfterLast() == false) {
                    view.append("\n" + c.getString(0));
                    view.append("\n" + c.getString(1));
                    view.append("\n" + c.getString(2));
                    view.append("\n" + c.getString(3));
                    view.append("\n" + c.getString(4));
                    view.append("\n" + c.getString(5));
                    view.append("\n" + c.getString(6));
                    view.append("\n" + c.getString(7));

                    c.moveToNext();
                }
                c.close();

                clo.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        dialog.dismiss();
                    }

                });
                dialog.show();

            }});

            }





    //code outside main activity

    public int show(String dat,String data){
        if(dat.equals("delete")){
            //Toast.makeText(getApplicationContext(), data , Toast.LENGTH_LONG).show();
            return db.delete("DETAM","cname = ? ",new String[]{data});
        }
        else if(dat.equals("search")){
            c=db.query("DETAM",null,"cname=?",new String[]{data},null,null,null,null);
            c.moveToFirst();
                a1=c.getString(c.getColumnIndex("cid"));
                a2=c.getString(c.getColumnIndex("cname"));
                a3=c.getString(c.getColumnIndex("mobile"));
                a4=c.getString(c.getColumnIndex("street"));
                a5=c.getString(c.getColumnIndex("city"));
                a6=c.getString(c.getColumnIndex("ser"));
                a7=c.getString(c.getColumnIndex("state"));
                a8=c.getString(c.getColumnIndex("country"));
            e1.setText(a1);
            e2.setText(a2);
            e3.setText(a3);
            Toast.makeText(getApplicationContext(), "Success" + a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8, Toast.LENGTH_LONG).show();
        }

        return 0;
    }


}

