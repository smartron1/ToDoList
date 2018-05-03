package com.yourstrulyssj.to_dolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SSJ_Recognized on 03-05-2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String Db_Name = "SSJ";
    private static final int Db_Ver = 1;
    public static final String Db_Table = "Task";
    public static final String Db_column = "TaskName";


    public DbHelper(Context context) {
        super(context, Db_Name, null, Db_Ver);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);",Db_Table,Db_column);
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = String.format("DELETE TABLE IF EXISTS %s;",Db_Table);
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

    }

    public void newTask(String task){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Db_column,task);
        sqLiteDatabase.insertWithOnConflict(Db_Table,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        sqLiteDatabase.close();
    }

    public void removeTask(String task){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Db_Table,Db_column + " = ?",new String[]{task});
        sqLiteDatabase.close();


    }

    public ArrayList<String> getTaskList(){
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(Db_Table,new String[]{Db_column},null,null,null,null,null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(Db_column);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        return taskList;

    }
}
