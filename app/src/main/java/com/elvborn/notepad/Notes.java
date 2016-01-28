package com.elvborn.notepad;

public class Notes {

    private int _id;
    private String _notename;

    public Notes(){

    }

    public Notes(String notename){
        this._notename = notename;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_notename(String _notename) {
        this._notename = _notename;
    }

    public int get_id() {
        return _id;
    }

    public String get_notename() {
        return _notename;
    }
}
