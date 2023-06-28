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

public class UpdateMusic extends AppCompatActivity {
    protected Cursor cursor;
    database dbHelper;
    Button ton1;
    EditText text1, text2, text3, text4;
    String edit;
    TextView textV1, textV2, textV3, textV4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_music);
        dbHelper = new database(this);
        text1 = (EditText) findViewById(R.id.editText1);
        text2 = (EditText) findViewById(R.id.editText2);
        text3 = (EditText) findViewById(R.id.editText3);
        text4 = (EditText) findViewById(R.id.editText4);
        textV1 = (TextView) findViewById(R.id.textView1);
        textV2 = (TextView) findViewById(R.id.textView2);
        textV3 = (TextView) findViewById(R.id.textView3);
        textV4 = (TextView) findViewById(R.id.textView4);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM singer WHERE title = '" +getIntent().getStringExtra("title") + "' LIMIT 1", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            text1.setText(cursor.getString(0).toString());
            text2.setText(cursor.getString(1).toString());
            text3.setText(cursor.getString(2).toString());
            text4.setText(cursor.getString(3).toString());
        }
        ton1 = (Button) findViewById(R.id.button1);
        // daftarkan even onClick pada btnSimpan
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                edit = text1.getText().toString();
                edit = text2.getText().toString();
                edit = text3.getText().toString();
                edit = text4.getText().toString();
                if (edit.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Kolom tidak boleh kosong...", Toast.LENGTH_SHORT).show();
                } else {
                    db.execSQL("update singer set title ='" +
                            text2.getText().toString() + "', artist='" +
                            text3.getText().toString() + "', genre='" +
                            text4.getText().toString() + "' where id='" +
                            text1.getText().toString() + "'");
                    Toast.makeText(getApplicationContext(), "Perubahan Tersimpan...", Toast.LENGTH_LONG).show();
                    finish();
                }
                SingerActivity1.da.RefreshList();
            }
        });

    }
}