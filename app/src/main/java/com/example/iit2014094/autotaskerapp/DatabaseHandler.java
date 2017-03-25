package com.example.iit2014094.autotaskerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.iit2014094.autotaskerapp.models.WifiLocations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iit2014094 on 3/24/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wifiManager";
    private static final String TABLE_WIFIS = "wifis";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_BSSID_NO = "mac_address";
    private static final String KEY_SILENCE = "silence";
    private static final String KEY_AUTO_SMS = "auto_sms";
    private Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WIFIS_TABLE = "CREATE TABLE " + TABLE_WIFIS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_BSSID_NO + " TEXT," + KEY_SILENCE + " TEXT,"+KEY_AUTO_SMS + " TEXT"+ ")";
        db.execSQL(CREATE_WIFIS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFIS);
        onCreate(db);
    }

    void addWifi(WifiLocations wifi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, wifi.getName());
        values.put(KEY_BSSID_NO, wifi.getMacAddress());
        values.put(KEY_SILENCE,wifi.getIsSilent());
        values.put(KEY_AUTO_SMS,wifi.getAutoSms());

        db.insert(TABLE_WIFIS, null, values);
        db.close();
    }

    // Getting single contact
    WifiLocations getWifi(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WIFIS, new String[] { KEY_ID,
                        KEY_NAME, KEY_BSSID_NO,KEY_SILENCE,KEY_AUTO_SMS }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        WifiLocations wifi = new WifiLocations(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return contact
        return wifi;
    }

    // Getting All wifis
    public List<WifiLocations> getAllWifis() {

        List<WifiLocations> wifiList = new ArrayList<WifiLocations>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WIFIS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                WifiLocations wifi = new WifiLocations();
                wifi.setID(Integer.parseInt(cursor.getString(0)));
                wifi.setName(cursor.getString(1));
                wifi.setMacAddress(cursor.getString(2));
                wifi.setIsSilent(cursor.getString(3));
                wifi.setIsAutoSms(cursor.getString(4));

                // Adding wifi to list
                wifiList.add(wifi);
            } while (cursor.moveToNext());
        }

        // return wifi list
        return wifiList;
    }

    // Updating single wifiLocation
    public int updateWifi(WifiLocations wifi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, wifi.getName());
        values.put(KEY_BSSID_NO, wifi.getMacAddress());
        values.put(KEY_SILENCE,wifi.getIsSilent());
        values.put(KEY_AUTO_SMS,wifi.getAutoSms());

        // updating row
        return db.update(TABLE_WIFIS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(wifi.getID()) });
    }

    public void deleteWifi(WifiLocations wifi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="DELETE FROM " + TABLE_WIFIS +" WHERE name = '"+wifi.getName()+"'";
        db.execSQL(query);
        db.close();
    }


    // Getting wifi Count
    public int getWifisCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WIFIS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int ret = cursor.getCount();
        cursor.close();

        // return count
        return ret;
    }

    //check if wifi is already in database
    public boolean CheckIsDataAlreadyInDBorNot(String xxx) {

        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_WIFIS +" WHERE mac_address = '" + xxx +"'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

}
