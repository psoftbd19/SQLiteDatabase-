package com.a.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student.db";
    private static final String STUDENT_DETAILS = "student_details";
    private static final String STUDENT_LOGIN = "student_login";
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String GENDER = "Gender";
    private static final String CONTACT = "Contact";
    private static final String USERID = "UserId";
    private static final String EMAIL = "Email";
    private static final String PASS = "Pass";
    private static final int DATABASE_VERSION_NO = 4;

    private static final String CREATE_TABLE = "CREATE TABLE "+STUDENT_DETAILS+"( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255),"+AGE+" INTEGER,"+GENDER+" VARCHAR(255),"+CONTACT+" VARCHAR(20));";

    private static final String CREATE_TABLE_LOGIN = "CREATE TABLE "+STUDENT_LOGIN+"( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255),"+EMAIL+" VARCHAR(200),"+USERID+" VARCHAR(255),"+PASS+" VARCHAR(20));";

    private static final String DROP_TABLE_STUDENT_DETAILS = "DROP TABLE IF EXISTS "+STUDENT_DETAILS;
    private static final String DROP_TABLE_STUDENT_LOGIN = "DROP TABLE IF EXISTS "+STUDENT_LOGIN;

    private static final String SELECT_ALL = "SELECT * FROM "+STUDENT_DETAILS;
    private static final String SELECT_LOGIN = "SELECT * FROM "+STUDENT_LOGIN;

    private static final String DELETE_ALL = "DELETE * FROM "+STUDENT_DETAILS;

    private Context context;
    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION_NO);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Toast.makeText(context,"onCreate is called",Toast.LENGTH_SHORT).show();
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE_LOGIN);


        }catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }
    }

    @Override//------------for Table Update-------------------------- 21-01-20 ------
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context,"onUpgrade is called",Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE_STUDENT_DETAILS);
            db.execSQL(DROP_TABLE_STUDENT_LOGIN);
            onCreate(db);
        }catch (Exception e)
        {
            Toast.makeText(context,"Exception : "+e,Toast.LENGTH_SHORT).show();
        }
    }

    public long insertData(String name,String age,String gender,String contact)
    {
       SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);
        contentValues.put(CONTACT,contact);
       long rowId= sqLiteDatabase.insert(STUDENT_DETAILS,null,contentValues);
        return rowId;
    }
    //====================Insert Data Log In Table =======================================================
    public long insertDataLogin(String name,String email,String userID,String pass)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(EMAIL,email);
        contentValues.put(USERID,userID);
        contentValues.put(PASS,pass);

        long rowId= sqLiteDatabase.insert(STUDENT_LOGIN,null,contentValues);
        return rowId;
    }
     public Cursor showAllData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor= sqLiteDatabase.rawQuery(SELECT_ALL,null);
        return cursor;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
       return sqLiteDatabase.delete(STUDENT_DETAILS,ID+" =?",new String[]{id});
    }


 //=====================Update Data========================================================================
public boolean updateData(String id,String name,String age,String gender,String contact)
{
    SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(ID,id);
    contentValues.put(NAME,name);
    contentValues.put(AGE,age);
    contentValues.put(GENDER,gender);
    contentValues.put(CONTACT,contact);
    sqLiteDatabase.update(STUDENT_DETAILS,contentValues,ID+" =?",new String[]{id});
    return true;
}

public Boolean findPassword(String useID,String passn)
{
SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    Cursor cursor= sqLiteDatabase.rawQuery(SELECT_LOGIN,null);
    Boolean result = false;
    if(cursor.getCount()==0)
    {
        Toast.makeText(context,"Data not Found !!",Toast.LENGTH_SHORT).show();
    }
else{
    while (cursor.moveToNext())
    {
        String userID =cursor.getString(3);
        String pass =cursor.getString(4);
        if (userID.equals(useID) && pass.equals(passn))
        {
            result = true;
            break;
        }
    }
    }
return result;
}
}
