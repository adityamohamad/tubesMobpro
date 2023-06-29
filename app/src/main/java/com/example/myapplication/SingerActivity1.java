package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SingerActivity1 extends AppCompatActivity {
    String[] daftar, name;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    database dbcenter;

    public static SingerActivity1 da; protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singer1);
        Button btn = (Button) findViewById(R.id.buttonInput);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(SingerActivity1.this, InputSinger.class);
                startActivity(inte);
            }
        });
        da = this;
        dbcenter = new database(this);
        RefreshList();
    }
    public void RefreshList(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM singer", null);
        daftar = new String[cursor.getCount()];
        name = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(0).toString()+ "-" + cursor.getString(1).toString();
            name[cc] = cursor.getString(1).toString();
        }
        ListView01 = (ListView) findViewById(R.id.listView1);
        ListView01.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = name[arg2];
                final CharSequence[] dialogitem = {"Lihat Data", "Update Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SingerActivity1.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent  i = new Intent(getApplicationContext(), ProfilMusic.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                            case 1:// akan memanggil activity update data
                                Intent in = new Intent(getApplicationContext(), UpdateMusic.class);
                                in.putExtra("nama", selection);
                                startActivity(in);
                                break;
                            case 2://hapus data
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("delete from singer where title = '" + selection + "'");
                                RefreshList();
                                break;
                            default:
                        }
                    }
                });
                builder.create().show();
                Button btn = (Button) findViewById(R.id.buttonInput);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View args0) {
                        Intent inte = new Intent(SingerActivity1.this, InputMusic.class);
                        startActivity(inte);
                    }
                });
            }
        });
    }
}
