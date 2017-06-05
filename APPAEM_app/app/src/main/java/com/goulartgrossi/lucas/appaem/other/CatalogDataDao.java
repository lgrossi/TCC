package com.goulartgrossi.lucas.appaem.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import appaem.BasicCircuit;
import appaem.CatalogData;
import appaem.InductionMachine;

/**
 * Created by Lucas Goulart Grossi on 5/30/2017.
 */

public class CatalogDataDao extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "appaem";
    public static final String CD_TABLE_NAME = "catalogData";
    public static final String CD_COLUMN_ID = "_id";
    public static final String CD_COLUMN_POWER = "power";
    public static final String CD_COLUMN_NOMINAL_SPEED = "nominalSpeed";
    public static final String CD_COLUMN_IN = "nominalCurrent";
    public static final String CD_COLUMN_IB = "blockedCurrent";
    public static final String CD_COLUMN_CN = "nominalTorque";
    public static final String CD_COLUMN_CB= "blockedTorque";
    public static final String CD_COLUMN_C_MAX = "maxTorque";
    public static final String CD_COLUMN_EFF_50 = "efficiency50";
    public static final String CD_COLUMN_EFF_75 = "efficiency75";
    public static final String CD_COLUMN_EFF_100 = "efficiency100";
    public static final String CD_COLUMN_PF_50 = "powerFactor50";
    public static final String CD_COLUMN_PF_75 = "powerFactor75";
    public static final String CD_COLUMN_PF_100 = "powerFactor100";

    public CatalogDataDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + CD_TABLE_NAME + " (" +
                CD_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CD_COLUMN_POWER + " DOUBLE, " +
                CD_COLUMN_NOMINAL_SPEED + " DOUBLE, " +
                CD_COLUMN_IN + " DOUBLE, " +
                CD_COLUMN_IB + " DOUBLE, " +
                CD_COLUMN_CN + " DOUBLE, " +
                CD_COLUMN_CB + " DOUBLE, " +
                CD_COLUMN_C_MAX + " DOUBLE, " +
                CD_COLUMN_EFF_50 + " DOUBLE, " +
                CD_COLUMN_EFF_75 + " DOUBLE, " +
                CD_COLUMN_EFF_100 + " DOUBLE, " +
                CD_COLUMN_PF_50 + " DOUBLE, " +
                CD_COLUMN_PF_75 + " DOUBLE, " +
                CD_COLUMN_PF_100 + " DOUBLE" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CD_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteAll () {
        this.getWritableDatabase().execSQL("delete from " + CD_TABLE_NAME);
    }

    public boolean deleteCatalogData(CatalogData catalogData){
        return this.getWritableDatabase().delete(CD_TABLE_NAME, CD_COLUMN_ID + "=" + catalogData.getId(), null) > 0;
    }

    public long saveCatalogDataToDB(CatalogData catalogData) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CD_COLUMN_POWER, catalogData.getPower());
        values.put(CD_COLUMN_NOMINAL_SPEED, catalogData.getNominalSpeed());
        values.put(CD_COLUMN_IN, catalogData.getNominalCurrent());
        values.put(CD_COLUMN_IB, catalogData.getBlockedCurrent());
        values.put(CD_COLUMN_CN, catalogData.getNominalTorque());
        values.put(CD_COLUMN_CB, catalogData.getBlockedTorque());
        values.put(CD_COLUMN_C_MAX, catalogData.getMaxTorque());

        values.put(CD_COLUMN_EFF_50, catalogData.getEfficiency50());
        values.put(CD_COLUMN_EFF_75, catalogData.getEfficiency75());
        values.put(CD_COLUMN_EFF_100, catalogData.getEfficiency100());

        values.put(CD_COLUMN_PF_50, catalogData.getPowerFactor50());
        values.put(CD_COLUMN_PF_75, catalogData.getPowerFactor75());
        values.put(CD_COLUMN_PF_100, catalogData.getPowerFactor100());

        return database.insert(CD_TABLE_NAME, null, values);
    }

    public CatalogData readCatalogDataFromDB(Long id) {

        SQLiteDatabase database = this.getReadableDatabase();

        String[] projection = {
                CD_COLUMN_ID,
                CD_COLUMN_POWER,
                CD_COLUMN_NOMINAL_SPEED,
                CD_COLUMN_IN,
                CD_COLUMN_IB,
                CD_COLUMN_CN,
                CD_COLUMN_CB,
                CD_COLUMN_C_MAX,
                CD_COLUMN_EFF_50,
                CD_COLUMN_EFF_75,
                CD_COLUMN_EFF_100,
                CD_COLUMN_PF_50,
                CD_COLUMN_PF_75,
                CD_COLUMN_PF_100
        };

        String selection =
                CD_COLUMN_ID + " like ?";

        String[] selectionArgs = {"%" + id + "%"};

        Cursor cursor = database.query(
                CD_TABLE_NAME,   // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );
        CatalogData catalogData = null;
        if (cursor != null && cursor.moveToFirst()) {
            catalogData = new CatalogData(cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_POWER)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_NOMINAL_SPEED)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_IN)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_IB)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_CN)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_CB)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_C_MAX)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_EFF_50)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_EFF_75)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_EFF_100)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_PF_50)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_PF_75)),
                    cursor.getDouble(cursor.getColumnIndex(CD_COLUMN_PF_100)));

            catalogData.setId(id);
            cursor.close();
        }
        return catalogData;
    }
}