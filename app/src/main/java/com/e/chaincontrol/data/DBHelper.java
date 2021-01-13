package com.e.chaincontrol.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.e.chaincontrol.models.MachineModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";
    //
    public static final String DATABASE_NAME="Prodline.db";
    public static final int DATABASE_VERSION=7;
    private static final String TABLE_MACHINES="Machines";
    //
    private static final String COLUMN_MACHINES_ID ="MachineId";
    private static final String COLUMN_MACHINES_NAME ="MachineName";
    private static final String COLUMN_MACHINES_IP="MachineIp";
    private static final String COLUMN_MACHINES_PORT= "MachinePort";

    private static DBHelper mInstance = null;

    public static DBHelper getInstance(Context ctx) {

        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://developer.android.com/resources/articles/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DBHelper(ctx);
        }
        return mInstance;
    }

    private DBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_MACHINES + "("
                + COLUMN_MACHINES_ID + " INTEGER PRIMARY KEY," + COLUMN_MACHINES_NAME + " TEXT,"
                + COLUMN_MACHINES_IP + " TEXT," + COLUMN_MACHINES_PORT + " INTEGER );";
        // Execute Script.
        db.execSQL(script);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MACHINES);

        // Create tables again
        onCreate(db);

    }
    public void addMachine(MachineModel machine) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + machine.getName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MACHINES_NAME, machine.getName());
        values.put(COLUMN_MACHINES_IP, machine.getIP());
        values.put(COLUMN_MACHINES_PORT,machine.getPort());

        // Inserting Row
        db.insert(TABLE_MACHINES, null, values);

        // Closing database connection
        db.close();
    }


    public MachineModel getMachine(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MACHINES, new String[] { COLUMN_MACHINES_ID,
                        COLUMN_MACHINES_NAME, COLUMN_MACHINES_IP, COLUMN_MACHINES_PORT }, COLUMN_MACHINES_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        MachineModel machine = new MachineModel(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MACHINES_ID))),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MACHINES_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MACHINES_IP)),
                Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MACHINES_PORT))));
        // return note
        return machine;
    }


    public List<MachineModel> getAllMachines() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        List<MachineModel> machineList = new ArrayList<MachineModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MACHINES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MachineModel machine = new MachineModel();
                machine.setIdMachine(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MACHINES_ID))));
                machine.setNameMachine(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MACHINES_NAME)));
                machine.setIPMachine(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MACHINES_IP)));
                machine.setPort(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MACHINES_PORT))));
                // Adding note to list
                machineList.add(machine);
            } while (cursor.moveToNext());
        }

        // return note list
        return machineList;
    }

    public int getMachinesCount() {
        Log.i(TAG, "DBHELPER.getMachinesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_MACHINES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }



    public void deleteNote(MachineModel machine) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... " + machine.getName() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MACHINES, COLUMN_MACHINES_ID + " = ?",
                new String[] { String.valueOf(machine.getId()) });
        db.close();
    }

}
