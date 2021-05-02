package com.example.noteapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.noteapp.model.Note;
import com.example.noteapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.DESCRIPTION + " TEXT)";

        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_NOTE_TABLE = "DROP TABLE IF EXISTS";
        db.execSQL(DROP_NOTE_TABLE, new String[]{Util.TABLE_NAME});

        onCreate(db);
    }

    public boolean fetchNote(String description) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.NOTE_ID}, Util.DESCRIPTION + "=?",
                new String[]{description}, null, null, null);

        int numberOfRows = cursor.getCount();
        db.close();

        if (numberOfRows > 0)
            return true;
        else
            return false;
    }

    public List<Note> fetchAllNotes() {
        List<Note> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNote_id(cursor.getInt(0));
                note.setNote_desc(cursor.getString(1));

                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }

    public long insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.DESCRIPTION, note.getNote_desc());

        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();

        return newRowId;
    }

    public int updatePassword(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.NOTE_ID, note.getNote_id());
        contentValues.put(Util.DESCRIPTION, note.getNote_desc());

        return db.update(Util.TABLE_NAME, contentValues, Util.NOTE_ID + "=?", new String[]{String.valueOf(note.getNote_id())});
    }

    public int deletePassword(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Util.TABLE_NAME, Util.NOTE_ID + "=?", new String[]{String.valueOf(note.getNote_id())});
    }
}
