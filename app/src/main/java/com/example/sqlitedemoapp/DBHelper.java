package com.example.sqlitedemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StudentDB";
    private static final String TABLE_NAME = "Student";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ROLL_NO = "rollNo";
    private static final String COLUMN_NAME = "name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_ROLL_NO +
                " INTEGER, " + COLUMN_NAME + " TEXT" + ")";
        Log.d("DBHelper", "Query: " + CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_STUDENT_TABLE);
        Log.d("DBHelper", "TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.d("DBHelper", "TABLE UPDATED");
        onCreate(db);
    }

    // Insert new student record
    public void insertStudent(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ROLL_NO, student.getRollNo());
        values.put(COLUMN_NAME, student.getName());

        db.insert(TABLE_NAME, null, values);
        Log.d("DBHelper", "NEW RECORD INSERTED");
        db.close();
    }

    public List<Student> getAllStudents() {

        List<Student> listStudents = new ArrayList<Student>();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);    // Get all the student records

        if(cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setRollNo(cursor.getInt(1));
                student.setName(cursor.getString(2));

                listStudents.add(student);
            } while(cursor.moveToNext());
        }
        cursor.close();
        Log.d("DBHelper", "No. of records: " + listStudents.size());
         return listStudents;   // Return list back
    }

    public void updateStudent(Student student) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ROLL_NO, student.getRollNo());
        values.put(COLUMN_NAME, student.getName());

        db.update(TABLE_NAME, values, COLUMN_ROLL_NO + "=?", new String[]{String.valueOf(student.getRollNo())});
        db.close();
    }

    public void deleteStudent(int rn) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ROLL_NO + "=?", new String[]{String.valueOf(rn)});
        db.close();
    }
}
