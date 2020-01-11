package com.example.sheik.stocker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String databaseName = "users.db";
    public static final String tableName = "userinfo";
    public static final String col1 = "id";
    public static final String col2 = "username";
    public static final String col3 = "password";
    public static final String col4 = "email";
    public static final String col5 = "gender";

    //  Constructor
    public SQLiteHelper(Context context) {

        super(context, databaseName, null, 1);
        // (for storing data of class, name of database, for Default setting, version of database)
        //  super means that current class is calling the constructor of its
        //  superclass (SqliteHelper constructer is calling SQLiteOpenHelper constructor)

        SQLiteDatabase db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLString = "create table " + tableName + " ("
                + col1 + " " + "Integer primary key autoincrement,"
                + col2 + " " + "Text,"
                + col3 + " " + "Text,"
                + col4 + " " + "Text,"
                + col5 + " " + "Text"
                + ")";


        db.execSQL(SQLString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + tableName);
        onCreate(db);
    }

    public boolean insertData (String row[])
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ourContent = new ContentValues();
        //  Content Values are used to insert all the data you want to send to database in each
        //  transaction.
        ourContent.put(col2, row[0]);
        ourContent.put(col3, row[1]);
        ourContent.put(col4, row[2]);
        ourContent.put(col5, row[3]);

        long result = db.insert(tableName, null, ourContent);

        //  if it receive null means nothing to do means don't insert in case if ContentValues have
        //  nothing put.

        if(result == -1)
            return false;   // in case of any error.
        else
            return true;
    }
    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from "+ tableName, null);

        //rawQuery("Select id, name FROM people where name = ? AND id = ?" ,new String[] {"David","2"};
        return result;
    }
    public Cursor CheckSpecificData(String uname)
    {
        String[] columns = {
                col1, col2, col3, col4, col5
        };
        String[] args = {
                uname
        };
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(tableName, columns, col2 + " = ?", args, null
        ,null, null);
        return res;
        //rawQuery("Select id, name FROM people where name = ? AND id = ?" ,new String[] {"David","2"};
    }
    public Cursor CheckSpecificData(String uname, String pword)
    {
        String[] columns = {
                col1, col2, col3, col4, col5
        };
        String[] args = {
                uname, pword
        };
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query(tableName, columns, col2 + " = ? " + " And "+ col3 +" = ? ", args, null
                ,null, null);
        return res;
        //rawQuery("Select id, name FROM people where name = ? AND id = ?" ,new String[] {"David","2"};
    }
    public boolean updateData (String row[])
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col1, row[0]);
        values.put(col2, row[1]);
        values.put(col3, row[2]);
        values.put(col4, row[3]);
        values.put(col5, row[4]);
        int result = db.update(tableName,values,
                "RollNumber = "+Integer.parseInt(row[0])+"",null);
        if(result > 0)
            return true;
        else
            return false;
    }
    public boolean deleteData(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(tableName,"RollNumber = "+id+"", null);
        if(result > 0)
            return true;
        else
            return false;
    }
}
