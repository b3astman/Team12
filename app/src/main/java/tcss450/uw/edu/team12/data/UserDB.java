package tcss450.uw.edu.team12.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by bethany on 5/2/16.
 */
public class UserDB {

    private static final String USER_TABLE = "User";
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "User.db";

    private UserDBHelper mUserDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public UserDB(Context context) {
        mUserDBHelper = new UserDBHelper(
                context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mUserDBHelper.getWritableDatabase();

    }

    public boolean insertUser(String id, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("password", password);

        long rowId = mSQLiteDatabase.insert("User", null, contentValues);
        return rowId != -1;
    }

    public void deleteUser(){
        mSQLiteDatabase.delete(USER_TABLE, null, null);
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    /* Returns the list of users from the local Course table.
    * @return list
    */
//    public List<User> getUsers() {
//
//        String[] columns = {
//                "id", "password",
//        };
//
//        Cursor c = mSQLiteDatabase.query(
//                USER_TABLE,  // The table to query
//                columns,                               // The columns to return
//                null,                                // The columns for the WHERE clause
//                null,                            // The values for the WHERE clause
//                null,                                     // don't group the rows
//                null,                                     // don't filter by row groups
//                null                                 // The sort order
//        );
//        c.moveToFirst();
//        List<User> list = new ArrayList<User>();
//        for (int i=0; i<c.getCount(); i++) {
//            String id = c.getString(0);
//            String password = c.getString(1);
//            User user = new User(id, password);
//            list.add(user);
//            c.moveToNext();
//        }
//
//        return list;
//    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
//        HashMap<String,String> user = new HashMap<String,String>();
//        String selectQuery = "SELECT  * FROM " + USER_TABLE;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        // Move to first row
//        cursor.moveToFirst();
//        if(cursor.getCount() > 0){
//            user.put("email", cursor.getString(1));
//            user.put("password", cursor.getString(4));
////            Log.e(cursor.getString(1)+cursor.getString(2), cursor.getString(3)+cursor.getString(4));
//        }
//        cursor.close();
//        db.close();
//        // return user
//        return user;

        return new HashMap<String, String>();
    }

    class UserDBHelper extends SQLiteOpenHelper {

        private static final String CREATE_USER_SQL =
                "CREATE TABLE IF NOT EXISTS User "
                        + "(id TEXT PRIMARY KEY, password TEXT)";

        private static final String DROP_USER_SQL =
                "DROP TABLE IF EXISTS User";

        public UserDBHelper(Context context, String name,
                            SQLiteDatabase.CursorFactory factory,
                            int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_USER_SQL);
            onCreate(db);
        }

        @Override
        public synchronized void close() {
            super.close();
        }
    }



}
