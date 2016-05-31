package tcss450.uw.edu.team12.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tcss450.uw.edu.team12.model.Stop;

/**
 * Created by Lachezar Dimov on 5/15/2016.
 */
public class FavoriteStopsDB {
    private static final String FAV_STOPS_TABLE = "FavoriteStops";

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "FavStops.db";

    private FavoriteStopsDBHelper mFavStopsDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public FavoriteStopsDB(Context context) {
        mFavStopsDBHelper = new FavoriteStopsDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mFavStopsDBHelper.getWritableDatabase();
    }

    /**
     * Inserts the stop into the local sqlite favorite stops table.
     * Returns true if successful, false otherwise.
     * @param stopId
     * @param stopName
     * @return true or false
     */
    public boolean insertStop(String stopId, String stopName) {

        long rowId = -1;
        ContentValues contentValues = new ContentValues();
        contentValues.put("stop_id", stopId);
        contentValues.put("stop_name", stopName);

        try {
            rowId = mSQLiteDatabase.insertWithOnConflict(FAV_STOPS_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_FAIL);

        } catch (SQLiteConstraintException e) {
            Log.d(this.getClass().toString(), "SQLiteConstraintException caught." + " UNIQUE constraint failed.");
        }
        mSQLiteDatabase.close();
        return rowId != -1;
    }

    /**
     * Removes selected stop from local sqlite favorite stops table.
     * Returns true if successful, false otherwise.
     * @param stopId
     * //@param stopName
     * @return true or false
     */
    public boolean removeStop(String stopId) { // , String stopName

        long rowId = -1;

        try {
            rowId = mSQLiteDatabase.delete(FAV_STOPS_TABLE, "stop_id" + "=" + stopId, null);

        } catch (SQLiteConstraintException e) {
            Log.d(this.getClass().toString(), "SQLiteConstraintException caught." + " UNIQUE constraint failed.");
        }
        mSQLiteDatabase.close();
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    public void deleteFavStops(){
        mSQLiteDatabase.delete(FAV_STOPS_TABLE, null, null);
    }

    public List<Stop> getFavStops() {

        String[] columns = {
                "stop_id", "stop_name"
        };

        Cursor c = mSQLiteDatabase.query(
                FAV_STOPS_TABLE,  // The table to query
                columns,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        c.moveToFirst();
        List<Stop> list = new ArrayList<Stop>();
        for (int i=0; i < c.getCount(); i++) {
            String stopId = c.getString(0);
            String stopName = c.getString(1);
            Stop stop = new Stop(stopId, stopName);
            list.add(stop);
            c.moveToNext();
        }

        return list;
    }

    class FavoriteStopsDBHelper extends SQLiteOpenHelper {

        private static final String CREATE_FAV_STOPS_SQL =
                "CREATE TABLE IF NOT EXISTS FavoriteStops "
                        + "(stop_id TEXT PRIMARY KEY, stop_name TEXT)";

        private static final String DROP_FAV_STOPS_SQL =
                "DROP TABLE IF EXISTS FavoriteStops";

        public FavoriteStopsDBHelper(Context context, String name,
                                     SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_FAV_STOPS_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_FAV_STOPS_SQL);
            onCreate(sqLiteDatabase);
        }

        @Override
        public synchronized void close() {
            super.close();
        }
    }
}
