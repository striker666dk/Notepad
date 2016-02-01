package com.elvborn.notepad;

/**
 * Created by Elvborn on 30-01-2016.
 */
public class ListItem {

    private int _id;
    private String _noteName;
    private String _listItemName;
    private int _checkbox;
    private int _count;

    public ListItem(){

    }

    public ListItem(String notename, String listItemName, int checkbox, int count){
        this._noteName = notename;
        this._listItemName = listItemName;
        this._checkbox = checkbox;
        this._count = count;
    }

    public int get_id() {
        return _id;
    }

    public String get_notename() {
        return _noteName;
    }

    public String get_listItemName() {
        return _listItemName;
    }

    public int get_checkbox() {
        return _checkbox;
    }

    public int get_count() {
        return _count;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_notename(String _notename) {
        this._noteName = _notename;
    }

    public void set_listItemName(String _listItemName) {
        this._listItemName = _listItemName;
    }

    public void set_checkbox(int _checkbox) {
        this._checkbox = _checkbox;
    }

    public void set_count(int _count) {
        this._count = _count;
    }
}
