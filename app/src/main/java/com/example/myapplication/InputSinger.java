package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputSinger extends AppCompatActivity {
    protected Cursor cursor;
    database db1;
    Button ton1;
    EditText text1, text2, text3, text4, text5;
    String edit;
    TextView textV1, textV2, textV3, textV4, textV5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_singer);
        db1 = new database (this);
        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
        text3 = (EditText) findViewById(R.id.editText3);
        text4 = (EditText) findViewById(R.id.editText4);
        textV1 = (TextView) findViewById(R.id.textView1);
        textV2 = (TextView) findViewById(R.id.textView2);
        textV3 = (TextView) findViewById(R.id.textView3);
        textV4 = (TextView) findViewById(R.id.textView4);
        ton1 = (Button) findViewById(R.id.button1);
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = db1.getWritableDatabase();
                edit = text1.getText().toString();
                edit = text2.getText().toString();
                edit = text3.getText().toString();
                edit = text4.getText().toString();
                if (edit.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Kolom tidak boleh kosong...", Toast.LENGTH_SHORT).show();
                } else {
                    db.execSQL("insert into music (id, nama, jm, negara) values('" +
                            text1.getText().toString() + "','" +
                            text2.getText().toString() + "','" +
                            text3.getText().toString() + "','" +
                            text4.getText().toString()  + "')");
                    Toast.makeText(getApplicationContext(), "Data Tersimpan...", Toast.LENGTH_LONG).show();
                    finish();
                }
                    SongActivity1.da.RefreshList();
            }
        });
    }
}