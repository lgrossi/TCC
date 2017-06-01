package com.goulartgrossi.lucas.appaem.other;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import 	android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import appaem.InductionMachine;

/**
 * Created by Lucas Goulart Grossi on 5/30/2017.
 */

public class InductionMachineDao extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "appaem";
    public static final String IM_TABLE_NAME = "inductionMachine";
    public static final String IM_COLUMN_ID = "_id";
    public static final String IM_COLUMN_NAME = "name";
    public static final String IM_COLUMN_YEAR = "year";
    public static final String IM_COLUMN_MODEL = "model";
    public static final String IM_COLUMN_TYPE = "type";
    public static final String IM_COLUMN_MANUFACTURER = "manufacturer";
    public static final String IM_COLUMN_DESCRIPTION = "description";
    public static final String IM_COLUMN_FREQUENCY = "frequency";
    public static final String IM_COLUMN_POLES = "poles";
    public static final String IM_COLUMN_XMAGNETIC = "xmagnetic";
    public static final String IM_COLUMN_STATOR_ID = "stator";
    public static final String IM_COLUMN_ROTOR_ID = "rotor";
    public static final String IM_COLUMN_THEVENIN_ID = "thevenin";

    private CircuitDao circuitDao;

    public InductionMachineDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(this.getWritableDatabase());
        circuitDao = new CircuitDao(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + IM_TABLE_NAME + " (" +
                IM_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IM_COLUMN_NAME + " TEXT, " +
                IM_COLUMN_YEAR + " TEXT, " +
                IM_COLUMN_MODEL + " TEXT, " +
                IM_COLUMN_TYPE + " TEXT, " +
                IM_COLUMN_MANUFACTURER + " TEXT, " +
                IM_COLUMN_DESCRIPTION + " TEXT, " +
                IM_COLUMN_FREQUENCY + " INTEGER UNSIGNED, " +
                IM_COLUMN_POLES + " INTEGER UNSIGNED, " +
                IM_COLUMN_XMAGNETIC + " DOUBLE, " +
                IM_COLUMN_STATOR_ID + " LONG, " +
                IM_COLUMN_ROTOR_ID + " LONG, " +
                IM_COLUMN_THEVENIN_ID + " LONG" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IM_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteAll () {
        circuitDao.deleteAll();
        this.getWritableDatabase().execSQL("delete from " + IM_TABLE_NAME);
    }

    public boolean deleteInductionMachine(InductionMachine machine){
        circuitDao.deleteInductionMachine(machine.getRotor());
        circuitDao.deleteInductionMachine(machine.getStator());
        //circuitDao.deleteInductionMachine(machine.getThevenin());
        return this.getWritableDatabase().delete(IM_TABLE_NAME, IM_COLUMN_ID + "=" + machine.getId(), null) > 0;
    }

    public long saveInductionMachineToDB(InductionMachine machine) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IM_COLUMN_NAME, machine.getName());
        values.put(IM_COLUMN_YEAR, machine.getYear());
        values.put(IM_COLUMN_MODEL, machine.getModel());
        values.put(IM_COLUMN_TYPE, machine.getTypeId());
        values.put(IM_COLUMN_MANUFACTURER, machine.getManufacturer());
        values.put(IM_COLUMN_DESCRIPTION, machine.getDescription());
        values.put(IM_COLUMN_FREQUENCY, machine.getFrequency());
        values.put(IM_COLUMN_POLES, machine.getnPoles());
        values.put(IM_COLUMN_XMAGNETIC, machine.getXMagnetic());

        values.put(IM_COLUMN_STATOR_ID, circuitDao.saveCircuitToDB(machine.getStator()));
        values.put(IM_COLUMN_ROTOR_ID, circuitDao.saveCircuitToDB(machine.getRotor()));
        values.put(IM_COLUMN_THEVENIN_ID, circuitDao.saveCircuitToDB(machine.getThevenin()));

        return database.insert(IM_TABLE_NAME, null, values);
    }

    public ArrayList<InductionMachine> readInductionMachineFromDB() {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] projection = {
                IM_COLUMN_ID,
                IM_COLUMN_NAME,
                IM_COLUMN_YEAR,
                IM_COLUMN_MODEL,
                IM_COLUMN_TYPE,
                IM_COLUMN_MANUFACTURER,
                IM_COLUMN_DESCRIPTION,
                IM_COLUMN_FREQUENCY,
                IM_COLUMN_POLES,
                IM_COLUMN_XMAGNETIC,
                IM_COLUMN_STATOR_ID,
                IM_COLUMN_ROTOR_ID,
                IM_COLUMN_THEVENIN_ID
        };

        Cursor cursor = database.query(
                IM_TABLE_NAME,   // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                IM_COLUMN_NAME + " ASC"                                      // don't sort
        );

        if (cursor != null && cursor.moveToFirst()) {
            ArrayList<InductionMachine> machines = new ArrayList<>();
            while (true) {
                InductionMachine machine = new InductionMachine(cursor.getInt(cursor.getColumnIndex(IM_COLUMN_FREQUENCY)),
                        cursor.getInt(cursor.getColumnIndex(IM_COLUMN_POLES)),
                        circuitDao.readCircuitFromDB(cursor.getLong(cursor.getColumnIndex(IM_COLUMN_STATOR_ID))),
                        circuitDao.readCircuitFromDB(cursor.getLong(cursor.getColumnIndex(IM_COLUMN_ROTOR_ID))),
                        cursor.getDouble(cursor.getColumnIndex(IM_COLUMN_XMAGNETIC)));

                machine.defineBasicMachineData(cursor.getLong(cursor.getColumnIndex(IM_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(IM_COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(IM_COLUMN_YEAR)),
                        cursor.getString(cursor.getColumnIndex(IM_COLUMN_MODEL)),
                        cursor.getString(cursor.getColumnIndex(IM_COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(IM_COLUMN_MANUFACTURER)),
                        cursor.getString(cursor.getColumnIndex(IM_COLUMN_DESCRIPTION)));

                machines.add(machine);
                if (!cursor.moveToNext()) break;
            }
            cursor.close();
            return machines;
        }
       return null;
    }
}