package com.example.studentlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_AVATAR = "avatar";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_STUDENT_ID + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_AVATAR + " INTEGER" + ")";
        db.execSQL(CREATE_STUDENTS_TABLE);

        // Insert sample data
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        insertStudent(db, "Nguyen Van A", "21200288", "nva@gmail.com", R.drawable.avatar1);
        insertStudent(db, "Tran Thi B", "21200222", "ttb@gmail.com", R.drawable.avatar2);
        insertStudent(db, "Le Van C", "21200221", "lvc@gmail.com", R.drawable.avatar3);
    }

    private void insertStudent(SQLiteDatabase db, String name, String studentId, String email, int avatar) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_STUDENT_ID, studentId);
        values.put(KEY_EMAIL, email);
        values.put(KEY_AVATAR, avatar);
        db.insert(TABLE_STUDENTS, null, values);
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)));
                student.setStudentId(cursor.getString(cursor.getColumnIndexOrThrow(KEY_STUDENT_ID)));
                student.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(KEY_EMAIL)));
                student.setAvatar(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_AVATAR)));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentList;
    }
}
