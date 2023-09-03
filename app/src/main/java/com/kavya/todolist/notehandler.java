package com.kavya.todolist;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class notehandler extends DatabaseHelper {

    public notehandler(Context context) {
        super(context);
    }

    //CRUD  C create, R read, U update, D delete

    public boolean create(node note) {

        ContentValues values = new ContentValues();

        values.put("title", note.getTitle());
        values.put("description", note.getDesc());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean isSuccessfull = db.insert("Note", null, values) > 0;
        db.close();
        return isSuccessfull;
    }

    public ArrayList<node> readNotes() {
        ArrayList<node> notes = new ArrayList<>();

        String sqlQuery = "SELECT * FROM Note ORDER BY id ASC";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            do {

               @SuppressLint("Range") int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
               @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));

                node note = new node(title, description);
                note.setId(id);
                notes.add(note);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
        }
        return notes;
    }

    public node readSingleNote(int id) {
        node note = null;
        String sqlQuery = "SELECT * FROM Note WHERE id=" + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int noteId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));

            note = new node(title, description);
            note.setId(noteId);
        }
        cursor.close();
        db.close();
        return note;
    }

    public boolean update(node note) {

        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("description", note.getDesc());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isSuccessfull = db.update("Note", values, "id='" + note.getId() + "'", null) > 0;
        db.close();
        return isSuccessfull;
    }

    public boolean delete(int id) {
        boolean isDeleted;
        SQLiteDatabase db = this.getWritableDatabase();
        isDeleted = db.delete("Note", "id='" + id + "'", null) > 0;
        db.close();
        return isDeleted;
    }

}
