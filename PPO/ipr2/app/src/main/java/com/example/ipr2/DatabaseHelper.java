package com.example.ipr2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE = "Products";
    public static final String ID = "_id";
    public static final String NAME = "_name";
    public static final String TYPE = "_type";
    public static final String COST = "_cost";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Users.db";

    public static final String TO_SQL_CREATE =
            "CREATE TABLE " + TABLE + " (" +
                    ID + " INTEGER PRIMARY KEY," +
                    NAME + " TEXT," +
                    TYPE + " TEXT," +
                    COST  + " INTEGER)";

    public static final String TO_SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE;

    public SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TO_SQL_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TO_SQL_DELETE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    public void addToDatabase(String name, String type, int cost) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(TYPE, type);
        cv.put(COST, cost);
        database.insert(TABLE, null, cv);
    }

    public void clearDb() {
        for(int i = 0; i < 100; i++) {
            database.delete(TABLE,ID + "=" + i, null);
        }
    }

    @SuppressLint("Range")
    public List<Product> readDatabase() {
        List<Product> list = new ArrayList<>();
        Cursor c = database.query(TABLE,null,null,null,
                null, null, null);
        while (c.moveToNext()) {
             String id = c.getString(c.getColumnIndex(ID));
             String name = c.getString(c.getColumnIndex(NAME));
             String type = c.getString(c.getColumnIndex(TYPE));
             int cost = c.getInt(c.getColumnIndex(COST));
             Product product = new Product(name,type,cost);
             list.add(product);
        }
        c.close();
        return list;
    }

    @SuppressLint("Range")
    public Product takeFromDatabase(int id) {
        Product product = new Product(null,null,0);
        Cursor c = database.query(TABLE,null,null,null,
                null, null, null);
        while (c.moveToNext()) {
            int thisId = c.getInt(c.getColumnIndex(ID));
            if (id == thisId) {
                String name = c.getString(c.getColumnIndex(NAME));
                String type = c.getString(c.getColumnIndex(TYPE));
                int cost = c.getInt(c.getColumnIndex(COST));
                product = new Product(name, type, cost);
                break;
            }
        }
        c.close();
        return product;
    }

    @SuppressLint("Range")
    public void deleteFromDatabase(String name) {
        int id = 0;
        Cursor c = database.query(TABLE,null,null,null,
                null, null, null);
        while (c.moveToNext()) {
            String thisUsername = c.getString(c.getColumnIndex(NAME));
            if(name.equals(thisUsername)) {
                id = c.getInt(c.getColumnIndex(ID));
                database.delete(TABLE,ID + "=" + id, null);
                break;
            }
        }
        c.close();
        ContentValues cv = new ContentValues();
        Cursor cursor = database.query(TABLE,null,null,null,
                null, null, null);
        while (cursor.moveToNext()) {
            int i = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)));
            if(i > id) {
                cv.put(ID, i - 1);
                database.update(TABLE, cv, ID + "=" + i, null);
            }
        }
        cursor.close();
    }


    @SuppressLint("Range")
    public void updateDatabase(String oldName, String newName, String type, int cost) {
        Cursor c = database.query(TABLE,null,null,null,
                null, null, null);
        while (c.moveToNext()) {
            String thisUsername = c.getString(c.getColumnIndex(NAME));
            if(oldName.equals(thisUsername)) {
                int id = c.getInt(c.getColumnIndex(ID));
                ContentValues cv = new ContentValues();
                cv.put(NAME, newName);
                cv.put(TYPE, type);
                cv.put(COST, cost);
                database.update(TABLE, cv, ID + "=" + id, null);
                break;
            }
        }
        c.close();
    }

    public void closeDatabase() {
        this.close();
    }
}