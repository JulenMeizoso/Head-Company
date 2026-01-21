package com.example.headcompany.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.headcompany.model.Incidence;

import java.util.ArrayList;
import java.util.List;

public class DataManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "favs.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Incidence";
    public static final String autonomousRegion = "autonomousRegion";
    public static final String carRegistration = "carRegistration";
    public static final String cause = "cause";
    public static final String cityTown = "cityTown";
    public static final String direction = "direction";
    public static final String endDate = "endDate";
    public static final String incidenceDescription = "incidenceDescription";
    public static final String incidenceId = "incidenceId";
    public static final String incidenceLevel = "incidenceLevel";
    public static final String incidenceName = "incidenceName";
    public static final String incidenteType = "incidenteType";
    public static final String latitude = "latitude";
    public static final String longitude = "longitude";
    public static final String pkEnd = "pkEnd";
    public static final String pkStart = "pkStart";
    public static final String province = "province";
    public static final String road = "road";
    public static final String sourceId = "sourceId";
    public static final String startDate = "startDate";
    private final Context context;

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            incidenceId + " INTEGER PRIMARY KEY UNIQUE, " +
            autonomousRegion + " TEXT, " +
            carRegistration + " TEXT, " +
            cause + " TEXT, " +
            cityTown + " TEXT, " +
            direction + " TEXT, " +
            endDate + " TEXT, " +
            incidenceDescription + " TEXT, " +
            incidenceLevel + " TEXT, " +
            incidenceName + " TEXT, " +
            incidenteType + " TEXT, " +
            latitude + " REAL, " +
            longitude + " REAL, " +
            pkEnd + " REAL, " +
            pkStart + " REAL, " +
            province + " TEXT, " +
            road + " TEXT, " +
            sourceId + " INTEGER, " +
            startDate + " TEXT" +
            " );";

    public DataManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addFavorite(Incidence incidence) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(incidenceId, incidence.getIncidenceId());
        values.put(autonomousRegion, incidence.getAutonomousRegion());
        values.put(carRegistration, incidence.getCarRegistration());
        values.put(cause, incidence.getCause());
        values.put(cityTown, incidence.getCityTown());
        values.put(direction, incidence.getDirection());
        values.put(endDate, incidence.getEndDate());
        values.put(incidenceDescription, incidence.getIncidenceDescription());
        values.put(incidenceLevel, incidence.getIncidenceLevel());
        values.put(incidenceName, incidence.getIncidenceName());
        values.put(incidenteType, incidence.getIncidenceType());
        values.put(latitude, incidence.getLatitude());
        values.put(longitude, incidence.getLongitude());
        values.put(pkEnd, incidence.getPkEnd());
        values.put(pkStart, incidence.getPkStart());
        values.put(province, incidence.getProvince());
        values.put(road, incidence.getRoad());
        values.put(sourceId, incidence.getSourceId());
        values.put(startDate, incidence.getStartDate());

        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // Get all favorite incidences
    public List<Incidence> getFavorites() {
        List<Incidence> favorites = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Incidence incidence = new Incidence(
                        cursor.getString(cursor.getColumnIndexOrThrow(autonomousRegion)),
                        cursor.getString(cursor.getColumnIndexOrThrow(carRegistration)),
                        cursor.getString(cursor.getColumnIndexOrThrow(cause)),
                        cursor.getString(cursor.getColumnIndexOrThrow(cityTown)),
                        cursor.getString(cursor.getColumnIndexOrThrow(direction)),
                        cursor.getString(cursor.getColumnIndexOrThrow(endDate)),
                        cursor.getString(cursor.getColumnIndexOrThrow(incidenceDescription)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(incidenceId)),
                        cursor.getString(cursor.getColumnIndexOrThrow(incidenceLevel)),
                        cursor.getString(cursor.getColumnIndexOrThrow(incidenceName)),
                        cursor.getString(cursor.getColumnIndexOrThrow(incidenteType)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(latitude)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(longitude)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(pkEnd)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(pkStart)),
                        cursor.getString(cursor.getColumnIndexOrThrow(province)),
                        cursor.getString(cursor.getColumnIndexOrThrow(road)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(sourceId)),
                        cursor.getString(cursor.getColumnIndexOrThrow(startDate))
                );

                favorites.add(incidence);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return favorites;
    }

    public boolean isFavorite(int incidenceId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_NAME + " WHERE " + this.incidenceId + " = ?",
                new String[]{String.valueOf(incidenceId)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }

    public void removeFavorite(int incidenceId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, this.incidenceId + "=?", new String[]{String.valueOf(incidenceId)});
        db.close();
    }


}
