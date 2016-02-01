package com.elvborn.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products.db";

    //Table 1
    public static final String TABLE_1_NAME = "notes";
    public static final String COLUMN_1_ID = "_id";
    public static final String COLUMN_1_NOTENAME = "_notename";

    //Table 2
    public static final String TABLE_2_NAME = "lists";
    public static final String COLUMN_2_PARENT_NAME = "_notename";
    public static final String COLUMN_2_LISTITEM_NAME = "_listitemname";
    public static final String COLUMN_2_CHECKBOX = "_checkbox";
    public static final String COLUMN_2_LISTITEM_COUNT = "_count";


    public static final String CREATE_TABLE_1 = "CREATE TABLE "+ TABLE_1_NAME + "(" +
            COLUMN_1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_1_NOTENAME + " TEXT " +
            ");";

    public static final String CREATE_TABLE_2 = "CREATE TABLE " + TABLE_2_NAME + "(" +
            COLUMN_2_PARENT_NAME + " TEXT, " +
            COLUMN_2_LISTITEM_NAME + " TEXT, " +
            COLUMN_2_CHECKBOX + " INTEGER, " +
            COLUMN_2_LISTITEM_COUNT + " INTEGER " +
            ");";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_1);
        db.execSQL(CREATE_TABLE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1_NAME + ";");
        onCreate(db);
    }

    //Add a note to the database
    public void addNote(Notes note){
        ContentValues values = new ContentValues();
        values.put(COLUMN_1_NOTENAME, note.get_notename());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_1_NAME, null, values);
        db.close();
    }

    //Delete note from database
    public void deleteNote(String noteName){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_2_NAME + " WHERE " + COLUMN_2_PARENT_NAME + "=\"" + noteName + "\";");
        db.execSQL("DELETE FROM " + TABLE_1_NAME + " WHERE " + COLUMN_1_NOTENAME + "=\"" + noteName + "\";");
    }

    //Add list item
    public void addListItem(ListItem listItem){
        ContentValues values = new ContentValues();
        values.put(COLUMN_2_PARENT_NAME, listItem.get_notename());
        values.put(COLUMN_2_LISTITEM_NAME, listItem.get_listItemName());
        values.put(COLUMN_2_CHECKBOX, listItem.get_checkbox());
        values.put(COLUMN_2_LISTITEM_COUNT, listItem.get_count());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_2_NAME, null, values);
        db.close();
    }

    //Delete selected listItems
    public void deleteSelectedItems(String noteName){
        SQLiteDatabase db = getWritableDatabase();

        if(checkCheckboxes(noteName)) {
            db.delete(TABLE_2_NAME,
                    COLUMN_2_PARENT_NAME + " = ? AND " + COLUMN_2_CHECKBOX + " = ?",
                    new String[]{noteName, "1"});
        }
        db.close();
    }

    //Update listItem values
    public void updateListItem(ListItem listItem, int isChecked, int listItemCount){
        ContentValues values = new ContentValues();
        values.put(COLUMN_2_CHECKBOX, isChecked);
        values.put(COLUMN_2_LISTITEM_COUNT, listItemCount);

        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_2_NAME,
                values,
                COLUMN_2_PARENT_NAME + " = ? AND " + COLUMN_2_LISTITEM_NAME + " = ?",
                new String[]{listItem.get_notename(), listItem.get_listItemName()});
        db.close();
    }

    //Returns an array for all the notes in database
    public List getAllNotes(){
        List<String> notes = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_1_NAME + " WHERE 1;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_1_NOTENAME)) != null){
                notes.add(c.getString(c.getColumnIndex(COLUMN_1_NOTENAME)));

                c.moveToNext();
            }
        }

        db.close();
        return notes;
    }

    //Returns an array of listItems
    public ListItem[] getListItems(String noteName){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_2_NAME + " WHERE " + COLUMN_2_PARENT_NAME + "=\"" + noteName + "\";";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        ListItem[] listItems = new ListItem[c.getCount()];
        int counter = 0;

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_2_LISTITEM_NAME)) != null){
                ListItem listItem = new ListItem(
                        noteName,
                        c.getString(c.getColumnIndex(COLUMN_2_LISTITEM_NAME)),
                        c.getInt(c.getColumnIndex(COLUMN_2_CHECKBOX)),
                        c.getInt(c.getColumnIndex(COLUMN_2_LISTITEM_COUNT))
                );

                listItems[counter] = listItem;
                counter++;
                c.moveToNext();
            }
        }

        db.close();
        return listItems;
    }

    //Check if note already is in database, and if name in valid
    public boolean checkNote(String name){
        String query = "SELECT * FROM " + TABLE_1_NAME + " WHERE " + COLUMN_1_NOTENAME + "=\"" + name + "\";";
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        System.out.println(c.getCount());

        return !(c.getCount() != 0 || name.isEmpty());
    }

    //Check if note already in in database
    public boolean checkListItem(String noteName, String listItem){
        String query = "SELECT * FROM " + TABLE_2_NAME + " WHERE " + COLUMN_2_PARENT_NAME + "=\"" + noteName + "\" AND " + COLUMN_2_LISTITEM_NAME + "=\"" + listItem + "\";";
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        System.out.println(c.getCount());

        return !(c.getCount() != 0 || listItem.isEmpty());
    }

    //Check if any checkboxes is checked
    public boolean checkCheckboxes(String noteName){
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_2_NAME + " WHERE " + COLUMN_2_PARENT_NAME + "=\"" + noteName + "\" AND " + COLUMN_2_CHECKBOX + "=1";
        Cursor c = db.rawQuery(query, null);

        return c.getCount() != 0;
    }
}