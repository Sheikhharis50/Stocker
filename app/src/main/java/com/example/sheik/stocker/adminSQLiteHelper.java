package com.example.sheik.stocker;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class adminSQLiteHelper extends SQLiteOpenHelper {

    private static final String databaseName ="admins.db";

    public static final String tableName = "admininfo";
    public static final String col1 = "id";
    public static final String col2 = "username";
    public static final String col3 = "password";
    public static final String col4 = "email";
    public static final String col5 = "category";
    public static final String col6 = "company";
    public static final String col7 = "adresss";
    public static final String col8 = "contact";
    public static final String col9 = "gender";

    public static final String ctableName= "clothes";
    public static final String ccol1 = "id";
    public static final String ccol2 = "company";
    public static final String ccol3 = "title";
    public static final String ccol4 = "code";
    public static final String ccol5 = "stock";
    public static final String ccol6 = "price";
    public static final String ccol7 = "description";
    public static final String ccol8 = "owner";
    public static final String ccol9 = "gender";

    private static final String ftableName= "food";
    // same as clothes


    //  Constructor
    public adminSQLiteHelper(Context context) {

        super(context, databaseName, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQLString = "create table "+ tableName + "("
                + col1 + " " + "Integer primary key autoincrement,"
                + col2 + " " + "Text not null,"
                + col3 + " " + "Text not null,"
                + col4 + " " + "Text not null,"
                + col5 + " " + "Text not null,"
                + col6 + " " + "Text not null,"
                + col7 + " " + "Text not null,"
                + col8 + " " + "Integer not null,"
                + col9 + " " + "Text not null"
                + ")";


        String SQLString1 = "create table "+ ctableName + "("
                + ccol1 + " " + "Integer primary key autoincrement, "
                + ccol2 + " " + "Text not null, "
                + ccol3 + " " + "Text not null, "
                + ccol4 + " " + "Text not null, "
                + ccol5 + " " + "Integer not null, "
                + ccol6 + " " + "Text not null, "
                + ccol7 + " " + "Text not null, "
                + ccol8 + " " + "Text not null, "
                + ccol9 + " " + "Text not null "
                + ")";

        String SQLString2 = "create table "+ ftableName + "("
                + ccol1 + " " + "Integer primary key autoincrement, "
                + ccol2 + " " + "Text not null, "
                + ccol3 + " " + "Text not null, "
                + ccol4 + " " + "Text not null, "
                + ccol5 + " " + "Integer not null, "
                + ccol6 + " " + "Text not null, "
                + ccol7 + " " + "Text not null, "
                + ccol8 + " " + "Text not null "
                + ")";


        db.execSQL(SQLString);
        db.execSQL(SQLString1);
        db.execSQL(SQLString2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + tableName);
        db.execSQL("Drop table if exists " + ctableName);
        db.execSQL("Drop table if exists " + ftableName);
        onCreate(db);
    }

    public boolean insertData(String tn,String row[])
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ourContent = new ContentValues();
        if(tn.equals(tableName))
        {
            ourContent.put(col2, row[0]);
            ourContent.put(col3, row[1]);
            ourContent.put(col4, row[2]);
            ourContent.put(col5, row[3]);
            ourContent.put(col6, row[4]);
            ourContent.put(col7, row[5]);
            ourContent.put(col8, row[6]);
            ourContent.put(col9, row[7]);
        }
        else if(tn.equals(ctableName) || tn.equals(ftableName))
        {
            ourContent.put(ccol2, row[0]);
            ourContent.put(ccol3, row[1]);
            ourContent.put(ccol4, row[2]);
            ourContent.put(ccol5, row[3]);
            ourContent.put(ccol6, row[4]);
            ourContent.put(ccol7, row[5]);
            ourContent.put(ccol8, row[6]);
            if(tn.equals(ctableName)) { ourContent.put(ccol9, row[7]);}
        }
        long result = db.insert(tn, null, ourContent);
        if(result==-1)
            return false;
        else
            return true;
    }
    public String[] getCategoryAndCompany(String uname)
    {
        String [] admin = new String[2];
        SQLiteDatabase db = this.getReadableDatabase();
        String [] columns = {
                col5, col6
        };
        String [] args = {
            uname
        };
        Cursor res = db.query(tableName, columns, col2 + " = ?", args,
                null, null, null);
        if(res.getCount() > 0 && res.moveToFirst() ) {
            admin[0] = res.getString(res.getColumnIndex(col5));
            admin[1] = res.getString(res.getColumnIndex(col6));
            res.close();
            return admin;
        }
        else
            return null;
    }
    public Cursor getAllData(String tn, String com) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = null;
        if(tn.equals(ctableName)  || tn.equals(ftableName))
        {
            String [] columns = {
                    ccol3,ccol4,ccol5,ccol6,ccol7
            };
            String [] args = {
                    com
            };
            res = db.query(ctableName, columns, ccol2 + " = ?", args, null
                    ,null, null);
        }
        return res;
    }
    public Cursor getAllCompanys(String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        if(category.equals(ftableName))
        {
            c = db.rawQuery("Select "+ ccol2 +" from "+tableName+" where "+col5+" = '"+ftableName+"'", null);
        }
        else if(category.equals(ctableName))
        {
            c = db.rawQuery("Select "+ ccol2 +" from "+tableName+" where "+col5+" = '"+ctableName+"'", null);
        }
        return c;
    }
    public Cursor getAllProducts(String category, String company)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;
        if(category.equals(ftableName))
        {
            c = db.rawQuery("Select * from "+ftableName+" where "+col6+" = '"+company+"'", null);
        }
        else if(category.equals(ctableName))
        {
            c = db.rawQuery("Select * from "+ctableName+" where "+col6+" = '"+company+"'", null);
        }
        return c;
    }
    public Cursor CheckSpecificData(String tn, String uname)
    {
        String[] columns = new String[9];
        Cursor res = null;
        SQLiteDatabase db = this.getWritableDatabase();
        if(tn.equals(tableName))
        {
            columns[0] = col1;
            columns[1] = col2;
            columns[2] = col3;
            columns[3] = col4;
            columns[4] = col5;
            columns[5] = col6;
            columns[6] = col7;
            columns[7] = col8;
            columns[8] = col9;
            String[] args = {
                    uname
            };
            res = db.query(tn, columns, col2 + " = ?", args, null
                    ,null, null);
        }
        else if(tn.equals(ctableName)  || tn.equals(ftableName))
        {
            columns[0] = ccol1;
            columns[1] = ccol2;
            columns[2] = ccol3;
            columns[3] = ccol4;
            columns[4] = ccol5;
            columns[5] = ccol6;
            columns[6] = ccol7;
            columns[7] = ccol8;
            if(tn.equals(ctableName)) { columns[8] = ccol9;}
            String[] args = {
                    uname
            };
            res = db.query(tn, columns, ccol8 + " = ?", args, null
                    ,null, null);
        }
        return res;
    }
    public Cursor CheckSpecificData(String tn, String uname, String pword)
    {
        String[] columns = new String[8];
        Cursor res = null;
        SQLiteDatabase db = this.getWritableDatabase();
        if(tn.equals("admininfo"))
        {
            columns[0] = col1;
            columns[1] = col2;
            columns[2] = col3;
            columns[3] = col4;
            columns[4] = col5;
            columns[5] = col6;
            columns[6] = col7;
            columns[7] = col8;
            String[] args = {
                    uname, pword
            };
            res = db.query(tn, columns, col2 + " = ? " + " And "+ col3 +" = ? ", args, null
                ,null, null);
        }
        else if(tn.equals(ctableName)  || tn.equals(ftableName))
        {
            columns[0] = ccol1;
            columns[1] = ccol2;
            columns[2] = ccol3;
            columns[3] = ccol4;
            columns[4] = col5;
            columns[5] = ccol6;
            columns[6] = ccol7;
            columns[7] = ccol8;
            String[] args = {
                    uname, pword
            };
            res = db.query(tn, columns, ccol8 + " = ? " + " And "+ ccol3 +" = ? ", args, null
                    ,null, null);
        }
        return res;
    }
    public boolean updateData (String tn, String row[])
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int result = 0;
        if(tn.equals(tableName))
        {
            values.put(col3, row[1]);
            values.put(col4, row[2]);
            values.put(col5, row[3]);
            values.put(col6, row[4]);
            values.put(col7, row[5]);
            values.put(col8, row[6]);
            result = db.update(tn,values,
                    col2 + " = "+row[0]+"",null);
        }
        else if(tn.equals(ctableName)   || tn.equals(ftableName))
        {
            values.put(ccol4, row[2]);
            values.put(ccol5, row[3]);
            values.put(ccol6, row[4]);
            values.put(ccol7, row[5]);
            values.put(ccol8, row[6]);
            result = db.update(tn,values,
                    ccol2 + " = "+ "'" + row[0]+"'" + " AND " + ccol3 + " = " + "'"+row[1]+"'",null);
        }
        if(result > 0)
            return true;
        else
            return false;
    }
    public boolean Deletedata(String tn, String uname, String Title)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        if(tn.equals(ctableName)   || tn.equals(ftableName))
        {
            String [] com = getCategoryAndCompany(uname);
            db.delete(tn, ccol3 + " = '" + Title + "' And " + ccol2 + " = '" + com[1] + "'",null );
        }
        return true;
    }
}
