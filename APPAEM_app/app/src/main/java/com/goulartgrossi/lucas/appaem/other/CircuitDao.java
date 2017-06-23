package com.goulartgrossi.lucas.appaem.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import appaem.BasicCircuit;
import appaem.InductionMachine;

/**
 * Created by Lucas Goulart Grossi on 5/30/2017.
 */

public class CircuitDao extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "appaem";
    public static final String CIRCUIT_TABLE_NAME = "circuit";
    public static final String CIRCUIT_COLUMN_ID = "_id";
    public static final String CIRCUIT_COLUMN_VOLTAGE = "voltage";
    public static final String CIRCUIT_COLUMN_CURRENT = "current";
    public static final String CIRCUIT_COLUMN_RESISTANCE = "resistance";
    public static final String CIRCUIT_COLUMN_REACTANCE = "reactance";

    public CircuitDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + CIRCUIT_TABLE_NAME + " (" +
                CIRCUIT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CIRCUIT_COLUMN_VOLTAGE + " DOUBLE, " +
                CIRCUIT_COLUMN_CURRENT + " DOUBLE, " +
                CIRCUIT_COLUMN_RESISTANCE + " DOUBLE, " +
                CIRCUIT_COLUMN_REACTANCE + " DOUBLE" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CIRCUIT_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void deleteAll () {
        this.getWritableDatabase().execSQL("delete from " + CIRCUIT_TABLE_NAME);
    }

    public boolean deleteInductionMachine(BasicCircuit circuit){
        return this.getWritableDatabase().delete(CIRCUIT_TABLE_NAME, CIRCUIT_COLUMN_ID + "=" + circuit.getId(), null) > 0;
    }

    public long saveCircuitToDB(BasicCircuit circuit) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CIRCUIT_COLUMN_VOLTAGE, circuit.getVoltage());
        values.put(CIRCUIT_COLUMN_CURRENT, circuit.getCurrent());
        values.put(CIRCUIT_COLUMN_RESISTANCE, circuit.getResistance());
        values.put(CIRCUIT_COLUMN_REACTANCE, circuit.getReactance());

        if (circuit.getId() != null) {
            return database.update(CIRCUIT_TABLE_NAME, values, "_id="+circuit.getId(), null);
        }

        return database.insert(CIRCUIT_TABLE_NAME, null, values);
    }

    public BasicCircuit readCircuitFromDB(Long id) {

        SQLiteDatabase database = this.getReadableDatabase();

        String[] projection = {
                CIRCUIT_COLUMN_ID,
                CIRCUIT_COLUMN_VOLTAGE,
                CIRCUIT_COLUMN_CURRENT,
                CIRCUIT_COLUMN_RESISTANCE,
                CIRCUIT_COLUMN_REACTANCE
        };

        String selection =
                CIRCUIT_COLUMN_ID + " like ?";

        String[] selectionArgs = {"%" + id + "%"};

        Cursor cursor = database.query(
                CIRCUIT_TABLE_NAME,   // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );
        BasicCircuit circuit = null;
        if (cursor != null && cursor.moveToFirst()) {
            circuit = new BasicCircuit(cursor.getDouble(cursor.getColumnIndex(CIRCUIT_COLUMN_VOLTAGE)),
                    cursor.getDouble(cursor.getColumnIndex(CIRCUIT_COLUMN_CURRENT)),
                    cursor.getDouble(cursor.getColumnIndex(CIRCUIT_COLUMN_RESISTANCE)),
                    cursor.getDouble(cursor.getColumnIndex(CIRCUIT_COLUMN_REACTANCE)));
            circuit.setId(id);
            cursor.close();
        }
        return circuit;
    }
}