package com.umnappclass.contactviewer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactViewerDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "ContactViewer.db";

    public ContactViewerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Contact (id integer primary key, name text, title text,  phone text, email text, twitterId text);");

        db.execSQL("insert into Contact (name, title, phone, email) values ('Malcom Reynolds', 'Captain', '555-1234', 'malcom@firefly.com');");
        db.execSQL("insert into Contact (name, title, phone, email) values ('Zoe Washburn', 'First Mate', '555-5678', 'soe@firefly.com');");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Contact");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
